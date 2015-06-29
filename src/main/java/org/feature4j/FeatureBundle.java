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

import com.google.common.collect.ImmutableMap;

import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

public class FeatureBundle {


  private final Map<String, String> featureToVariants;

  public FeatureBundle(Map<String, String> featureToVariants) {
    this.featureToVariants = ImmutableMap.copyOf(featureToVariants);
  }

  public boolean enabled(String key, boolean defaultEnabled) {
    return get(key, Boolean::valueOf, defaultEnabled);
  }

  public Optional<Boolean> enabled(String key) {
    return get(key, Boolean::valueOf);
  }

  public String string(String key, String defaultString) {
    return get(key, String::toString, defaultString);
  }

  public Optional<String> string(String key) {
    return get(key, String::toString);
  }

  public int integer(String key, int defaultInt) {
    return get(key, Integer::valueOf, defaultInt);
  }

  public Optional<Integer> integer(String key) {
    return get(key, Integer::valueOf);
  }

  public float getFloat(String key, float defaultFloat) {
    return get(key, Float::valueOf, defaultFloat);
  }

  public Optional<Float> getFloat(String key) {
    return get(key, Float::valueOf);
  }

  public <T> T get(String key, Function<String, T> variantConverter, T defaultValue) {
    final Optional<T> value = get(key, variantConverter);
    return value.isPresent() ? value.get() : defaultValue;
  }

  public <T> Optional<T> get(String key, Function<String, T> variantConverter) {
    String variantString =  featureToVariants.get(key);

    T variant;
    try {
      variant = variantString != null ? variantConverter.apply(variantString) : null;
    } catch (Exception e) {
      variant = null;
    }
    return Optional.ofNullable((T) variant);
  }

  public Map<String, String> getFeatures() {
    return featureToVariants;
  }
}
