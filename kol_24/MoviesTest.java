package SecondPartialExcercises.kol_24;

import java.util.*;
import java.util.stream.Collectors;

public class MoviesTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        MoviesList moviesList = new MoviesList();
        int n = scanner.nextInt();
        scanner.nextLine();
        for (int i = 0; i < n; ++i) {
            String title = scanner.nextLine();
            int x = scanner.nextInt();
            int[] ratings = new int[x];
            for (int j = 0; j < x; ++j) {
                ratings[j] = scanner.nextInt();
            }
            scanner.nextLine();
            moviesList.addMovie(title, ratings);
        }
        scanner.close();
        List<Movie> movies = moviesList.top10ByAvgRating();
        System.out.println("=== TOP 10 BY AVERAGE RATING ===");
        for (Movie movie : movies) {
            System.out.println(movie);
        }
        movies = moviesList.top10ByRatingCoef();
        System.out.println("=== TOP 10 BY RATING COEFFICIENT ===");
        for (Movie movie : movies) {
            System.out.println(movie);
        }
    }
}

// vashiot kod ovde
class MoviesList{
    private static List<Movie> movies;

    public MoviesList() {
        movies = new ArrayList<>();
    }

    public void addMovie(String title, int[] ratings){
        List<Integer>list = Arrays.stream(ratings).boxed().collect(Collectors.toList());
        movies.add(new Movie(title, list));
    }
    public List<Movie> top10ByAvgRating(){
        return movies.stream()
                .sorted(Comparator.comparing(Movie::averageRating).reversed().thenComparing(Movie::getTitle))
                .limit(10)
                .collect(Collectors.toList());
    }
    public List<Movie> top10ByRatingCoef(){
        return movies.stream()
                .sorted(Comparator.comparing(Movie::getRatingCoef).reversed().thenComparing(Movie::getTitle))
                .limit(10)
                .collect(Collectors.toList());
    }
    public static int maximumNumOfRatings(){
        return movies.stream()
                .mapToInt(movie->movie.getRatings().size())
                .max().orElse(0);
    }
}
class Movie{
    private String title;
    private List<Integer> ratings;

    public Movie(String title, List<Integer> ratings) {
        this.title = title;
        this.ratings = ratings;
    }
    public double averageRating(){
       return (double) ratings.stream().mapToInt(rating -> rating).sum() /ratings.size();
    }
    public double getRatingCoef(){
       return averageRating() *ratings.size()/MoviesList.maximumNumOfRatings();
    }

    public String getTitle() {
        return title;
    }

    public List<Integer> getRatings() {
        return ratings;
    }

    @Override
    public String toString() {
        return String.format("%s (%.2f) of %d ratings",title,averageRating(),ratings.size());
    }
}