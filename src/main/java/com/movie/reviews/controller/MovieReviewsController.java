package com.movie.reviews.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.movie.reviews.exception.BadRequestException;
import com.movie.reviews.exception.ConflictException;
import com.movie.reviews.exception.ResourceNotFoundException;
import com.movie.reviews.projection.MovieProjection;
import com.movie.reviews.service.MovieReviewsService;
import com.movie.reviews.utils.ProjectConstants;

@RestController
@RequestMapping(value = "/v1")
public class MovieReviewsController {

	@Autowired
	private MovieReviewsService movieReviewsService;

	@PostMapping(value = "/user")
	public ResponseEntity<Map<String, String>> addUsers(@RequestBody(required = true) Map<String, String> request)
			throws BadRequestException {

		if (!request.containsKey(ProjectConstants.NAME) || request.get(ProjectConstants.NAME) == null) {
			throw new BadRequestException(ProjectConstants.NOT_VALID_REQUEST);
		}

		Map<String, String> response = movieReviewsService.addUsers(request.get(ProjectConstants.NAME));
		return new ResponseEntity<Map<String, String>>(response, HttpStatus.CREATED);

	}

	@PostMapping(value = "/movie")
	public ResponseEntity<Map<String, String>> addMovie(@RequestBody(required = true) Map<String, String> request)
			throws BadRequestException {

		if (!request.containsKey(ProjectConstants.NAME) || request.get(ProjectConstants.NAME) == null
				|| !request.containsKey(ProjectConstants.GENRE) || request.get(ProjectConstants.GENRE) == null
				|| !request.containsKey(ProjectConstants.YEAR) || request.get(ProjectConstants.YEAR) == null) {
			throw new BadRequestException(ProjectConstants.NOT_VALID_REQUEST);
		}

		Map<String, String> response = movieReviewsService.addMovie(request.get(ProjectConstants.NAME),
				request.get(ProjectConstants.GENRE), request.get(ProjectConstants.YEAR));
		return new ResponseEntity<Map<String, String>>(response, HttpStatus.CREATED);

	}

	@PostMapping(value = "/movie/review")
	public ResponseEntity<Map<String, String>> addReview(@RequestBody(required = true) Map<String, String> request)
			throws ConflictException, BadRequestException, ResourceNotFoundException {

		if (!request.containsKey(ProjectConstants.USER_ID) || request.get(ProjectConstants.USER_ID) == null
				|| !request.containsKey(ProjectConstants.MOVIE_ID) || request.get(ProjectConstants.MOVIE_ID) == null
				|| !request.containsKey(ProjectConstants.SCORE) || request.get(ProjectConstants.SCORE) == null) {
			throw new BadRequestException(ProjectConstants.NOT_VALID_REQUEST);
		}

		int score = Integer.parseInt(request.get(ProjectConstants.SCORE));
		if (score < 1 && score > 10) {
			throw new BadRequestException(ProjectConstants.NOT_VALID_REQUEST);
		}

		Map<String, String> response = movieReviewsService.addReview(request.get(ProjectConstants.USER_ID),
				request.get(ProjectConstants.MOVIE_ID), score);
		return new ResponseEntity<Map<String, String>>(response, HttpStatus.CREATED);

	}

	@GetMapping(value = "/movie/genre/{genre}")
	public ResponseEntity<Map<String, List<MovieProjection>>> getMoviesByTotalCriticReviewScoresAndGenre(
			@PathVariable(name = "genre", required = true) String genre,
			@RequestParam(name = "size", defaultValue = "10") int size) {

		Map<String, List<MovieProjection>> response = movieReviewsService
				.getMoviesByTotalCriticReviewScoresAndGenre(genre, size);
		return new ResponseEntity<Map<String, List<MovieProjection>>>(response, HttpStatus.OK);

	}

	@GetMapping(value = "/movie/year/{year}")
	public ResponseEntity<Map<String, Object>> getAverageReviewScoresByYear(
			@PathVariable(name = "year", required = true) String year) throws ResourceNotFoundException {

		Map<String, Object> response = movieReviewsService.getAverageReviewScoresByYear(year);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);

	}

	@GetMapping(value = "/movie/id/{movieId}")
	public ResponseEntity<Map<String, Object>> getAverageReviewScoresByMovie(
			@PathVariable(name = "movieId", required = true) String movieId) throws ResourceNotFoundException {

		Map<String, Object> response = movieReviewsService.getAverageReviewScoresByMovie(movieId);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);

	}

}
