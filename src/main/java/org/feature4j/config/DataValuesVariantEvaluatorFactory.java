package org.feature4j.config;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.feature4j.VariantEvaluator;
import org.feature4j.evaluators.DataValueVariantEvaluator;

import java.util.Map;
import java.util.Set;

public class DataValuesVariantEvaluatorFactory extends AbstractMappableVariantEvaluatorFactory {

  public DataValuesVariantEvaluatorFactory() {
    super("dataValues");
  }

  @Override
  public Iterable<VariantEvaluator> doCreateVariantEvaluators(JsonArray variantConfigs) {
    ImmutableList.Builder<VariantEvaluator> builder = ImmutableList.builder();
    for (JsonElement element : variantConfigs) {
      JsonObject dataVariant = element.getAsJsonObject();
      String dataKey = dataVariant.get("key").getAsString();
      Set<String> dataValues = getDataValues(dataVariant);
      String variantValue = dataVariant.get("variant").getAsString();
      VariantEvaluator evaluator = new DataValueVariantEvaluator(dataKey, dataValues, variantValue);
      builder.add(evaluator);
    }
    return builder.build();
  }

  Set<String> getDataValues(JsonObject dataVariant) {
    JsonArray dataArray = dataVariant.get("values").getAsJsonArray();
    ImmutableSet.Builder<String> builder = ImmutableSet.builder();
    for (JsonElement element : dataArray) {
      builder.add(element.getAsString());
    }
    return builder.build();
  }
}
