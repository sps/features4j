/*
 * Copyright 2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.feature4j.config;

import com.google.common.collect.Range;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.annotations.JsonAdapter;
import org.feature4j.VariantEvaluator;
import org.feature4j.evaluators.RangeVariantEvaluator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;

import static org.hamcrest.Matchers.allOf;


public class BucketRangeVariantEvaluatorFactory extends AbstractMappableVariantEvaluatorFactory {
  public BucketRangeVariantEvaluatorFactory() {
    super("bucketIds");
  }

  @Override
  public Iterable<VariantEvaluator> doCreateVariantEvaluators(JsonArray variantConfig) {
    List<VariantEvaluator> overrides = new ArrayList<>();
    for (JsonElement entry : variantConfig) {
      JsonObject bucketRange = entry.getAsJsonObject();
      Optional<Range<Integer>> range = getRange(bucketRange);
      Optional<String> variant = getVariant(bucketRange);
      if (range.isPresent() && variant.isPresent()) {
        RangeVariantEvaluator rangeVariantEvaluator =
            new RangeVariantEvaluator(range.get(), variant.get());
        overrides.add(rangeVariantEvaluator);
      }
    }
    return overrides;
  }

  public Optional<String> getVariant(JsonObject bucketRange) {
    JsonElement variant = bucketRange.get("variant");
    return variant == null ? Optional.<String>empty() : Optional.of(variant.getAsString());
  }

  public Optional<Range<Integer>> getRange(JsonObject bucketRange) {
    Optional<Range<Integer>> optRange = Optional.empty();
    if (bucketRange != null && bucketRange.has("min") && bucketRange.has("max")) {
        int lower = bucketRange.get("min").getAsInt();
        int upper = bucketRange.get("max").getAsInt();
        Range<Integer> range = Range.closed(lower, upper);
        optRange = Optional.of(range);
    }
    return optRange;
  }


}
