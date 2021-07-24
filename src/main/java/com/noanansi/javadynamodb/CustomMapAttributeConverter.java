package com.noanansi.javadynamodb;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.Map;
import software.amazon.awssdk.enhanced.dynamodb.AttributeConverter;
import software.amazon.awssdk.enhanced.dynamodb.AttributeValueType;
import software.amazon.awssdk.enhanced.dynamodb.EnhancedType;
import software.amazon.awssdk.enhanced.dynamodb.internal.converter.attribute.EnhancedAttributeValue;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

/**
 * Converter used to persist in DynamoDB a Map as String.
 */
public class CustomMapAttributeConverter implements AttributeConverter<Map<String, Object>> {

  private static Gson gson = new GsonBuilder()
      .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
      .create();

  @Override
  public AttributeValue transformFrom(Map<String, Object> input) {
    final var json = gson.toJson(input);
    return EnhancedAttributeValue.fromString(json).toAttributeValue();
  }

  @Override
  public Map<String, Object> transformTo(AttributeValue input) {
    return gson.fromJson(input.s(), Map.class);
  }

  @Override
  public EnhancedType<Map<String, Object>> type() {
    return EnhancedType.mapOf(String.class, Object.class);
  }

  @Override
  public AttributeValueType attributeValueType() {
    return AttributeValueType.S;
  }

}

