package org.feature4j.evaluators;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSet;
import org.feature4j.FeaturesContext;

import java.util.Set;

public class DataValueVariantEvaluator extends AbstractVariantEvaluator {

  private final String dataKey;
  private final Set<String> dataValues;

  public DataValueVariantEvaluator(String dataKey, Set<String> dataValues, String variantValue) {
    super(variantValue);
    this.dataKey = Preconditions.checkNotNull(dataKey, "dataKey must be non-null");
    this.dataValues = ImmutableSet.copyOf(Preconditions.checkNotNull(dataValues, "dataValues must be non-null"));
  }

  @Override
  public boolean matches(FeaturesContext ctx) {
    String featureCtxDataValue =  ctx.getFeatureData().get(dataKey);
    return dataValues.contains(featureCtxDataValue);
  }


}
