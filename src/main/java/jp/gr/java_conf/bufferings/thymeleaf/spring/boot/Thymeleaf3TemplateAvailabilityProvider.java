/*
 * Copyright 2012-2015 the original author or authors.
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
package jp.gr.java_conf.bufferings.thymeleaf.spring.boot;

import org.springframework.boot.autoconfigure.template.TemplateAvailabilityProvider;
import org.springframework.boot.autoconfigure.thymeleaf.ThymeleafProperties;
import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.core.env.Environment;
import org.springframework.core.env.PropertyResolver;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.ClassUtils;

/**
 * Modify {@link ThymeleafProperties} for Thymeleaf3.
 * 
 * @author Mitsuyuki Shiiba
 */
public class Thymeleaf3TemplateAvailabilityProvider implements TemplateAvailabilityProvider {

  @Override
  public boolean isTemplateAvailable(String view, Environment environment, ClassLoader classLoader,
      ResourceLoader resourceLoader) {
    if (ClassUtils.isPresent("org.thymeleaf.spring4.SpringTemplateEngine", classLoader)
        && ClassUtils.isPresent("org.thymeleaf.Thymeleaf", classLoader)) {
      PropertyResolver resolver = new RelaxedPropertyResolver(environment, "spring.thymeleaf3.");
      String prefix = resolver.getProperty("prefix", Thymeleaf3Properties.DEFAULT_PREFIX);
      String suffix = resolver.getProperty("suffix", Thymeleaf3Properties.DEFAULT_SUFFIX);
      return resourceLoader.getResource(prefix + view + suffix).exists();
    }
    return false;
  }

}
