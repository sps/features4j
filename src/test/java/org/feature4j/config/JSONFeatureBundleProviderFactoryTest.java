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

import org.feature4j.FeatureBundle;
import org.feature4j.FeatureBundleProvider;
import org.feature4j.SimpleFeaturesContext;

import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;
import java.util.Optional;

import static org.junit.Assert.assertEquals;

/*
  * this is a bit closer to an integration test than a unit test.
  * see: src/test/resources/test_features.json
 */
public class JSONFeatureBundleProviderFactoryTest {


  private static final SimpleFeaturesContext
      SESSION_3 =
      SimpleFeaturesContext.builder().bucketId(3).build();
  private static final SimpleFeaturesContext
      SESSION_99 =
      SimpleFeaturesContext.builder().bucketId(99).build();

  private FeatureBundleProviderFactory factory;
  private FeatureBundleProvider provider;

  @Before
  public void setUp() throws Exception {
    InputStream TEST_JSON = JSONFeatureBundleProviderFactoryTest.class.getResourceAsStream("/test_features.json");
    factory = new JSONFeatureBundleProviderFactory(TEST_JSON);
    provider = factory.create();
  }

  @Test
  public void testDefaultValueShouldBeUsedForBucket0() throws Exception {
    // GIVEN
    final String featureKey = "unit.test.one";
    SimpleFeaturesContext SESSION_0 = SimpleFeaturesContext.builder().bucketId(0).build();

    // WHEN
    FeatureBundle bundle = provider.getFeatures(SESSION_0);
    Optional<String> opt = bundle.string(featureKey);

    // THEN
    assertEquals(true, opt.isPresent());
    assertEquals("default-value", opt.get());
  }

  @Test
  public void testOverridesShouldBeUsedForBuckets1And2() throws Exception {
    // GIVEN
    final String featureKey = "unit.test.one";
    SimpleFeaturesContext SESSION_1 = SimpleFeaturesContext.builder().bucketId(1).build();
    SimpleFeaturesContext SESSION_2 = SimpleFeaturesContext.builder().bucketId(2).build();

    // WHEN
    FeatureBundle bundle1 = provider.getFeatures(SESSION_1);
    Optional<String> opt1 = bundle1.string(featureKey);
    FeatureBundle bundle2 = provider.getFeatures(SESSION_2);
    Optional<String> opt2 = bundle2.string(featureKey);

    // THEN
    assertEquals(true, opt1.isPresent());
    assertEquals("one-two", opt1.get());
    assertEquals(true, opt2.isPresent());
    assertEquals("one-two", opt2.get());
  }

    // assertEquals("one-two", provider.getFeatures(SESSION_1).string(featureKey).get());
    // assertEquals("one-two", provider.getFeatures(SESSION_2).string(featureKey).get());
    // assertEquals("three", provider.getFeatures(SESSION_3).string(featureKey).get());
    // assertEquals("default-value", provider.getFeatures(SESSION_99).string(featureKey).get());
    // assertEquals("true", provider.getFeatures(SESSION_0).string("unit.test.two").get());

  @Test
  public void testASessionWithMatchingDataShouldSeeVariantsThatMatch() throws Exception {
    // GIVEN
    SimpleFeaturesContext session = SimpleFeaturesContext.builder()
        .addFeatureData("eventId", "1")
        .build();
    final String featureKey = "unit.test.one";

    // WHEN
    FeatureBundle bundle = provider.getFeatures(session);

    // THEN
    assertEquals(true, bundle.string(featureKey).isPresent());
    assertEquals("five-six", bundle.string(featureKey).get());

  }

  @Test
  public void testFeatureWithMultipleMatchingVariantsShouldSelectForTheFirstMatchInConfigOrder()
      throws Exception {
    // GIVEN
     SimpleFeaturesContext bucketSelectedSession = SimpleFeaturesContext.builder()
         .bucketId(1)
        .addFeatureData("eventId", "1")
        .addFeatureData("artistId", "2")
        .build();
    SimpleFeaturesContext eventSelectedSession = SimpleFeaturesContext.builder()
        .addFeatureData("eventId", "1")
        .addFeatureData("artistId", "2")
        .build();
    SimpleFeaturesContext artistSelectedSession = SimpleFeaturesContext.builder()
        .addFeatureData("artistId", "2")
        .build();
    final String featureKey = "unit.test.one";


    // WHEN
    FeatureBundle bucketSelectedBundle = provider.getFeatures(bucketSelectedSession);
    FeatureBundle eventSelectedBundle = provider.getFeatures(eventSelectedSession);
    FeatureBundle artistSelectedBundle = provider.getFeatures(artistSelectedSession);

    // THEN
    assertEquals(true, bucketSelectedBundle.string(featureKey).isPresent());
    assertEquals("one-two", bucketSelectedBundle.string(featureKey).get());

    assertEquals(true, eventSelectedBundle.string(featureKey).isPresent());
    assertEquals("five-six", eventSelectedBundle.string(featureKey).get());

    assertEquals(true, artistSelectedBundle.string(featureKey).isPresent());
    assertEquals("seven-eight", artistSelectedBundle.string(featureKey).get());
  }
}
