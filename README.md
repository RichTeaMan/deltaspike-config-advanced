![Build Status](https://api.travis-ci.org/chattersley/deltaspike-config-advanced.svg?branch=master)

# README #

There are two libraries that are provided, they both extend the configuration options provided by the DeltaSpike CDI extension:
* External Configuration
* YAML Extension

## Building the libraries

Clone the repo and install locally using gradle wrapper, this does not reside in a repository so must be built locally.

```
git clone *URL*
.\gradlew build publishToMavenLocal
```

## External Configuration Installation

The external configuration allows for internal property files to be overridden by a referenced external file.

To use the external configuration.

Add the dependency in Maven

```xml
<dependency>
    <groupId>com.bearingsoftware.deltaspike.advancedconfig</groupId>
    <artifactId>deltaspike-config-advanced-external</artifactId>
    <version>0.1.0</version>
</dependency>
```

...or in Gradle using `compile "com.bearingsoftware.deltaspike.advancedconfig:deltaspike-config-advanced-external:0.1.0"`.

Then create a `ConfigSourceProvider` by extending `com.bearingsoftware.deltaspike.config.ExternalFileConfigSourceProvider`. An example is provided as follows:

```java
import javax.enterprise.context.ApplicationScoped;

import com.bearingsoftware.deltaspike.config.ExternalFileConfigSourceProvider;

/**
 * Application configuration source provide.
 */
@ApplicationScoped
public class ApplicationExternalFileConfigSourceProvider extends ExternalFileConfigSourceProvider {

  /** Prefix. */
  private static final String PREFIX = "app";

  /** Property file name. */
  private static final String PROPERTY_FILE = "app.properties";

  /**
   * {@inheritDoc}.
   */
  @Override
  public String getPrefix() {
    return PREFIX;
  }

  /**
   * {@inheritDoc}.
   */
  @Override
  public String getConfigurationFolderKey() {
    return "my.values.properties.folder";
  }

  /**
   * {@inheritDoc}.
   */
  @Override
  public String getPropertyFile() {
    return PROPERTY_FILE;
  }
}
```

The external file is found through providing the location of the external folder as a system property when Java starts. An example is `-Dmy.values.properties.folder=/opt/my-application-properties`.

The default set of properties is provided in the resources folder in the file `app.properties`, another properties file is then included in `/opt/my-application-properties/app.properties` which overrides the values **only** if provided.

A property can then be addressed using the prefix specified in the provider. In the example `app` is given. To address a property with a given key of `important.url` the following snippet can be used.

```java
  /** The important URL. */
  @Inject
  @ConfigProperty(name = "$app{important.url}")
  private String importantUrl;
```

The following code snippet shows how to map a source type to a destination type:

```java
Mapping.from(Customer.class)
    .to(Person.class)
    .omitInSource(Customer::getAddress)
    .omitInDestination(Person::getBodyHeight)
    .reassign(Customer::getTitle)
        .to(Person::getSalutation)
    .replace(Customer::getGender, Person::getGender)
        .withSkipWhenNull(Gender::valueOf)
    .mapper();
```

### Who do I talk to? ###

* chattersley
* See deltaspike configuration for more details [DeltaSpike Configuration](https://deltaspike.apache.org/documentation/configuration.html)
