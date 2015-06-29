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

import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class FeatureConfigurationTest {

  @Test
  public void testCountingRocks() throws Exception {
    final FeatureConfiguration featureConfig = new FeatureConfiguration();
    assertNotNull(featureConfig.getVariants());
    featureConfig.setKey(null);
    featureConfig.setName(null);
    featureConfig.setType(null);
    featureConfig.setType(null);
    featureConfig.setVariants(null);
    featureConfig.setValue(null);
    assertNull(featureConfig.getKey());
    assertNull(featureConfig.getName());
    assertNull(featureConfig.getType());
    assertNull(featureConfig.getValue());
    assertNull(featureConfig.getVariants());
  }
}
