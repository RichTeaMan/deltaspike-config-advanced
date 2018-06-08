![Build Status](https://api.travis-ci.org/chattersley/deltaspike-config-advanced.svg?branch=master)

# README #

There are two libraries that are provided, they both extend the configuration options provided by the DeltaSpike CDI extension:
* External Configuration
* YAML Extension

## External configuration ##

The external configuration library allows you to create a default internal properties file that can be overridden with an external properties file referenced through a system property. In addition the library gives you the ability to address property files with a prefix, this allows you to create property files with the same key.

## YAML extension ##
Although not utilising the DeltaSpike configuration mechanism the YAML extension allows you to inject managed classes being built by a YAML file. This class can then be utilised as property values through injection.

## Instructions ##
Please see the [Wiki](https://github.com/chattersley/deltaspike-config-advanced/wiki) for instructions on how to build and configure.

### Who do I talk to? ###

* chattersley
* See DeltaSpike configuration for more details [DeltaSpike Configuration](https://deltaspike.apache.org/documentation/configuration.html)
