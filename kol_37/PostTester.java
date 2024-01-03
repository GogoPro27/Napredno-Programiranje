package SecondPartialExcercises.kol_37;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


public class PostTester {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String postAuthor = sc.nextLine();
        String postContent = sc.nextLine();

        Post p = new Post(postAuthor, postContent);

        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            String[] parts = line.split(";");
            String testCase = parts[0];

            if (testCase.equals("addComment")) {
                String author = parts[1];
                String id = parts[2];
                String content = parts[3];
                String replyToId = null;
                if (parts.length == 5) {
                    replyToId = parts[4];
                }
                p.addComment(author, id, content, replyToId);
            } else if (testCase.equals("likes")) { //likes;1;2;3;4;1;1;1;1;1 example
                for (int i = 1; i < parts.length; i++) {
                    p.likeComment(parts[i]);
                }
            } else {
                System.out.println(p);
            }

        }
    }
}
interface ISocialMediaComponent{
    String print(int indent);
}
abstract class SocialMediaComponent implements ISocialMediaComponent{
    String username;
    String content;
    List<Comment> comments;

    public SocialMediaComponent(String username, String content) {
        this.username = username;
        this.content = content;
        comments = new ArrayList<>();
    }
}
class Post extends SocialMediaComponent{
    Map<String,Comment> commentMap;

    public Post(String username, String content) {
        super(username, content);
        commentMap = new HashMap<>();
    }

    public void addComment(String username, String commentId, String content,String replyToId) {
        Comment newComment = new Comment(username, content, commentId);
        if (replyToId==null) {
            comments.add(newComment);
            commentMap.putIfAbsent(commentId,newComment);
        } else {
            if (commentMap.containsKey(replyToId)) {
                commentMap.get(replyToId).addComment(username,commentId,content);
            }
        }
    }
    void likeComment (String commentId){
        if (commentMap.containsKey(commentId))
            commentMap.get(commentId).addLike();
    }

    @Override
    public String print(int indent) {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Post: %s\nWritten by: %s\n",content,username));
        sb.append("Comments:\n");
        comments.stream().sorted(Comment.COMPARATOR).forEach(comment -> sb.append(comment.print(indent+8)));
        return sb.toString();
    }

    @Override
    public String toString() {
//        System.out.println(comments);
        return print(0);
    }
}
class Comment extends SocialMediaComponent{
    String commentId;
    int likes;
    final static Comparator<Comment> COMPARATOR =
            Comparator.comparing(Comment::getLikes).reversed();

    public Comment(String username, String content, String commentId) {
        super(username, content);
        this.commentId = commentId;
        likes = 0;
    }

    public void addComment(String username, String commentId, String content) {
        comments.add(new Comment(username,content,commentId));
    }

    @Override
    public String print(int indent) {
        StringBuilder sb = new StringBuilder();
        String spaces = IntStream.range(0,indent).mapToObj(i->" ").collect(Collectors.joining(""));
        sb.append(String.format("%sComment: %s\n%sWritten by: %s\n%sLikes: %d\n",
                spaces,content,spaces,username,spaces,likes));
        comments.stream().sorted(COMPARATOR).forEach(comment -> sb.append(comment.print(indent+4)));
        return sb.toString();
    }

    public String getCommentId() {
        return commentId;
    }

    public int getLikes() {
        if (comments.isEmpty())
            return likes;
        else return likes + comments.stream().mapToInt(Comment::getLikes).sum();
    }
    public void addLike(){likes++;}
}