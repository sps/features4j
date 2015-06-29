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

import com.google.gson.JsonElement;

import java.util.Collections;
import java.util.Map;

public class FeatureConfiguration {

  private String name;
  private String key;
  private String type;
  private String value;

  private Map<String, JsonElement> variants = Collections.emptyMap();

  public String getName() {
    return name;
  }

  public void setName(
      String name) {
    this.name = name;
  }

  public String getKey() {
    return key;
  }

  public void setKey(String key) {
    this.key = key;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  public Map<String, JsonElement> getVariants() {
    return variants;
  }

  public void setVariants(Map<String, JsonElement> variants) {
    this.variants = variants;
  }
}
