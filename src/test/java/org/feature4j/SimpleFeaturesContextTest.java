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
import junit.framework.TestCase;
import org.junit.Test;

public class SimpleFeaturesContextTest extends TestCase {

  @Test
  public void testEmptyContextShouldHaveBucket0() throws Exception {
    // WHEN
    FeaturesContext subject = SimpleFeaturesContext.EMPTY;

    // THEN
    assertEquals(0, subject.getBucketId());
    assertEquals(true, subject.getFeatureData().isEmpty());
    assertEquals(true, subject.getVariantOverrides().isEmpty());

  }

  @Test
  public void testBuilderShouldAllowAdditionOfFeatureData() throws Exception {
    // WHEN
    FeaturesContext subject = SimpleFeaturesContext.builder()
        .bucketId(1)
        .addFeatureData("key1", "value1")
        .addAllFeatureData(ImmutableMap.of("key2", "value2"))
        .build();

    // THEN
    assertEquals(1, subject.getBucketId());
    assertEquals(true, subject.getVariantOverrides().isEmpty());

    assertEquals(2, subject.getFeatureData().size());
    assertEquals("value1", subject.getFeatureData().get("key1"));
    assertEquals("value2", subject.getFeatureData().get("key2"));
  }

  @Test
  public void testBuilderShouldAllowAdditionOfVariantOverrides() throws Exception {
    // WHEN
    FeaturesContext subject = SimpleFeaturesContext.builder()
        .bucketId(1)
        .addVariantOverride("key1", "value1")
        .addAllVariantOverride(ImmutableMap.of("key2", "value2"))
        .build();

    // THEN
    assertEquals(1, subject.getBucketId());
    assertEquals(true, subject.getFeatureData().isEmpty());

    assertEquals(2, subject.getVariantOverrides().size());
    assertEquals("value1", subject.getVariantOverrides().get("key1"));
    assertEquals("value2", subject.getVariantOverrides().get("key2"));
  }

  @Test
  public void testBuilderShouldAllowCreationOfBucketId() throws Exception {
    // WHEN
    FeaturesContext subject = SimpleFeaturesContext.builder()
        .bucketId(1)
        .build();

    // THEN
    assertEquals(1, subject.getBucketId());
    assertEquals(true, subject.getFeatureData().isEmpty());
    assertEquals(true, subject.getVariantOverrides().isEmpty());

  }
}