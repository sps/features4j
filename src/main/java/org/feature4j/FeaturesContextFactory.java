package org.feature4j;

/**
 * This is how one can create a FeaturesContext from another type of data context, represented
 * by Type Parameter <T>. Examples of <T> could include include:
 * * HttpServletRequest
 * * Session object
 * * Vert.x/Yoke YokeRequest
 * * BatchContext in a Batch Program
 */
public interface FeaturesContextFactory<T> {
  FeaturesContext from(T data);
}
