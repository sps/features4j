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

import com.google.common.collect.Range;
import junit.framework.TestCase;
import org.feature4j.FeaturesContext;
import org.hamcrest.Matcher;
import org.junit.Test;

import java.util.Optional;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BucketRangeFeatureOverrideFactoryTest extends TestCase {

  BucketRangeFeatureOverrideFactory subject = new BucketRangeFeatureOverrideFactory();

  @Test
  public void testGetRangeWithOneValueInCorrectFormatShouldReturnRange() throws Exception {
    // When
    Optional<Range<Integer>> range = subject.getRange("2");
    assertEquals(true, range.isPresent());
    assertEquals(Range.closed(2, 2), subject.getRange("2").get());
  }

  @Test
  public void testGetRangeWithTwoValuesInCorrectFormatShouldReturnRange() throws Exception {
    // When
    Optional<Range<Integer>> range = subject.getRange("2,50");
    assertEquals(true, range.isPresent());
    assertEquals(Range.closed(2, 50), range.get());
  }

  @Test
  public void testGetRangeOnWrongFormatsReturnsNoRange() throws Exception {
    assertEquals(false, subject.getRange(null).isPresent());
    assertEquals(false, subject.getRange("").isPresent());
    assertEquals(false, subject.getRange("2,X").isPresent());
    assertEquals(false, subject.getRange("X,2").isPresent());
    assertEquals(false, subject.getRange("X").isPresent());
    assertEquals(false, subject.getRange("2,3,4").isPresent());
    assertEquals(false, subject.getRange("2,3,4").isPresent());
  }

  @Test
  public void testRangeMatcherShouldCreateMatcherThatWillMatchFeatureContextRanges() throws Exception {
    // GIVEN
    FeaturesContext inContext = mock(FeaturesContext.class);
    FeaturesContext outContext = mock(FeaturesContext.class);

    when(inContext.getBucketId()).thenReturn(20);
    when(outContext.getBucketId()).thenReturn(50);


    // WHEN
    Matcher matcher = subject.createRangeMatcher(Range.closed(0, 20));

    // THEN
    assertEquals(true, matcher.matches(inContext));
    assertEquals(false, matcher.matches(outContext));


  }
}