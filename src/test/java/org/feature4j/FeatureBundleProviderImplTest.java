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
import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class FeatureBundleProviderImplTest {

  private final FeatureOverride featureOverride = mock(FeatureOverride.class);
  private final Feature feature1 = new SimpleFeature("1", "one", "1", null);
  private final Feature feature2 = new SimpleFeature("1", "two", "0", ImmutableList.of(featureOverride));
  private FeatureBundleProvider featureProvider;


  @Before
  public void setUp() throws Exception {
    featureProvider = new FeatureBundleProviderImpl(ImmutableList.<Feature>of(feature1, feature2));
  }

  @Test
  public void testFeatureOverridesShouldBeIgnoredIfTheyReturnNull() throws Exception {
    when(featureOverride.extractFeatureValue(SimpleFeaturesContext.EMPTY)).thenReturn(null);
    FeatureBundle bundle = featureProvider.getFeatures(SimpleFeaturesContext.EMPTY);
    assertEquals(2, bundle.getFeatures().size());
    assertEquals("1", bundle.string("one", null));
    assertEquals("0", bundle.string("two", null));

  }
  @Test
  public void testFeatureOverridesShouldBeIgnoredIfTheyReturnAbsent() throws Exception {
    when(featureOverride.extractFeatureValue(SimpleFeaturesContext.EMPTY)).thenReturn(Optional.<String>empty());
    FeatureBundle bundle = featureProvider.getFeatures(SimpleFeaturesContext.EMPTY);
    assertEquals(2, bundle.getFeatures().size());
    assertEquals("1", bundle.string("one", null));
    assertEquals("0", bundle.string("two", null));
  }

  @Test
  public void testFeatureOverridesShouldBeUsedIfTheyReturnAValue() throws Exception {
    when(featureOverride.extractFeatureValue(SimpleFeaturesContext.EMPTY)).thenReturn(Optional.of("OVERRIDE"));
    FeatureBundle bundle = featureProvider.getFeatures(SimpleFeaturesContext.EMPTY);
    assertEquals("1", bundle.string("one", null));
    assertEquals("OVERRIDE", bundle.string("two", null));
  }
}
