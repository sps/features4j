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

import com.google.common.collect.Iterables;
import com.google.common.collect.Range;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import junit.framework.TestCase;
import org.feature4j.FeaturesContext;
import org.feature4j.SimpleFeaturesContext;
import org.feature4j.VariantEvaluator;
import org.feature4j.evaluators.RangeVariantEvaluator;
import org.hamcrest.Matcher;
import org.junit.Test;

import java.util.Iterator;
import java.util.Optional;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BucketRangeVariantEvaluatorFactoryTest extends TestCase {

  BucketRangeVariantEvaluatorFactory subject = new BucketRangeVariantEvaluatorFactory();
  JsonParser parser = new JsonParser();

  @Test
  public void testGetRangeInCorrectFormatShouldReturnRange() throws Exception {
    // Given
    JsonArray jsonArray = parser.parse("[{\"min\" : 3, \"max\" : 4, \"variant\" : \"foo\"}]")
        .getAsJsonArray();
    FeaturesContext session = SimpleFeaturesContext.builder().bucketId(3).build();

    // When
    Iterable<VariantEvaluator> evals = subject.doCreateVariantEvaluators(jsonArray);

    // Then
    Iterator<VariantEvaluator> it = evals.iterator();
    assertEquals(1, Iterables.size(evals));
    RangeVariantEvaluator eval = (RangeVariantEvaluator) it.next();
    assertEquals(true, eval.matches(session));
  }

  @Test
  public void testGetRangeWithTwoValuesInCorrectFormatShouldReturnRange() throws Exception {
    // Given
    JsonArray config = parser.parse("[" +
        "{\"min\" : 3, \"max\" : 4, \"variant\" : \"foo\"}," +
        "{\"min\" : 6, \"max\" : 7, \"variant\" : \"bar\"}," +
        "{}," + //garbage, ignore
        "{\"min\" : 6 }," + //garbage, ignore
        "{\"max\" : 6 }," +  //garbage, ignore
        "{\"min\" : 6, \"max\" : 6 }" +  //garbage, ignore
        "]")
        .getAsJsonArray();

    FeaturesContext sessionFoo = SimpleFeaturesContext.builder().bucketId(3).build();
    FeaturesContext sessionBar = SimpleFeaturesContext.builder().bucketId(6).build();

    // When
    Iterable<VariantEvaluator> evals = subject.doCreateVariantEvaluators(config);

    // Then
    Iterator<VariantEvaluator> it = evals.iterator();
    assertEquals(2, Iterables.size(evals));

    RangeVariantEvaluator evalFoo = (RangeVariantEvaluator) it.next();
    assertEquals(true, evalFoo.matches(sessionFoo));
    assertEquals(false, evalFoo.matches(sessionBar));

    RangeVariantEvaluator evalBar = (RangeVariantEvaluator) it.next();
    assertEquals(false, evalBar.matches(sessionFoo));
    assertEquals(true, evalBar.matches(sessionBar));
  }

}