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

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class FeatureBundleTest {

  @Test
  public void testValue() throws Exception {
    final FeatureBundle
        bundle = new FeatureBundle(ImmutableMap.<String, String>of(
        "foo", "1",
        "bar", "NaN",
        "int", "4",
        "truth", "true"));

    // truthiness
    assertTrue(bundle.enabled("truth").get());
    assertFalse(bundle.enabled("no.key", false));
    assertFalse(bundle.enabled("foo", false));

    // stringiness
    assertEquals(true, bundle.string("foo").isPresent());
    assertEquals("1", bundle.string("foo").get());
    assertEquals("true", bundle.string("truth", "false"));
    assertEquals("", bundle.string("no.key", ""));

    // integeriness
    assertEquals(1, bundle.integer("foo", 0));
    assertEquals(2, bundle.integer("xxx", 2));
    assertEquals(3, bundle.integer("bar", 3));
    assertEquals(4, bundle.integer("int", 0));
    assertEquals(true, bundle.integer("foo").isPresent());
    assertEquals(1, bundle.integer("foo").get().intValue());

    // floatiness
    assertEquals(1F, bundle.getFloat("foo", 0), 0.1F);
    assertEquals(2F, bundle.getFloat("xxx", 2), 0.1F);
    assertEquals(Float.NaN, bundle.getFloat("bar", 3), 0.1F);
    assertEquals(4F, bundle.getFloat("int", 0), 0.1F);
    assertEquals(true, bundle.getFloat("foo").isPresent());
    assertEquals(1F, bundle.getFloat("foo").get().intValue(), 0.1F);

    assertNotNull(bundle.getFeatures());
  }
}
