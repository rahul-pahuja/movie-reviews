package com.movie.reviews.service;

public interface MovieReviewsService {

	public String addUsers(String name);

	public String addMovie(String movieName, String genre, String year);

	public void addReviews(String userId, String movieId, int score);

	public void getMoviesByTotalCriticReviewScoresAndGenre(String movieGenre, int size);

	public float getAverageReviewScoresByYear(String movieYear);

	public float getAverageReviewScoresByMovie(String movieId);

}
