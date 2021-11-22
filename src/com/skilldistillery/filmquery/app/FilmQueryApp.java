package com.skilldistillery.filmquery.app;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import com.skilldistillery.filmquery.database.DatabaseAccessor;
import com.skilldistillery.filmquery.database.DatabaseAccessorObject;
import com.skilldistillery.filmquery.entities.Film;

public class FilmQueryApp {

	DatabaseAccessor db = new DatabaseAccessorObject();

	public static void main(String[] args) {
		FilmQueryApp app = new FilmQueryApp();

		app.launch();
	}

	private void launch() {
		Scanner input = new Scanner(System.in);

		startUserInterface(input);

		input.close();
	}

	private void startUserInterface(Scanner input) {

		boolean keepGoing = true;
		while (keepGoing) {
			try {
				createMenu();
				int choice = input.nextInt();
				input.nextLine();
				switch (choice) {
				case 1:
					searchFilmById(input);
					break;
				case 2:
					searchFilmByKeyword(input);
					break;
				case 3:
					System.out.println("Thank You! Closing Application!");
					keepGoing = false;
					break;
				default:
					System.out.println("Invalid Selection. Please choose options 1, 2 or 3.");
					break;
				}
			} catch (InputMismatchException e) {
				System.out.println("Please choose a valid option.");
				input.nextLine();
			}

		}

	}

	private void createMenu() {
		System.out.println("*****************************");
		System.out.println("*_______Film Query App______*");
		System.out.println("*    1. Search By Film-ID   *");
		System.out.println("*    2. Search By Keyword   *");
		System.out.println("*    3. Exit Application    *");
		System.out.println("*****************************");
	}

	private void searchFilmById(Scanner input) {
		System.out.println("Please enter a Film ID: ");
		int userIdInput = input.nextInt();
		Film film = db.findFilmById(userIdInput);
		if (film != null) {
			printFilm(film);
		}
	}

	private void searchFilmByKeyword(Scanner input) {
		System.out.println("Please enter a keyword to search for film information.");
		String userKwInput = input.nextLine();
		List<Film> film = db.findFilmByKeyword(userKwInput);
		for (int i = 0; i < film.size(); i++) {
			if (film != null) {
				printFilm(film.get(i));
			}
		}
	}

	private void printFilm(Film film) {
		System.out.println("The following information was found!");
		System.out.println("Title: " + film.getTitle() + "\n Release Year: " + film.getReleaseYear() + "\n Rating: \""
				+ film.getRating() + "\"\n Description: " + film.getDescription() + ".\n Language: "
				+ film.getFilmLanguage() + "\n Actors: " + film.getActors().toString().replace("[", " ").replace("]", "."));
	}
}
