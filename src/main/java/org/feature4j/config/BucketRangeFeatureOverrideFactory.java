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
import org.feature4j.FeatureOverride;
import org.feature4j.MatcherFeatureOverride;
import org.hamcrest.Matcher;
import org.hamcrest.beans.HasPropertyWithValue;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.lessThanOrEqualTo;


public class BucketRangeFeatureOverrideFactory implements FeatureOverridesFactory {
  private static final Pattern RANGE_PATTERN = Pattern.compile("^\\s*(\\d+)\\s*(,\\s*(\\d+)\\s*)?$");

  @Override
  public Iterable<FeatureOverride> createOverrides(FeatureConfiguration featureConfiguration) {
    List<FeatureOverride> overrides = new ArrayList<>();
    for (Map.Entry<String, String> entry : featureConfiguration.getOverrides().entrySet()) {
      Optional<Range<Integer>> range = getRange(entry.getKey());
      if (range.isPresent()) {
        Matcher matcher = createRangeMatcher(range.get());
        FeatureOverride featureOverride = new MatcherFeatureOverride(matcher, entry.getValue());
        overrides.add(featureOverride);
      }
    }
    return overrides;
  }

  public Optional<Range<Integer>> getRange(String rangeString) {
    Optional<Range<Integer>> optRange = Optional.empty();
    if (rangeString != null && !rangeString.isEmpty()) {
      java.util.regex.Matcher regexMatcher = RANGE_PATTERN.matcher(rangeString);
      if (regexMatcher.matches()) {
        String lower = regexMatcher.group(1);
        String upper =  (regexMatcher.group(3) != null) ? regexMatcher.group(3) : lower;
        Range<Integer> range = Range.closed(Integer.valueOf(lower), Integer.valueOf(upper));
        optRange = Optional.of(range);
      }
    }
    return optRange;
  }

  public Matcher createRangeMatcher(Range<Integer> range) {
    return new HasPropertyWithValue("bucketId",
        allOf(
            greaterThanOrEqualTo(range.lowerEndpoint()),
            lessThanOrEqualTo(range.upperEndpoint())));
  }
}
