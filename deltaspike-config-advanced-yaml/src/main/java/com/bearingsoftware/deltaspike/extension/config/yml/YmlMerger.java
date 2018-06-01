/**
 * (c) Copyright 2013-2015 Jonathan Cobb This code is available under the Apache License, version 2:
 * http://www.apache.org/licenses/LICENSE-2.0.html
 */
package com.bearingsoftware.deltaspike.extension.config.yml;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Yaml;

import com.github.mustachejava.DefaultMustacheFactory;

/**
 * YAML merger.
 */
public class YmlMerger {

  /** The Constant LOG. */
  private static final Logger LOG = LoggerFactory.getLogger(YmlMerger.class);

  /** The Constant DEFAULT_MUSTACHE_FACTORY. */
  public static final DefaultMustacheFactory DEFAULT_MUSTACHE_FACTORY =
      new DefaultMustacheFactory();

  /** The YAML. */
  private final Yaml yaml = new Yaml();

  /** The scope. */
  private final Map<String, Object> scope = new HashMap<String, Object>();;

  /**
   * Instantiates a new yml merger.
   */
  public YmlMerger() {
    init(System.getenv());
  }

  /**
   * Instantiates a new yml merger.
   *
   * @param env the env
   */
  public YmlMerger(final Map<String, String> env) {
    if (env != null)
      init(env);
  }

  /**
   * Inits the.
   *
   * @param env the env
   */
  private void init(final Map<String, String> env) {
    for (final String varname : env.keySet()) {
      scope.put(varname, env.get(varname));
    }
  }

  /**
   * Merge.
   *
   * @param files the files
   * @return the map
   * @throws IOException Signals that an I/O exception has occurred.
   */
  @SuppressWarnings("unchecked")
  public Map<String, Object> merge(final String[] files) throws IOException {
    final Map<String, Object> mergedResult = new LinkedHashMap<String, Object>();
    for (final String file : files) {
      InputStream in = null;
      try {
        // read the file into a String
        in = new FileInputStream(file);
        final String entireFile = IOUtils.toString(in, Charset.defaultCharset());

        // substitute variables
        final StringWriter writer = new StringWriter(entireFile.length() + 10);
        DEFAULT_MUSTACHE_FACTORY
            .compile(new StringReader(entireFile), "mergeyml_" + System.currentTimeMillis())
            .execute(writer, scope);

        // load the YML file
        final Map<String, Object> yamlContents = (Map<String, Object>) yaml.load(writer.toString());

        // merge into results map
        merge_internal(mergedResult, yamlContents);
        LOG.info("loaded YML from " + file + ": " + yamlContents);

      } finally {
        if (in != null)
          in.close();
      }
    }
    return mergedResult;
  }

  /**
   * Merge internal.
   *
   * @param mergedResult the merged result
   * @param yamlContents the yaml contents
   */
  @SuppressWarnings("unchecked")
  private void merge_internal(final Map<String, Object> mergedResult,
      final Map<String, Object> yamlContents) {

    if (yamlContents == null)
      return;

    for (final String key : yamlContents.keySet()) {

      final Object yamlValue = yamlContents.get(key);
      if (yamlValue == null) {
        addToMergedResult(mergedResult, key, yamlValue);
        continue;
      }

      final Object existingValue = mergedResult.get(key);
      if (existingValue != null) {
        if (yamlValue instanceof Map) {
          if (existingValue instanceof Map) {
            merge_internal((Map<String, Object>) existingValue, (Map<String, Object>) yamlValue);
          } else if (existingValue instanceof String) {
            throw new IllegalArgumentException(
                "Cannot merge complex element into a simple element: " + key);
          } else {
            throw unknownValueType(key, yamlValue);
          }
        } else if (yamlValue instanceof List) {
          mergeLists(mergedResult, key, yamlValue);

        } else if (yamlValue instanceof String || yamlValue instanceof Boolean
            || yamlValue instanceof Double || yamlValue instanceof Integer
            || yamlValue instanceof Date) {
          LOG.info("overriding value of " + key + " with value " + yamlValue);
          addToMergedResult(mergedResult, key, yamlValue);

        } else {
          throw unknownValueType(key, yamlValue);
        }

      } else {
        if (yamlValue instanceof Map || yamlValue instanceof List || yamlValue instanceof String
            || yamlValue instanceof Boolean || yamlValue instanceof Integer
            || yamlValue instanceof Double || yamlValue instanceof Date) {
          LOG.info("adding new key->value: " + key + "->" + yamlValue);
          addToMergedResult(mergedResult, key, yamlValue);
        } else {
          throw unknownValueType(key, yamlValue);
        }
      }
    }
  }

  /**
   * Unknown value type.
   *
   * @param key the key
   * @param yamlValue the yaml value
   * @return the illegal argument exception
   */
  private IllegalArgumentException unknownValueType(final String key, final Object yamlValue) {
    final String msg =
        "Cannot merge element of unknown type: " + key + ": " + yamlValue.getClass().getName();
    LOG.error(msg);
    return new IllegalArgumentException(msg);
  }

  /**
   * Adds the to merged result.
   *
   * @param mergedResult the merged result
   * @param key the key
   * @param yamlValue the yaml value
   * @return the object
   */
  private Object addToMergedResult(final Map<String, Object> mergedResult, final String key,
      final Object yamlValue) {
    return mergedResult.put(key, yamlValue);
  }

  /**
   * Merge lists.
   *
   * @param mergedResult the merged result
   * @param key the key
   * @param yamlValue the yaml value
   */
  @SuppressWarnings("unchecked")
  private void mergeLists(final Map<String, Object> mergedResult, final String key,
      final Object yamlValue) {
    if (!(yamlValue instanceof List && mergedResult.get(key) instanceof List)) {
      throw new IllegalArgumentException("Cannot merge a list with a non-list: " + key);
    }

    final List<Object> originalList = (List<Object>) mergedResult.get(key);
    originalList.addAll((List<Object>) yamlValue);
  }

  /**
   * Merge to string.
   *
   * @param files the files
   * @return the string
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public String mergeToString(final String[] files) throws IOException {
    return toString(merge(files));
  }

  /**
   * Merge to string.
   *
   * @param files the files
   * @return the string
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public String mergeToString(final List<String> files) throws IOException {
    return toString(merge(files.toArray(new String[files.size()])));
  }

  /**
   * To string.
   *
   * @param merged the merged
   * @return the string
   */
  public String toString(final Map<String, Object> merged) {
    return yaml.dump(merged);
  }

}
