package org.feature4j.evaluators;

import com.google.common.base.Preconditions;
import com.google.common.collect.Range;
import org.feature4j.FeaturesContext;
import org.feature4j.VariantEvaluator;
import org.hamcrest.Matcher;
import org.hamcrest.beans.HasPropertyWithValue;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.lessThanOrEqualTo;

/**
 * Created by dannwebster on 6/29/15.
 */
public class RangeVariantEvaluator extends AbstractVariantEvaluator {
  private final Range<Integer> range;
  public RangeVariantEvaluator(Range<Integer> range, String variantValue) {
    super(variantValue);
    this.range = Preconditions.checkNotNull(range, "range must be non-null");
  }

  @Override
  public boolean matches(FeaturesContext ctx) {
    return range.contains(ctx.getBucketId());
  }
}
