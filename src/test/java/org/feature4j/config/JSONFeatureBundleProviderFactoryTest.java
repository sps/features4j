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

import org.feature4j.FeatureBundleProvider;
import org.feature4j.SimpleFeaturesContext;

import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;

import static org.junit.Assert.assertEquals;

/*
  * this is a bit closer to an integration test than a unit test.
  * see: src/test/resources/test_features.json
 */
public class JSONFeatureBundleProviderFactoryTest {

  private static final InputStream
      TEST_JSON =
      JSONFeatureBundleProviderFactoryTest.class.getResourceAsStream("/test_features.json");

  private static final SimpleFeaturesContext
      SESSION_0 =
      SimpleFeaturesContext.builder().bucketId(0).build();
  private static final SimpleFeaturesContext
      SESSION_1 =
      SimpleFeaturesContext.builder().bucketId(1).build();
  private static final SimpleFeaturesContext
      SESSION_2 =
      SimpleFeaturesContext.builder().bucketId(2).build();
  private static final SimpleFeaturesContext
      SESSION_3 =
      SimpleFeaturesContext.builder().bucketId(3).build();
  private static final SimpleFeaturesContext
      SESSION_99 =
      SimpleFeaturesContext.builder().bucketId(99).build();

  private FeatureBundleProviderFactory factory;

  @Before
  public void setUp() throws Exception {
    factory = new JSONFeatureBundleProviderFactory(TEST_JSON);
  }

  @Test
  public void testCreate() throws Exception {

    FeatureBundleProvider bundle = factory.create();

    final String featureKey = "unit.test.one";
    assertEquals("default-value", bundle.getFeatures(SESSION_0).getFeatures().get(featureKey));
    assertEquals("one-two", bundle.getFeatures(SESSION_1).getFeatures().get(featureKey));
    assertEquals("one-two", bundle.getFeatures(SESSION_2).getFeatures().get(featureKey));
    assertEquals("three", bundle.getFeatures(SESSION_3).getFeatures().get(featureKey));
    assertEquals("default-value", bundle.getFeatures(SESSION_99).getFeatures().get(featureKey));
    assertEquals("true", bundle.getFeatures(SESSION_0).getFeatures().get("unit.test.two"));
  }
}
