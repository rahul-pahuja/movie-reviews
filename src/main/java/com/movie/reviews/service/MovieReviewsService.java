package com.movie.reviews.service;

import java.util.List;
import java.util.Map;

import com.movie.reviews.exception.BadRequestException;
import com.movie.reviews.exception.ConflictException;
import com.movie.reviews.exception.ResourceNotFoundException;
import com.movie.reviews.projection.MovieProjection;

public interface MovieReviewsService {

	public Map<String, String> addUsers(String name);

	public Map<String, String> addMovie(String movieName, String genre, String year);

	public Map<String, String> addReview(String userId, String movieId, int score)
			throws ConflictException, BadRequestException, ResourceNotFoundException;

	public Map<String, List<MovieProjection>> getMoviesByTotalCriticReviewScoresAndGenre(String genre, int size);

	public Map<String, Object> getAverageReviewScoresByYear(String movieYear) throws ResourceNotFoundException;

	public Map<String, Object> getAverageReviewScoresByMovie(String movieId) throws ResourceNotFoundException;

}
