package com.movie.reviews.repo;

import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;

import org.springframework.stereotype.Repository;

import com.movie.reviews.entity.User;
import com.movie.reviews.exception.BadRequestException;
import com.movie.reviews.exception.ResourceNotFoundException;
import com.movie.reviews.utils.ProjectConstants;

@Repository
public class UsersRepository {

	private final Map<String, User> users = new ConcurrentHashMap<String, User>();

	public String addUser(String name) {

		User user = new User();
		user.setUserName(name);

		String userId = UUID.randomUUID().toString();
		users.put(userId, user);

		return userId;

	}

	public boolean addMovieReview(String userId, String movieId) throws BadRequestException, ResourceNotFoundException {

		isuserPresent(userId);

		User user = users.get(userId);

		if (user.getMovieReviews() == null) {
			user.setMovieReviews(new ConcurrentSkipListSet<String>());
		}

		Set<String> movieReviews = user.getMovieReviews();
		boolean isAdded = movieReviews.add(movieId);

		return isAdded;

	}

	public int getMovieReviewedCountByUser(String userId) {

		User user = users.get(userId);
		Set<String> movieReviews = user.getMovieReviews();

		int movieReviewsCount = movieReviews.size();
		return movieReviewsCount;

	}

	private void isuserPresent(String userId) throws ResourceNotFoundException {
		if (!users.containsKey(userId)) {
			throw new ResourceNotFoundException(ProjectConstants.USER_NOT_FOUND_MESSAGE);
		}
	}

}
