/**
 * Used the example code and YAML from SnakeYAML. Preventing CDI instantiation as don't want these
 * managed by CDI automatically.
 */
@Vetoed
package org.yaml.snakeyaml;

import javax.enterprise.inject.Vetoed;
