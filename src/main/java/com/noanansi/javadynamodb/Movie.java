package com.noanansi.javadynamodb;

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;

import java.util.Map;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbConvertedBy;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSecondaryPartitionKey;

@DynamoDbBean
public class Movie {

  private String id;
  private Integer year;
  private String title;
  private Map<String, Object> info;

  @DynamoDbPartitionKey
  @DynamoDbAttribute(value = "id")
  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  @DynamoDbSecondaryPartitionKey(indexNames = "year-index")
  @DynamoDbAttribute(value = "year")
  public Integer getYear() {
    return year;
  }

  public void setYear(Integer year) {
    this.year = year;
  }

  @DynamoDbSecondaryPartitionKey(indexNames = "title-index")
  @DynamoDbAttribute(value = "title")
  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  @DynamoDbAttribute(value = "info")
  @DynamoDbConvertedBy(CustomMapAttributeConverter.class)
  public Map<String, Object> getInfo() {
    return info;
  }

  public void setInfo(Map<String, Object> info) {
    this.info = info;
  }
}
