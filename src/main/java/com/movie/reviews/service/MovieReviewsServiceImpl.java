package com.movie.reviews.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.movie.reviews.repo.MoviesRepository;
import com.movie.reviews.repo.UsersRepository;

@Service
public class MovieReviewsServiceImpl implements MovieReviewsService {

	@Autowired
	private UsersRepository usersRepository;

	@Autowired
	private MoviesRepository moviesRepository;

	@Override
	public String addUsers(String name) {
		String userId = usersRepository.addUser(name);
		return userId;
	}

	@Override
	public String addMovie(String movieName, String movieGenre, String movieReleasedYear) {
		String movieId = moviesRepository.addMovie(movieName, movieReleasedYear, movieGenre);
		return movieId;
	}

	@Override
	public void addReviews(String userId, String movieId, int score) {

		if (1 > score || score > 10) {
			throw new RuntimeException("send a valid review scores!");
		}

		boolean isAdded = usersRepository.addMovieReview(userId, movieId);
		if (isAdded) {
			throw new RuntimeException("user has already reviewed the movie!");
		}

		int userReviewedMovieCount = usersRepository.getMovieReviewedCountByUser(userId);

		if (userReviewedMovieCount > 3) {
			moviesRepository.updateReviewScoresByCritic(movieId, score);
		} else {
			moviesRepository.updateReviewScores(movieId, score);
		}

	}

	@Override
	public void getMoviesByTotalCriticReviewScoresAndGenre(String genre, int size) {

	}

	@Override
	public float getAverageReviewScoresByYear(String movieYear) {
		float averageReviewScores = moviesRepository.getAverageReviewScoresByYear(movieYear);
		return averageReviewScores;
	}

	@Override
	public float getAverageReviewScoresByMovie(String movieId) {
		float averageReviewScore = moviesRepository.getAverageReviewScoresByMovie(movieId);
		return averageReviewScore;
	}

}
