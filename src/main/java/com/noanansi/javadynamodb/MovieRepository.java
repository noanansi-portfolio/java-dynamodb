package com.noanansi.javadynamodb;

import java.net.URI;
import java.util.Map;
import java.util.UUID;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
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

  private final DynamoDbTable<Movie> moviesTable =
      dynamoDbEnhancedClient.table("movies", TableSchema.fromBean(Movie.class));

  public Movie create(final String title, final Integer year, final Map<String, Object> info) {
    final var movie = new Movie();
    movie.setId(UUID.randomUUID().toString());
    movie.setTitle(title);
    movie.setYear(year);
    movie.setInfo(info);
    moviesTable.putItem(movie);
    return movie;
  }

}
