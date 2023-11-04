package movie.review.controller.model;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.ManyToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import movie.review.entity.Genre;
import movie.review.entity.Movie;
import movie.review.entity.Review;

@Data
@NoArgsConstructor
public class MovieData {
	private Long movieId;
	private String movieName;
	private String director;
	private String releaseDate;
	private String runtime;
	private String budget;
	private Set<ReviewData> reviews = new HashSet<>();
	private Set<GenreData> genres = new HashSet<>();

	public MovieData(Movie movie) {  // constructor
		this.movieId = movie.getMovieId();
		this.movieName = movie.getMovieName();
		this.director = movie.getDirector();
		this.releaseDate = movie.getReleaseDate();
		this.runtime = movie.getRuntime();
		this.budget = movie.getBudget();

		for (Review review : movie.getReviews()) {
			this.reviews.add(new ReviewData(review));
		}

		for (Genre genre : movie.getGenres()) {
			this.genres.add(new GenreData(genre));
		}
	}

	public MovieData(Long movieId, String movieName, String director, String releaseDate, String runtime,
			String budget) {
		this.movieId = movieId;
		this.movieName = movieName;
		this.director = director;
		this.releaseDate = releaseDate;
		this.runtime = runtime;
		this.budget = budget;
	}

	public Movie toMovie() {
		Movie movie = new Movie();

		movie.setMovieId(movieId);
		movie.setMovieName(movieName);
		movie.setDirector(director);
		movie.setReleaseDate(releaseDate);
		movie.setRuntime(runtime);
		movie.setBudget(budget);

		for (ReviewData reviewData : reviews) {
			movie.getReviews().add(reviewData.toReview());
		}
		
		for (GenreData genreData : genres) {
			movie.getGenres().add(genreData.toGenre());
		}

		return movie;
	}

	@Data
	@NoArgsConstructor
	public class ReviewData {
		private Long reviewId;
		private String reviewerName;
		private String review;
		private String score;

		public ReviewData(Review review) {  // constructor 
			this.reviewId = review.getReviewId();
			this.reviewerName = review.getReviewerName();
			this.review = review.getReview();
			this.score = review.getScore();
		}

		public Review toReview() {
			Review review1 = new Review();

			review1.setReviewId(reviewId);
			review1.setReviewerName(reviewerName);
			review1.setReview(review);
			review1.setScore(score);

			return review1;
		}
	}
	
	@Data
	@NoArgsConstructor
	private class GenreData {
		private Long genreId;
		private String genreName;
		private Set<Movie> movies = new HashSet<>();
		
		public GenreData(Genre genre) {  // constructor
			this.genreId = genre.getGenreId();
			this.genreName = genre.getGenreName();
		}
		
		public Genre toGenre() {
			Genre genre = new Genre();

			genre.setGenreId(genreId);
			genre.setGenreName(genreName);

			return genre;
		}
	}

}
