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

  @Test
  void givenExistingTitle_whenFindingByTitle_thenRecordFound() {
    final var title = "The Fellowship of the Ring";
    final var repository = new MovieRepository();
    final var queryResult = repository.findByTitle(title);
    Assertions.assertFalse(queryResult.isEmpty());
    Assertions.assertEquals(title, queryResult.get(0).getTitle());
    Assertions.assertEquals(2001, queryResult.get(0).getYear());
  }

  @Test
  void givenExistingMovie_whenUpdating_thenRecordChanged() {
    final var title = "The Fellowship of the Ring";
    final var repository = new MovieRepository();
    final var queryResult = repository.findByTitle(title);
    final var movie = queryResult.get(0);
    movie.setYear(2000);
    final var updatedMovie = repository.update(movie);
    Assertions.assertEquals(2000, updatedMovie.getYear());
  }

  @Test
  void givenExistingMovieId_whenFinding_thenRecordReturned() {
    final var id = "033f2496-d906-4691-b39a-6ecd229de365";
    final var repository = new MovieRepository();
    final var result = repository.findById(id);
    Assertions.assertTrue(result.isPresent());
    Assertions.assertEquals(id, result.get().getId());
  }

  @Test
  void givenExistingMovieId_whenDeleting_thenRecordExcluded() {
    final var id = "033f2496-d906-4691-b39a-6ecd229de365";
    final var repository = new MovieRepository();
    final var result = repository.delete(id);
    Assertions.assertTrue(result.isPresent());
    Assertions.assertEquals(id, result.get().getId());
  }

}