package org.feature4j.config;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import org.feature4j.VariantEvaluator;

import java.util.Map;

public abstract class AbstractMappableVariantEvaluatorFactory implements VariantEvaluatorFactory {
  protected final String configKey;

  public AbstractMappableVariantEvaluatorFactory(String configKey) {
    this.configKey = Preconditions.checkNotNull(configKey);
  }

  public abstract Iterable<VariantEvaluator> doCreateVariantEvaluators(JsonArray variantConfigArray);

  @Override
  public final Iterable<VariantEvaluator> createVariantEvaluators(FeatureConfiguration featureConfiguration) {
    JsonElement variantConfigs = featureConfiguration.getVariants().get(configKey);
    return variantConfigs != null ?
        doCreateVariantEvaluators(variantConfigs.getAsJsonArray()) :
        ImmutableList.of();
  }



}
