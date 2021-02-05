package com.movie.reviews.entity;

import java.util.Set;

public class User {

	private String userName;

	private Set<String> movieReviews;

	public User() {
		super();
	}

	public User(String userName, Set<String> movieReviews) {
		super();
		this.userName = userName;
		this.movieReviews = movieReviews;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Set<String> getMovieReviews() {
		return movieReviews;
	}

	public void setMovieReviews(Set<String> movieReviews) {
		this.movieReviews = movieReviews;
	}

}
