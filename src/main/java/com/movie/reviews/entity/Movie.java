package com.movie.reviews.entity;

public class Movie {

	private String movieId;

	private String movieName;

	private String movieReleasedYear;

	private String movieGenre;

	private int totalReviewedScores = 0;

	private int totalReviewedBy = 0;

	private int totalCriticReviewedScores = 0;

	private int totalCriticReviewedBy = 0;

	public Movie() {
		super();
	}

	public Movie(String movieId, String movieName, String movieReleasedYear, String movieGenre, int totalReviewedScores,
			int totalReviewedBy, int totalCriticReviewedScores, int totalCriticReviewedBy) {
		super();
		this.movieId = movieId;
		this.movieName = movieName;
		this.movieReleasedYear = movieReleasedYear;
		this.movieGenre = movieGenre;
		this.totalReviewedScores = totalReviewedScores;
		this.totalReviewedBy = totalReviewedBy;
		this.totalCriticReviewedScores = totalCriticReviewedScores;
		this.totalCriticReviewedBy = totalCriticReviewedBy;
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

	public String getMovieReleasedYear() {
		return movieReleasedYear;
	}

	public void setMovieReleasedYear(String movieReleasedYear) {
		this.movieReleasedYear = movieReleasedYear;
	}

	public String getMovieGenre() {
		return movieGenre;
	}

	public void setMovieGenre(String movieGenre) {
		this.movieGenre = movieGenre;
	}

	public int getTotalReviewedScores() {
		return totalReviewedScores;
	}

	public void setTotalReviewedScores(int totalReviewedScores) {
		this.totalReviewedScores = totalReviewedScores;
	}

	public int getTotalReviewedBy() {
		return totalReviewedBy;
	}

	public void setTotalReviewedBy(int totalReviewedBy) {
		this.totalReviewedBy = totalReviewedBy;
	}

	public int getTotalCriticReviewedScores() {
		return totalCriticReviewedScores;
	}

	public void setTotalCriticReviewedScores(int totalCriticReviewedScores) {
		this.totalCriticReviewedScores = totalCriticReviewedScores;
	}

	public int getTotalCriticReviewedBy() {
		return totalCriticReviewedBy;
	}

	public void setTotalCriticReviewedBy(int totalCriticReviewedBy) {
		this.totalCriticReviewedBy = totalCriticReviewedBy;
	}

}
