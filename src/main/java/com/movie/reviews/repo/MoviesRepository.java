package com.movie.reviews.repo;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Repository;

import com.movie.reviews.entity.Movie;
import com.movie.reviews.exception.BadRequestException;
import com.movie.reviews.exception.ResourceNotFoundException;
import com.movie.reviews.projection.MovieProjection;
import com.movie.reviews.utils.ProjectConstants;

class MovieProjectionComparator implements Comparator<MovieProjection> {
	@Override
	public int compare(MovieProjection m1, MovieProjection m2) {
		return Float.compare(m2.getMovieAverageCriticReviews(), m1.getMovieAverageCriticReviews());
	}
}

@Repository
public class MoviesRepository {

	private final Map<String, Movie> movies = new ConcurrentHashMap<String, Movie>();

	private final Map<String, Map<String, Integer>> yearGroupByIndex = new ConcurrentHashMap<String, Map<String, Integer>>();

	private final static String TOTAL_REVIEWED_SCORES = "totalReviewedScores";

	private final static String TOTAL_REVIEWED_BY = "totalReviewedBy";

	public Movie findByMovieId(String movieId) throws BadRequestException, ResourceNotFoundException {

		isMoviePresent(movieId);

		Movie movie = movies.get(movieId);

		return movie;

	}

	public String addMovie(String movieName, String movieReleasedYear, String movieGenre) {

		Movie movie = new Movie();

		movie.setMovieName(movieName);
		movie.setMovieReleasedYear(movieReleasedYear);
		movie.setMovieGenre(movieGenre);

		String movieId = UUID.randomUUID().toString();
		movie.setMovieId(movieId);

		movies.put(movieId, movie);

		return movieId;

	}

	public void updateReviewScores(String movieId, int score) {

		Movie movie = movies.get(movieId);

		int currentTotalReviewedBy = movie.getTotalReviewedBy();
		movie.setTotalReviewedBy(currentTotalReviewedBy + 1);

		int currentTotalReviewedScores = movie.getTotalReviewedScores();
		movie.setTotalReviewedScores(currentTotalReviewedScores + score);

		String movieYear = movie.getMovieReleasedYear();
		addMovieRatingToYearIndex(movieYear, score, 1);

	}

	public void updateReviewScoresByCritic(String movieId, int score) {

		Movie movie = movies.get(movieId);

		int currentTotalReviewedBy = movie.getTotalReviewedBy();
		movie.setTotalReviewedBy(currentTotalReviewedBy + 2);

		int currentTotalReviewedScores = movie.getTotalReviewedScores();
		movie.setTotalReviewedScores(currentTotalReviewedScores + score * 2);

		int currentTotalCriticReviewedBy = movie.getTotalCriticReviewedBy();
		movie.setTotalCriticReviewedBy(currentTotalCriticReviewedBy + 1);

		int currentTotalCriticReviewedScores = movie.getTotalCriticReviewedScores();
		movie.setTotalCriticReviewedScores(currentTotalCriticReviewedScores + score);

		String movieYear = movie.getMovieReleasedYear();
		addMovieRatingToYearIndex(movieYear, score * 2, 2);

	}

	public List<MovieProjection> getMoviesByTotalCriticReviewScoresAndGenre(String genre, int size) {

		List<MovieProjection> movies = new ArrayList<MovieProjection>();

		for (Movie movie : this.movies.values()) {

			String currentMovieGenre = movie.getMovieGenre();
			if (currentMovieGenre.equals(genre)) {

				int currentCriticReviewedScores = movie.getTotalCriticReviewedScores();
				float averageCriticReviews;

				if (currentCriticReviewedScores == 0) {
					averageCriticReviews = 0.0f;
				} else {
					int currentCriticReviewedBy = movie.getTotalCriticReviewedBy();
					averageCriticReviews = (float) currentCriticReviewedScores / currentCriticReviewedBy;
				}

				MovieProjection movieProjection = new MovieProjection(movie.getMovieId(), movie.getMovieName(),
						averageCriticReviews);
				movies.add(movieProjection);
			}
		}

		movies.sort(new MovieProjectionComparator());

		if (movies.size() <= size) {
			return movies;
		}
		return movies.subList(0, size);

	}

	public float getAverageReviewScoresByMovie(String movieId) throws ResourceNotFoundException {

		isMoviePresent(movieId);

		Movie movie = movies.get(movieId);

		int currentTotalReviewedScores = movie.getTotalReviewedScores();
		if (currentTotalReviewedScores == 0) {
			return 0.0f;
		}

		int currentTotalReviewedBy = movie.getTotalReviewedBy();
		float averageReviewScore = (float) currentTotalReviewedScores / currentTotalReviewedBy;

		return averageReviewScore;

	}

	public float getAverageReviewScoresByYear(String movieYear) throws ResourceNotFoundException {

		if (!yearGroupByIndex.containsKey(movieYear)) {
			throw new ResourceNotFoundException(ProjectConstants.YEAR_NOT_FOUND_MESSAGE);
		}

		Map<String, Integer> reviews = yearGroupByIndex.get(movieYear);

		int currentTotalReviewedScores = reviews.get(TOTAL_REVIEWED_SCORES);
		int currentTotalReviewedBy = reviews.get(TOTAL_REVIEWED_BY);
		float averageReviewScore = (float) currentTotalReviewedScores / currentTotalReviewedBy;

		return averageReviewScore;

	}

	private void isMoviePresent(String movieId) throws ResourceNotFoundException {
		if (!this.movies.containsKey(movieId)) {
			throw new ResourceNotFoundException(ProjectConstants.MOVIE_NOT_FOUND_MESSAGE);
		}
	}

	private void addMovieRatingToYearIndex(String movieYear, int reviewedScore, int reviewedBy) {

		yearGroupByIndex.putIfAbsent(movieYear, new ConcurrentHashMap<String, Integer>());
		Map<String, Integer> reviews = yearGroupByIndex.get(movieYear);

		int currentTotalReviewedScores = reviews.getOrDefault(TOTAL_REVIEWED_SCORES, 0);
		int currentTotalReviewedBy = reviews.getOrDefault(TOTAL_REVIEWED_BY, 0);

		reviews.put(TOTAL_REVIEWED_SCORES, currentTotalReviewedScores + reviewedScore);
		reviews.put(TOTAL_REVIEWED_BY, currentTotalReviewedBy + reviewedBy);

	}

}
