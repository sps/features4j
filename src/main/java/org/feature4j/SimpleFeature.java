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
import com.google.common.collect.Range;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import static com.google.common.base.MoreObjects.firstNonNull;

public class SimpleFeature<T> implements Feature<T> {

  private final List<FeatureOverride> overrides;

  private final String defaultValue;

  private final String name;
  private final String key;
  private final Optional<Function<String, T>> converter;

  public SimpleFeature(String name,
                       String key,
                       String defaultValue,
                       Iterable<FeatureOverride> overrides,
                       Optional<Function<String, T>> converter) {
    this.name = name;
    this.key = key;
    this.defaultValue = defaultValue;
    this.overrides = ImmutableList.copyOf(firstNonNull(overrides, ImmutableList.<FeatureOverride>of()));
    this.converter = converter;
  }

  public SimpleFeature(String name,
                       String key,
                       String defaultValue,
                       Iterable<FeatureOverride> overrides) {
    this(name, key, defaultValue, overrides, Optional.empty());
  }

  @Override
  public String key() {
    return key;
  }

  @Override
  public String name() {
    return name;
  }

  @Override
  public String defaultValue() {
    return defaultValue;
  }

  @Override
  public Optional<Function<String, T>> converter() {
    return converter;
  }

  @Override
  public Optional<T> convertedDefaultValue() {
    T convertedDefaultValue;
    try {
      convertedDefaultValue = converter.isPresent() ?
          converter.get().apply(defaultValue) :
          null;
    } catch (Exception e) {
      convertedDefaultValue = null;
    }
    return Optional.ofNullable(convertedDefaultValue);
  }

  @Override
  public Iterable<FeatureOverride> overrides() {
    return overrides;
  }
}
