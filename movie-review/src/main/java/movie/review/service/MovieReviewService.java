package movie.review.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import movie.review.controller.model.MovieData;
import movie.review.controller.model.MovieData.ReviewData;
import movie.review.dao.MovieDao;
import movie.review.dao.ReviewDao;
import movie.review.entity.Movie;
import movie.review.entity.Review;

@Service
public class MovieReviewService {

	@Autowired
	private MovieDao movieDao;
	
	@Autowired
	private ReviewDao reviewDao;

	@Transactional(readOnly = false)
	public MovieData saveMovie(MovieData movieData) {
		Movie movie = movieData.toMovie();
		Movie dbMovie = movieDao.save(movie);

		return new MovieData(dbMovie);
	}

	@Transactional(readOnly = false)
	public void deleteMovieById(Long movieId) {
		Movie movie = findMovieById(movieId);
		movieDao.delete(movie);
	}

	private Movie findMovieById(Long movieId) {
		return movieDao.findById(movieId).orElseThrow(
			() -> new NoSuchElementException("Movie with ID=" + movieId + " was not found.")
		);
	}

	@Transactional(readOnly = true)
	public List<MovieData> getAllMovies() {
		return movieDao.findAll()
			.stream()
			.map(MovieData::new)
			.toList();
	}

	@Transactional(readOnly = true)
	public MovieData getMovieById(Long movieId) {
		Movie movie = findMovieById(movieId);
		return new MovieData(movie);
	}

//	@Transactional(readOnly = false)
//	public ReviewData saveReview(Long movieId, ReviewData reviewData) {
//		Movie movie = findMovieById(movieId);
//		
//		Review review = findOrCreateReview(reviewData.getReviewId());
//		setReviewFields(review, reviewData);
//		
//		review.setMovie(movie);
//		movie.getReviews().add(review);
//		
//		Review dbReview = reviewDao.save(review);
//		return new ReviewData(dbReview);
//	}

	private void setReviewFields(Review review, ReviewData reviewData) {
		review.setReviewId(reviewData.getReviewId());
		review.setReviewerName(reviewData.getReviewerName());
		review.setReview(reviewData.getReview());
		review.setScore(reviewData.getScore());
	}

	private Review findOrCreateReview(Long reviewId) {
		Review review;
		
		if(Objects.isNull(reviewId)) {
			review = new Review();
		}
		else {
			review = findReviewById(reviewId);
		}
		
		return review;
	}

	private Review findReviewById(Long reviewId) {
		return reviewDao.findById(reviewId).orElseThrow(
			() -> new NoSuchElementException("Review with ID=" + reviewId + " does not exist.")
		);
	}

}
