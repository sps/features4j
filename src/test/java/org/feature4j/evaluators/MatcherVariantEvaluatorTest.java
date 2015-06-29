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
package org.feature4j.evaluators;

import junit.framework.TestCase;
import org.feature4j.SimpleFeaturesContext;
import org.hamcrest.Matcher;
import org.junit.Test;

import java.util.Optional;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MatcherVariantEvaluatorTest extends TestCase {

  Matcher matcher = mock(Matcher.class);

  @Test
  public void testNonMatchingFeatureShouldProvideOriginalValue() throws Exception {
    // GIVEN
    when(matcher.matches(any())).thenReturn(false);
    MatcherVariantEvaluator subject = new MatcherVariantEvaluator(matcher, "OVERRIDE");

    // WHEN
    Optional<String> opt = subject.evaluateVariant(SimpleFeaturesContext.EMPTY);

    // THEN
    assertEquals(false, opt.isPresent());


  }

  @Test
  public void testMatchingFeatureShouldProvidOverrideValue() throws Exception {
    // GIVEN
    when(matcher.matches(any())).thenReturn(true);
    MatcherVariantEvaluator subject = new MatcherVariantEvaluator(matcher, "OVERRIDE");

    // WHEN
    Optional<String> opt = subject.evaluateVariant(SimpleFeaturesContext.EMPTY);

    // THEN
    assertEquals(true, opt.isPresent());
    assertEquals("OVERRIDE", opt.get());

  }

}