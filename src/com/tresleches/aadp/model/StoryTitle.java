package com.tresleches.aadp.model;

import com.tresleches.aadp.model.Story.Type;

/**
 * Class to find StoryTitle
 * @author Parag Sagar
 *
 */

public class StoryTitle {

	public static final String STORIES = "Stories";
	private static final String IN_LOVING_MEMORY = "In Loving Memory";
	private static final String SURVIVOR_STORIES = "Survivor Stories";
	private static final String DONOR_HEROES = "Donor Heroes";
	private static final String SEARCHING_FOR_DONORS = "Searching for Donors";

	public static String getTitle(Type type) {
		switch (type) {
		case SEARCHING:
			return SEARCHING_FOR_DONORS;
		case DONOR:
			return DONOR_HEROES;
		case SURVIVOR:
			return SURVIVOR_STORIES;
		case IN_LOVING_MEMORY:
			return IN_LOVING_MEMORY;

		default:
			return STORIES;
		}
	}

}
