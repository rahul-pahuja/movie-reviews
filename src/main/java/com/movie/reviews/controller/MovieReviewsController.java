package com.movie.reviews.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.movie.reviews.service.MovieReviewsService;

@RestController
public class MovieReviewsController {

	@Autowired
	private MovieReviewsService movieReviewsService;

	public void addUsers() {
	}

	public void addMovie() {
	}

	public void addReviews() {
	}

	public void getMoviesByTotalCriticReviewScoresAndGenre() {
	}

	public void getAverageReviewScoresByYear() {
	}

	public void getAverageReviewScoresByMovie() {
	}
}
