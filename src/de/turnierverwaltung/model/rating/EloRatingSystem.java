package de.turnierverwaltung.model.rating;

/*
 * JOGRE (Java Online Gaming Real-time Engine) - API
 * Copyright (C) 2004  Bob Marks (marksie531@yahoo.com)
 * http://jogre.sourceforge.org
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */

/**
 * JOGRE's implementation of the ELO rating system. The following is an example
 * of how to use the Elo Rating System. <code>
 *   EloRatingSystem elo = new EloRatingSystem();
 *   int userRating = 1600;
 *   int opponentRating = 1650;
 *   int newUserRating = elo.getNewRating(userRating, opponentRating, WIN);
 *   int newOpponentRating = elo.getNewRating(opponentRating, userRating, LOSS);
 * </code>
 *
 * @author Garrett Lehman (gman)
 */
public class EloRatingSystem {

	public final static int SUPPORTED_PLAYERS = 2;

	// Score constants
	public final static double WIN = 1.0;
	public final static double DRAW = 0.5;
	public final static double LOSS = 0.0;

	/** Default ELO starting rating for new users. */
	public static final int DEFAULT_ELO_START_RATING = 1200;

	/**
	 * Default ELO, K is the development coefficient. (FIDE System) K = 30 for a
	 * player new to the rating list until he has completed events with a total of
	 * at least 30 games. K = 20 for RAPID and BLITZ ratings all players. K = 15 as
	 * long as a player`s rating remains under 2400. K = 10 once a player`s
	 * published rating has reached 2400, and he has also completed events with a
	 * total of at least 30 games. Thereafter it remains permanently at 10.
	 */
	public static final double ELO_K_FACTOR_NEWBIE = 30.0;
	public static final double ELO_K_FACTOR_BLITZ = 20.0;
	public static final double ELO_K_FACTOR_DOWN2400 = 15.0;
	public static final double ELO_K_FACTOR_UP2400 = 10.0;
	/** Default ELO k factor. */
	public static final double DEFAULT_ELO_K_FACTOR = 30.0;
	// List of singletons are stored in this HashMap
	// private static HashMap<String, Object> ratingSystems = null;

	/**
	 * Constructor to the JOGRE ELO rating system.
	 *
	 * @param game
	 *            Game to do the rating on as games may vary in their implementation
	 *            of ELO.
	 */
	public EloRatingSystem() {
	}

	/**
	 * Return instance of an ELO rating system.
	 *
	 * @param game
	 *            Game to key of.
	 * @return ELO rating system for specified game.
	 */
	// public static synchronized EloRatingSystem getInstance(String game) {
	// if (ratingSystems == null)
	// ratingSystems = new HashMap();
	//
	// // Retrieve rating system
	// Object ratingSystem = ratingSystems.get(game);
	//
	// // If null then create new one and add to hash keying off the game
	// if (ratingSystem == null) {
	// ratingSystem = new EloRatingSystem(game);
	// ratingSystems.put(game, ratingSystem);
	//
	// return (EloRatingSystem) ratingSystem;
	// } else
	// return (EloRatingSystem) ratingSystem;
	// }

	/**
	 * Convience overloaded version of getNewRating (int, int, double) which takes a
	 * result type and
	 *
	 * @param rating
	 * @param opponentRating
	 * @param resultType
	 * @param gameMode
	 *            This param for K Factor: gameMode=1 - Blitz mode gameMode=0 -
	 *            Standart game mode
	 * @param isNewbie
	 *            for a player new to the rating list until he has completed events
	 *            with a total of at least 30 games. true if <=30, false if > 30
	 * @return
	 */
	// public int getNewRating(int rating, int opponentRating, int resultType, int
	// gameMode, boolean isNewbie) {
	// switch (resultType) {
	// case GAME_WON:
	// return getNewRating(rating, opponentRating, WIN, gameMode, isNewbie);
	// case GAME_LOSS:
	// return getNewRating(rating, opponentRating, LOSS, gameMode, isNewbie);
	// case GAME_DRAW:
	// return getNewRating(rating, opponentRating, DRAW, gameMode, isNewbie);
	// }
	// return -1; // no score this time.
	// }

	/**
	 * Calculate the new rating based on the ELO standard formula. newRating =
	 * oldRating + constant * (score - expectedScore)
	 *
	 * @param oldRating
	 *            Old Rating
	 * @param score
	 *            Score
	 * @param expectedScore
	 *            Expected Score
	 * @param constant
	 *            Constant
	 * @return the new rating of the player
	 */
	private int calculateNewRating(final int oldRating, final double score, final double expectedScore,
			final double kFactor) {
		return oldRating + (int) (kFactor * (score - expectedScore));
	}

	/**
	 * Get expected score based on two players. If more than two players are
	 * competing, then opponentRating will be the average of all other opponent's
	 * ratings. If there is two teams against each other, rating and opponentRating
	 * will be the average of those players.
	 *
	 * @param rating
	 *            Rating
	 * @param opponentRating
	 *            Opponent(s) rating
	 * @return the expected score
	 */
	private double getExpectedScore(final int rating, final int opponentRating) {
		return 1.0 / (1.0 + Math.pow(10.0, ((opponentRating - rating) / 400.0)));
	}

	/**
	 * This is the standard chess constant. This constant can differ based on
	 * different games. The higher the constant the faster the rating will grow.
	 * That is why for this standard chess method, the constant is higher for weaker
	 * players and lower for stronger players.
	 *
	 * @param rating
	 *            Rating
	 * @return Constant
	 */
	private double getKFactor(final int rating, final boolean isNewbie) {

		if (isNewbie) {
			return ELO_K_FACTOR_NEWBIE;

		}
		if (rating < 2400) {
			return ELO_K_FACTOR_DOWN2400;
		}
		if (rating >= 2400) {
			return ELO_K_FACTOR_UP2400;
		}
		return DEFAULT_ELO_K_FACTOR;
	}

	/**
	 * Get new rating.
	 *
	 * @param rating
	 *            Rating of either the current player or the average of the current
	 *            team.
	 * @param opponentRating
	 *            Rating of either the opponent player or the average of the
	 *            opponent team or teams.
	 * @param score
	 *            Score: 0=Loss 0.5=Draw 1.0=Win
	 * @param gameMode
	 *            This param for K Factor: gameMode=1 - Blitz mode gameMode=0 -
	 *            Standart game mode
	 * @param isNewbie
	 *            for a player new to the rating list until he has completed events
	 *            with a total of at least 30 games. true if <=30, false if > 30
	 * @return the new rating
	 */
	public int getNewRating(final int rating, final int opponentRating, final double score, final boolean isNewbie) {
		final double kFactor = getKFactor(rating, isNewbie);
		final double expectedScore = getExpectedScore(rating, opponentRating);
		final int newRating = calculateNewRating(rating, score, expectedScore, kFactor);

		return newRating;
	}
}