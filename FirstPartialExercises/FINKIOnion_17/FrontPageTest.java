package Napredno_Programiranje.FirstPartialExercises.FINKIOnion_17;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class FrontPageTest {
    public static void main(String[] args) {
        // Reading
        Scanner scanner = new Scanner(System.in);
        String line = scanner.nextLine();
        String[] parts = line.split(" ");
        Category[] categories = new Category[parts.length];
        for (int i = 0; i < categories.length; ++i) {
            categories[i] = new Category(parts[i]);
        }
        int n = scanner.nextInt();
        scanner.nextLine();
        FrontPage frontPage = new FrontPage(categories);
        Calendar cal = Calendar.getInstance();
        for (int i = 0; i < n; ++i) {
            String title = scanner.nextLine();
            cal = Calendar.getInstance();
            int min = scanner.nextInt();
            cal.add(Calendar.MINUTE, -min);
            Date date = cal.getTime();
            scanner.nextLine();
            String text = scanner.nextLine();
            int categoryIndex = scanner.nextInt();
            scanner.nextLine();
            TextNewsItem tni = new TextNewsItem(title, date, categories[categoryIndex], text);
            frontPage.addNewsItem(tni);
        }

        n = scanner.nextInt();
        scanner.nextLine();
        for (int i = 0; i < n; ++i) {
            String title = scanner.nextLine();
            int min = scanner.nextInt();
            cal = Calendar.getInstance();
            cal.add(Calendar.MINUTE, -min);
            scanner.nextLine();
            Date date = cal.getTime();
            String url = scanner.nextLine();
            int views = scanner.nextInt();
            scanner.nextLine();
            int categoryIndex = scanner.nextInt();
            scanner.nextLine();
            MediaNewsItem mni = new MediaNewsItem(title, date, categories[categoryIndex], url, views);
            frontPage.addNewsItem(mni);
        }
        // Execution
        String category = scanner.nextLine();
        System.out.println(frontPage);
        for(Category c : categories) {
            System.out.println(frontPage.listByCategory(c).size());
        }
        try {
            System.out.println(frontPage.listByCategoryName(category).size());
        } catch(CategoryNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
}

class CategoryNotFoundException extends Exception{
    String string;
    public CategoryNotFoundException(String s) {
        string = s;
    }

    @Override
    public String getMessage() {
        return "Category "+string+ " was not found";
    }
}
// Vasiot kod ovde

class Category{
    private String category;

    public Category(String category) {
        this.category = category;
    }

    public String getCategory() {
        return category;
    }
}
abstract class NewsItem {
    private String title;
    private Date date;
    private Category category;

    public NewsItem(String title, Date date, Category category) {
        this.title = title;
        this.date = date;
        this.category = category;
    }

    public Category getCategory() {
        return category;
    }
    public abstract String getTeaser();

    public String getTitle() {
        return title;
    }

    public Date getDate() {
        return date;
    }
}
class TextNewsItem extends NewsItem {
    private String news_text;

    public TextNewsItem(String title, Date date, Category category, String news_text) {
        super(title, date, category);
        this.news_text = news_text;
    }

    @Override
    public String getTeaser() {
        StringBuilder s = new StringBuilder();

        long duration = Calendar.getInstance().getTime().getTime() - getDate().getTime();
        s.append(getTitle()).append("\n");
        s.append(TimeUnit.MILLISECONDS.toMinutes(duration)).append('\n');

        for (int i = 0; i < news_text.length()&&i<80; i++) {
            s.append(news_text.charAt(i));
        }
        s.append('\n');
        return s.toString();
    }
}
class MediaNewsItem extends NewsItem {
    private final String url;
    private final int views;

    public MediaNewsItem(String title, Date date, Category category, String url, int views) {
        super(title, date, category);
        this.url = url;
        this.views = views;
    }

    @Override
    public String getTeaser() {
        StringBuilder s = new StringBuilder();
        long duration = Calendar.getInstance().getTime().getTime() - getDate().getTime();
        s.append(getTitle()).append("\n");
        s.append(TimeUnit.MILLISECONDS.toMinutes(duration)).append('\n');
        s.append(url).append('\n').append(views).append('\n');
        return s.toString();
    }
}
class FrontPage{
    List<NewsItem> news;
    Category[] categories;

    public FrontPage(Category[] categories) {
        this.categories = categories;
        news = new ArrayList<>();
    }
    public void addNewsItem(NewsItem newSItem){
        news.add(newSItem);
    }
    public List<NewsItem> listByCategory(Category category){
        return news.stream().filter(i->i.getCategory().equals(category)).collect(Collectors.toList());
    }
    public List<NewsItem> listByCategoryName(String category) throws CategoryNotFoundException {
        Optional<Category> optional = Arrays.stream(categories).filter(category1 -> category1.getCategory().equals(category)).findAny();
        if(optional.isEmpty()) throw new CategoryNotFoundException(category);
        return news.stream().filter(i->i.getCategory().getCategory().equals(category)).collect(Collectors.toList());
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        news.forEach(i->s.append(i.getTeaser()));
        return s.toString();
    }
}