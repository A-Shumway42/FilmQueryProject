package com.skilldistillery.filmquery.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.skilldistillery.filmquery.entities.Actor;
import com.skilldistillery.filmquery.entities.Film;

public class DatabaseAccessorObject implements DatabaseAccessor {
	private static final String URL = "jdbc:mysql://localhost:3306/sdvid?useSSL=false";
	private final String user = "student";
	private final String pass = "student";

	static {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.err.println("Error loading my SQL Driver!");
			e.printStackTrace();
		}
	}

	@Override
	public Film findFilmById(int filmId) {
		Film film = null;
		String sqltxt = "SELECT * FROM film WHERE id = ?;";
		try {
			Connection conn = DriverManager.getConnection(URL, user, pass);
			java.sql.PreparedStatement ps = conn.prepareStatement(sqltxt);
			ps.setInt(1, filmId);
			ResultSet filmResult = ps.executeQuery();
			if (filmResult.next()) {
				film = new Film();
				film.setFilmId(filmResult.getInt("id"));
				film.setTitle(filmResult.getString("title"));
				film.setDescription(filmResult.getString("description"));
				film.setReleaseYear(filmResult.getString("release_year"));
				film.setLanguageId(filmResult.getInt("language_id"));
				film.setRentalDuration(filmResult.getInt("rental_duration"));
				film.setRentalRate(filmResult.getDouble("rental_rate"));
				film.setLength(filmResult.getInt("length"));
				film.setReplacementCost(filmResult.getDouble("replacement_cost"));
				film.setRating(filmResult.getString("rating"));
				film.setSpecialFeatures(filmResult.getString("special_features"));
				film.setFilmLanguage(getLanguageForFilm(filmId));
				film.setActors(findActorsByFilmId(filmId));
			} else {
				System.out.println("That Film ID contains no data. Please select a Film ID" + " between 1 and 1000.");
			}
			filmResult.close();
			ps.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return film;
	}

	@Override
	public Actor findActorById(int actorId) {
		Actor actor = null;
		String sqltxt = "SELECT * FROM actor WHERE id = ?;";
		try {
			Connection conn = DriverManager.getConnection(URL, user, pass);
			java.sql.PreparedStatement ps = conn.prepareStatement(sqltxt);
			ps.setInt(1, actorId);
			ResultSet actorResult = ps.executeQuery();
			if (actorResult.next()) {
				actor = new Actor();
				actor.setActorId(actorResult.getInt("id"));
				actor.setFirstName(actorResult.getString("first_name"));
				actor.setLastName(actorResult.getString("last_name"));

			} else {
				System.out.println("That Actor ID contains no data. Please select an Actor ID" + " between 1 and 200.");
			}
			actorResult.close();
			ps.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return actor;
	}

	@Override
	public List<Actor> findActorsByFilmId(int filmId) {
		List<Actor> actors = new ArrayList<>();
		try {
			Connection conn = DriverManager.getConnection(URL, user, pass);
			String sqltxt = "SELECT DISTINCT actor.id, actor.first_name, actor.last_name"
					+ " FROM actor JOIN film_actor ON actor.id = film_actor.actor_id"
					+ " JOIN film ON film_actor.film_id = film_id" + " WHERE film_id = ?;";
			java.sql.PreparedStatement ps = conn.prepareStatement(sqltxt);
			ps.setInt(1, filmId);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				int actorId = rs.getInt("id");
				String firstName = rs.getString("first_name");
				String lastName = rs.getString("last_name");
				Actor actor = new Actor(actorId, firstName, lastName);
				actors.add(actor);
			}
			rs.close();
			ps.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return actors;
	}

	public List<Film> findFilmByKeyword(String keyword) {
		List<Film> films = new ArrayList<>();
		try {
			Connection conn = DriverManager.getConnection(URL, user, pass);
			String sqltxt = "SELECT * FROM film " + "WHERE title LIKE ? OR description LIKE ?;";
			java.sql.PreparedStatement ps = conn.prepareStatement(sqltxt);
			ps.setString(1, "%" + keyword + "%");
			ps.setString(2, "%" + keyword + "%");
			ResultSet filmResult = ps.executeQuery();
			if (filmResult.next()) {
				int filmId = filmResult.getInt("id");
				String title = filmResult.getString("title");
				String description = filmResult.getString("description");
				String releaseYear = filmResult.getString("release_year");
				int languageId = filmResult.getInt("language_id");
				int rentalDuration = filmResult.getInt("rental_duration");
				double rentalRate = filmResult.getDouble("rental_rate");
				int length = filmResult.getInt("length");
				double replacementCost = filmResult.getDouble("replacement_cost");
				String rating = filmResult.getString("rating");
				String specialFeatures = filmResult.getString("special_features");
				String filmLanguage = getLanguageForFilm(filmId);
				List<Actor> actor = findActorsByFilmId(filmId);
				Film film = new Film(filmId, title, description, releaseYear, languageId, rentalDuration, rentalRate,
						length, replacementCost, rating, specialFeatures, filmLanguage, actor);
				films.add(film);
			} else {
				System.out.println("That keyword does not appear in any films. Please enter another keyword.");
			}
			filmResult.close();
			ps.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return films;

	}

	public String getLanguageForFilm(int filmId) {
		String filmLanguage = "";
		String sqltxt = "SELECT language.name FROM language JOIN film ON film.language_id = language.id WHERE film.id = ?;";
		try {
			Connection conn = DriverManager.getConnection(URL, user, pass);
			java.sql.PreparedStatement ps = conn.prepareStatement(sqltxt);
			ps.setInt(1, filmId);
			ResultSet filmResult = ps.executeQuery();
			while (filmResult.next()) {
				filmLanguage = filmResult.getString(1);
			}
			filmResult.close();
			ps.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return filmLanguage;
	}
}
