package com.noanansi.javadynamodb;

import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class MovieRepositoryTest {

  @Test
  void givenValidData_whenCreatingMovie_thenSaved() {
    final var repository = new MovieRepository();
    final var actors = List.of(
        "Elijah Wood",
        "Ian McKellen",
        "Liv Tyler",
        "Viggo Mortensen",
        "Sean Astin",
        "Cate Blanchett",
        "John Rhys-Davies",
        "Billy Boyd",
        "Dominic Monaghan",
        "Orlando Bloom",
        "Christopher Lee",
        "Hugo Weaving",
        "Sean Bean",
        "Ian Holm",
        "Andy Serkis");
    final var info = Map.of("actors", actors, "director", "Peter Jackson");
    final var title = "The Fellowship of the Ring";
    final var year = 2001;
    final var createdMovie = repository.create(title, year, info);
    Assertions.assertNotNull(createdMovie.getId());
    Assertions.assertEquals(title, createdMovie.getTitle());
    Assertions.assertEquals(year, createdMovie.getYear());
  }

}