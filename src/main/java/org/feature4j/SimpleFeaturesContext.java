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
package org.feature4j;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import java.util.Arrays;
import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;

public class SimpleFeaturesContext implements FeaturesContext {

  private int bucketId;
  private Map<String, String> featureData;
  private Map<String, String> variantOverrides;

  public static final SimpleFeaturesContext EMPTY = new Builder().bucketId(0).build();

  private SimpleFeaturesContext() {
  }

  public static Builder builder() {
    return new Builder();
  }

  @Override
  public int getBucketId() {
    return bucketId;
  }

  @Override
  public Map<String, String> getFeatureData() {
    return featureData;
  }

  @Override
  public Map<String, String> getVariantOverrides() {
    return variantOverrides;
  }

  public static class Builder {

    private final SimpleFeaturesContext context = new SimpleFeaturesContext();
    private final ImmutableMap.Builder<String, String> featureDataBuilder =
        ImmutableMap.builder();
    private final ImmutableMap.Builder<String, String> variantOverridesBuilder =
        ImmutableMap.builder();

    public SimpleFeaturesContext build() {
      checkNotNull(context.bucketId);
      context.featureData = featureDataBuilder.build();
      context.variantOverrides = variantOverridesBuilder.build();
      return context;
    }

    public Builder addAllVariantOverride(Map<String, String> variantOverrides) {
      variantOverridesBuilder.putAll(variantOverrides);
      return this;
    }

    public Builder addVariantOverride(String variantKey, String variantValue) {
      variantOverridesBuilder.put(variantKey, variantValue);
      return this;
    }

    public Builder addFeatureData(String dataKey, String dataValue) {
      featureDataBuilder.put(dataKey, dataValue);
      return this;
    }

    public Builder addAllFeatureData(Map<String, String> featureData) {
      featureDataBuilder.putAll(featureData);
      return this;
    }

    public Builder bucketId(int bucketId) {
      context.bucketId = bucketId;
      return this;
    }
  }
}
