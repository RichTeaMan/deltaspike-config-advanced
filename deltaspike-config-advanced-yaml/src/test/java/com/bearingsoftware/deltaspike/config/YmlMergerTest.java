/**
 * (c) Copyright 2013 Jonathan Cobb This code is available under the Apache License, version 2:
 * http://www.apache.org/licenses/LICENSE-2.0.html
 */
package com.bearingsoftware.deltaspike.config;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Yaml;

import com.bearingsoftware.deltaspike.extension.config.yml.YmlMerger;

/**
 * Tests for {@link YmlMerger}.
 */
public class YmlMergerTest {

  /** The Constant LOG. */
  private static final Logger LOG = LoggerFactory.getLogger(YmlMergerTest.class);

  /** The Constant YML_1. */
  public static final String YML_1 = getResourceFile("test1.yml");

  /** The Constant YML_2. */
  public static final String YML_2 = getResourceFile("test2.yml");

  /** The Constant YML_NULL. */
  public static final String YML_NULL = getResourceFile("test-null.yml");

  /** The Constant YML_COLON. */
  public static final String YML_COLON = getResourceFile("test-colon.yml");

  /** The Constant YML_DATE. */
  public static final String YML_DATE = getResourceFile("test-date.yml");

  /** The Constant MERGEYML_1. */
  public static final String MERGEYML_1 = getResourceFile("testlistmerge1.yml");

  /** The Constant MERGEYML_2. */
  public static final String MERGEYML_2 = getResourceFile("testlistmerge2.yml");

  /** The yaml. */
  private final Yaml yaml = new Yaml();

  /** The merger. */
  private final YmlMerger merger = new YmlMerger();

  /**
   * Test merge 2 files.
   *
   * @throws Exception the exception.
   */
  @SuppressWarnings({"unchecked"})
  @Test
  public void testMerge2Files() throws Exception {
    final Map<String, Object> merged = merger.merge(new String[] {YML_1, YML_2});
    Map<String, Object> dbconfig;
    dbconfig = (Map<String, Object>) merged.get("database");
    assertEquals("wrong user", dbconfig.get("user"), "alternate-user");
    assertEquals("wrong db url", dbconfig.get("url"), "jdbc:mysql://localhost:3306/some-db");

    final String mergedYmlString = merger.toString(merged);
    LOG.info("resulting YML=\n" + mergedYmlString);
    final Map<String, Object> reloadedYaml = (Map<String, Object>) yaml.load(mergedYmlString);
    dbconfig = (Map<String, Object>) reloadedYaml.get("database");
    assertEquals("wrong user", dbconfig.get("user"), "alternate-user");
    assertEquals("wrong db url", dbconfig.get("url"), "jdbc:mysql://localhost:3306/some-db");
    final Map<String, Object> dbProperties = (Map<String, Object>) dbconfig.get("properties");
    assertEquals("wrong db url", dbProperties.get("hibernate.dialect"),
        "org.hibernate.dialect.MySQL5InnoDBDialect");
  }

  /**
   * Test merge file into self.
   *
   * @throws Exception the exception.
   */
  @SuppressWarnings({"unchecked"})
  @Test
  public void testMergeFileIntoSelf() throws Exception {
    final Map<String, Object> merged = merger.merge(new String[] {YML_1, YML_1});
    final Map<String, Object> dbconfig = (Map<String, Object>) merged.get("database");
    assertEquals("wrong user", dbconfig.get("user"), "some-user");
    assertEquals("wrong db url", dbconfig.get("url"), "jdbc:mysql://localhost:3306/some-db");
  }

  /**
   * Test null value.
   *
   * @throws Exception the exception.
   */
  @Test
  public void testNullValue() throws Exception {
    final Map<String, Object> merged = merger.merge(new String[] {YML_NULL});
    assertNotNull(merged.get("prop1"));
    assertNull(merged.get("prop2"));
  }

  /**
   * Test date value.
   *
   * @throws Exception the exception.
   */
  @Test
  public void testDateValue() throws Exception {
    final Map<String, Object> merged = merger.merge(new String[] {YML_DATE});
    assertNotNull(merged.get("date"));
  }

  /**
   * Test substitution value with colon.
   *
   * @throws Exception the exception.
   */
  @SuppressWarnings({"unchecked"})
  @Test
  public void testSubstitutionValueWithColon() throws Exception {
    final Map<String, Object> merged =
        new YmlMerger(Collections.singletonMap("ENV_VAR", "localhost"))
            .merge(new String[] {YML_COLON});
    final Map<String, Object> hash = (Map<String, Object>) merged.get("memcache");
    assertEquals(hash.get("one_key"), "value1");
    assertEquals(hash.get("another_key"), "localhost:22133");
    assertEquals(hash.get("some_other_key"), "value2");
  }

  /**
   * Test merge 2 lists.
   *
   * @throws Exception the exception.
   */
  @SuppressWarnings({"unchecked"})
  @Test
  public void testMerge2Lists() throws Exception {
    final Map<String, Object> merged = merger.merge(new String[] {MERGEYML_1, MERGEYML_2});
    final Map<String, Object> hash1 = (Map<String, Object>) merged.get("hashlevel1");
    final List<Object> list1 = (List<Object>) hash1.get("listlevel2");
    assertEquals("NotEnoughEntries", list1.size(), 2);
    final Map<String, Object> optionSet1 = (Map<String, Object>) list1.get(0);
    final Map<String, Object> optionSet2 = (Map<String, Object>) list1.get(1);
    assertEquals(optionSet1.get("namespace"), "namespace1");
    assertEquals(optionSet1.get("option_name"), "option1");
    assertEquals(optionSet2.get("namespace"), "namespace2");
    assertEquals(optionSet2.get("option_name"), "option2");
  }

  /**
   * Gets the resource file.
   *
   * @param file the file.
   * @return the resource file.
   */
  public static String getResourceFile(final String file) {
    return new File(System.getProperty("user.dir") + "/src/test/resources/" + file)
        .getAbsolutePath();
  }

}
