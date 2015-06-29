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

import com.google.common.collect.ImmutableList;
import com.google.gson.Gson;
import org.feature4j.Feature;
import org.feature4j.FeatureBundleProvider;
import org.feature4j.FeatureBundleProviderImpl;
import org.feature4j.FeatureOverride;
import org.feature4j.SimpleFeature;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Using json as a config file format is a bit brittle and prone to human error.
 */
public class JSONFeatureBundleProviderFactory implements FeatureBundleProviderFactory {

  private static final CompositeFeatureOverridesFactory DEFAULT_FACTORY =
      CompositeFeatureOverridesFactory.fromFactories(
        new BucketRangeFeatureOverrideFactory()
      );

  private final InputStream jsonResource;
  private final FeatureOverridesFactory featureOverridesFactory;

  public JSONFeatureBundleProviderFactory(InputStream jsonResource) {
    this(jsonResource, DEFAULT_FACTORY);
  }

  public JSONFeatureBundleProviderFactory(InputStream jsonResource, FeatureOverridesFactory featureOverridesFactory) {
    this.jsonResource = checkNotNull(jsonResource, "jsonResource must be non-null");
    this.featureOverridesFactory = checkNotNull(featureOverridesFactory, "featureOverridesFactory must be non-null");
  }

  @Override
  public FeatureBundleProvider create() throws IOException {

    final FeatureWrapper features = new Gson()
        .fromJson(new InputStreamReader(jsonResource, Charset.defaultCharset()),
            FeatureWrapper.class);

    final ImmutableList.Builder<Feature> listBuilder = ImmutableList.builder();

    for (FeatureConfiguration c : features.getFeatures()) {
      Iterable<FeatureOverride> featureOverrides = featureOverridesFactory.createOverrides(c);
      Feature feature = new SimpleFeature(c.getName(), c.getKey(), c.getValue(), featureOverrides);
      listBuilder.add(feature);
    }

    return new FeatureBundleProviderImpl(listBuilder.build());
  }

  static class FeatureWrapper {

    private List<FeatureConfiguration> features;

    public List<FeatureConfiguration> getFeatures() {
      return features;
    }
  }
}
