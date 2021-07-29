package com.noanansi.javadynamodb;

import java.net.URI;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

public class MovieRepository {

  private final DynamoDbClient dynamoDbClient = DynamoDbClient.builder()
      .region(Region.of(System.getenv("DYNAMODB_REGION")))
      .endpointOverride(URI.create(System.getenv("DYNAMODB_ENDPOINT")))
      .credentialsProvider(DefaultCredentialsProvider.builder().build())
      .build();

  private final DynamoDbEnhancedClient dynamoDbEnhancedClient = DynamoDbEnhancedClient.builder()
      .dynamoDbClient(dynamoDbClient)
      .build();

  private final String TABLE_NAME = "movies";
  private final String TITLE_INDEX = "title-index";
  private final DynamoDbTable<Movie> moviesTable =
      dynamoDbEnhancedClient.table(TABLE_NAME, TableSchema.fromBean(Movie.class));

  public Movie create(final String title, final Integer year, final Map<String, Object> info) {
    final var movie = new Movie();
    movie.setId(UUID.randomUUID().toString());
    movie.setTitle(title);
    movie.setYear(year);
    movie.setInfo(info);
    moviesTable.putItem(movie);
    return movie;
  }

  public Optional<Movie> findById(final String id) {
    final var movie = moviesTable.getItem(builder -> builder.key(key -> key.partitionValue(id)));
    return Optional.ofNullable(movie);
  }

  public List<Movie> findByTitle(final String title) {
    final var titleIndex = moviesTable.index(TITLE_INDEX);
    final var condition =
        QueryConditional.keyEqualTo(builder -> builder.partitionValue(title));
    final var queryResults = titleIndex.query(builder -> builder.queryConditional(condition));
    return queryResults.stream()
        .map(moviePage -> moviePage.items())
        .flatMap(Collection::stream)
        .collect(Collectors.toList());
  }

  public Movie update(final Movie movie) {
    return moviesTable.updateItem(movie);
  }

  public Optional<Movie> delete(final String id) {
    return Optional.ofNullable(
        moviesTable.deleteItem
            (Key.builder().partitionValue(id).build()));
  }

}
