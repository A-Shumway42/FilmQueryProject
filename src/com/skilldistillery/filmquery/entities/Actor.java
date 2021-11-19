package com.skilldistillery.filmquery.entities;

import java.util.List;
import java.util.Objects;

public class Actor {
	private int ActorId;
	private String firstName;
	private String lastName;
	private List<Film> films;

	public Actor() {
	}

	public Actor(int actorId, String firstName, String lastName, List<Film> films) {
		super();
		ActorId = actorId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.films = films;
	}

	public int getActorId() {
		return ActorId;
	}

	public void setActorId(int actorId) {
		ActorId = actorId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public List<Film> getFilms() {
		return films;
	}

	public void setFilms(List<Film> films) {
		this.films = films;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Actor [ActorId=");
		builder.append(ActorId);
		builder.append(", firstName=");
		builder.append(firstName);
		builder.append(", lastName=");
		builder.append(lastName);
		builder.append(", films=");
		builder.append(films);
		builder.append("]");
		return builder.toString();
	}

	@Override
	public int hashCode() {
		return Objects.hash(ActorId, films, firstName, lastName);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Actor other = (Actor) obj;
		return ActorId == other.ActorId && Objects.equals(films, other.films)
				&& Objects.equals(firstName, other.firstName) && Objects.equals(lastName, other.lastName);
	}

}
