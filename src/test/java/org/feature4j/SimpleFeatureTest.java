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
import com.google.common.collect.Iterables;
import com.google.common.collect.Range;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;

public class SimpleFeatureTest {

  private final String name = "name";
  private final String key = "key";
  private final String value = "value";
  private final String overrideValue = "override-value";
  private final Map<Range, String> overrides = ImmutableMap.<Range, String>of(
      Range.closed(1, 10), overrideValue
  );

  private VariantEvaluator variantEvaluator;

  @Before
  public void setUp() throws Exception {
  }

  @Test
  public void testConstructorWithoutConverterShouldReturnNoConvertedDefaultValue() {
    // GIVEN
    variantEvaluator = mock(VariantEvaluator.class);

    // WHEN
    Feature<String> feature = new SimpleFeature<>(name, key, value, ImmutableList.of(variantEvaluator));

    // THEN
    assertEquals(false, feature.converter().isPresent());
    assertEquals(name, feature.name());
    assertEquals(key, feature.key());
    assertEquals(value, feature.defaultValue());
    assertEquals(1, Iterables.size(feature.variantEvaluators()));

    assertEquals(false, feature.convertedDefaultValue().isPresent());
  }

  @Test
  public void testConstructorWithConverterShouldReturnConvertedDefaultValue() {
    // GIVEN
    variantEvaluator = mock(VariantEvaluator.class);

    // WHEN
    Feature<Integer> feature = new SimpleFeature<>(name, key, "5",
        ImmutableList.of(variantEvaluator),
        Optional.of(Integer::valueOf));

    // THEN
    assertEquals(true, feature.converter().isPresent());
    assertEquals(true, feature.convertedDefaultValue().isPresent());
    assertEquals(new Integer(5), feature.convertedDefaultValue().get());
  }

  @Test
  public void testConstructorWithConverterShouldReturnConvertedDefaultValueOnIllegalValue() {
    // GIVEN
    variantEvaluator = mock(VariantEvaluator.class);

    // WHEN
    Feature<Integer> feature = new SimpleFeature<>(name, key, "NaN",
        ImmutableList.of(variantEvaluator),
        Optional.of(Integer::valueOf));

    // THEN
    assertEquals(true, feature.converter().isPresent());
    assertEquals(false, feature.convertedDefaultValue().isPresent());
  }
}
