package com.movie.reviews.service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.movie.reviews.entity.Movie;
import com.movie.reviews.exception.BadRequestException;
import com.movie.reviews.exception.ConflictException;
import com.movie.reviews.exception.ResourceNotFoundException;
import com.movie.reviews.projection.MovieProjection;
import com.movie.reviews.repo.MoviesRepository;
import com.movie.reviews.repo.UsersRepository;
import com.movie.reviews.utils.ProjectConstants;

@Service
public class MovieReviewsServiceImpl implements MovieReviewsService {

	@Autowired
	private UsersRepository usersRepository;

	@Autowired
	private MoviesRepository moviesRepository;

	@Override
	public Map<String, String> addUsers(String name) {

		String userId = usersRepository.addUser(name);

		Map<String, String> response = new HashMap<String, String>();

		response.put(ProjectConstants.MESSAGE, ProjectConstants.USER_ADDED_MESSAGE);
		response.put(ProjectConstants.USER_ID, userId);

		return response;

	}

	@Override
	public Map<String, String> addMovie(String movieName, String movieGenre, String movieReleasedYear) {

		String movieId = moviesRepository.addMovie(movieName, movieReleasedYear, movieGenre);

		Map<String, String> response = new HashMap<String, String>();

		response.put(ProjectConstants.MESSAGE, ProjectConstants.MOVIE_ADDED_MESSAGE);
		response.put(ProjectConstants.MOVIE_ID, movieId);

		return response;

	}

	@Override
	public Map<String, String> addReview(String userId, String movieId, int score)
			throws ConflictException, BadRequestException, ResourceNotFoundException {

		Movie movie = moviesRepository.findByMovieId(movieId);

		int currentYear = LocalDateTime.now().getYear();
		int movieReleasedYear = Integer.parseInt(movie.getMovieReleasedYear());

		if (currentYear <= movieReleasedYear) {
			throw new BadRequestException(ProjectConstants.MOVIE_RELEASED_YEAR_MESSAGE);
		}

		boolean isAdded = usersRepository.addMovieReview(userId, movieId);

		if (!isAdded) {
			throw new ConflictException(ProjectConstants.ALREADY_REVIEWED_MOVIE_MESSAGE);
		}

		int userReviewedMovieCount = usersRepository.getMovieReviewedCountByUser(userId);

		if (userReviewedMovieCount > 3) {
			moviesRepository.updateReviewScoresByCritic(movieId, score);
		} else {
			moviesRepository.updateReviewScores(movieId, score);
		}

		Map<String, String> response = new HashMap<String, String>();

		response.put(ProjectConstants.MESSAGE, ProjectConstants.REVIEW_ADDED_MESSAGE);

		return response;

	}

	@Override
	public Map<String, List<MovieProjection>> getMoviesByTotalCriticReviewScoresAndGenre(String genre, int size) {

		List<MovieProjection> movies = moviesRepository.getMoviesByTotalCriticReviewScoresAndGenre(genre, size);

		Map<String, List<MovieProjection>> response = new HashMap<String, List<MovieProjection>>();

		response.put(ProjectConstants.DATA, movies);

		return response;

	}

	@Override
	public Map<String, Object> getAverageReviewScoresByYear(String movieYear) throws ResourceNotFoundException {

		float averageReviewScore = moviesRepository.getAverageReviewScoresByYear(movieYear);

		Map<String, Object> response = new HashMap<String, Object>();

		response.put(ProjectConstants.YEAR, movieYear);
		response.put(ProjectConstants.AVERAGE_REVIEWS, averageReviewScore);

		return response;

	}

	@Override
	public Map<String, Object> getAverageReviewScoresByMovie(String movieId) throws ResourceNotFoundException {

		float averageReviewScore = moviesRepository.getAverageReviewScoresByMovie(movieId);

		Map<String, Object> response = new HashMap<String, Object>();

		response.put(ProjectConstants.MOVIE_ID, movieId);
		response.put(ProjectConstants.AVERAGE_REVIEWS, averageReviewScore);

		return response;

	}

}
