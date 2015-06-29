package org.feature4j.evaluators;

import org.feature4j.FeaturesContext;
import org.hamcrest.Matcher;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by dannwebster on 6/29/15.
 */
public class MatcherVariantEvaluator extends AbstractVariantEvaluator {

  private final Matcher matcher;

  public MatcherVariantEvaluator(Matcher matcher, String variantValue) {
    super(variantValue);
    this.matcher = checkNotNull(matcher, "matcher must be set to a non-null value");
  }

  @Override
  public boolean matches(FeaturesContext ctx) {
    return matcher.matches(ctx);
  }
}
