package com.movie.reviews.projection;

public class MovieProjection {

	private String movieId;

	private String movieName;

	private float movieAverageCriticReviews;

	public MovieProjection() {
		super();
	}

	public MovieProjection(String movieId, String movieName, float movieAverageCriticReviews) {
		super();
		this.movieId = movieId;
		this.movieName = movieName;
		this.movieAverageCriticReviews = movieAverageCriticReviews;
	}

	public String getMovieId() {
		return movieId;
	}

	public void setMovieId(String movieId) {
		this.movieId = movieId;
	}

	public String getMovieName() {
		return movieName;
	}

	public void setMovieName(String movieName) {
		this.movieName = movieName;
	}

	public float getMovieAverageCriticReviews() {
		return movieAverageCriticReviews;
	}

	public void setMovieAverageCriticReviews(float movieAverageCriticReviews) {
		this.movieAverageCriticReviews = movieAverageCriticReviews;
	}

}
