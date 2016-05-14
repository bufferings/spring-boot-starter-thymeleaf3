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

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;

import javax.annotation.PostConstruct;
import javax.servlet.Servlet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.template.TemplateLocation;
import org.springframework.boot.autoconfigure.thymeleaf.ThymeleafAutoConfiguration;
import org.springframework.boot.autoconfigure.web.ConditionalOnEnabledResourceChain;
import org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.util.MimeType;
import org.springframework.web.servlet.resource.ResourceUrlEncodingFilter;
import org.thymeleaf.Thymeleaf;
import org.thymeleaf.dialect.IDialect;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;

/**
 * Modify {@link ThymeleafAutoConfiguration} for Thymeleaf3.
 * 
 * @author Mitsuyuki Shiiba
 */
@Configuration
@EnableConfigurationProperties(Thymeleaf3Properties.class)
@ConditionalOnClass({ Thymeleaf.class, SpringTemplateEngine.class })
@AutoConfigureAfter(WebMvcAutoConfiguration.class)
public class Thymeleaf3AutoConfiguration {

  private static final Log logger = LogFactory.getLog(Thymeleaf3AutoConfiguration.class);

  @Configuration
  @ConditionalOnMissingBean(SpringResourceTemplateResolver.class)
  public static class SpringResourceTemplateResolverConfiguration {

    @Autowired
    private Thymeleaf3Properties properties;

    @Autowired
    private ApplicationContext applicationContext;

    @PostConstruct
    public void checkTemplateLocationExists() {
      boolean checkTemplateLocation = this.properties.isCheckTemplateLocation();
      if (checkTemplateLocation) {
        TemplateLocation location = new TemplateLocation(this.properties.getPrefix());
        if (!location.exists(this.applicationContext)) {
          logger.warn("Cannot find template location: " + location + " (please add some templates or check "
              + "your Thymeleaf configuration)");
        }
      }
    }

    @Bean
    public SpringResourceTemplateResolver springResourceTemplateResolver() {
      SpringResourceTemplateResolver resolver = new SpringResourceTemplateResolver();
      resolver.setApplicationContext(this.applicationContext);
      resolver.setPrefix(this.properties.getPrefix());
      resolver.setSuffix(this.properties.getSuffix());
      resolver.setTemplateMode(this.properties.getMode());
      if (this.properties.getEncoding() != null) {
        resolver.setCharacterEncoding(this.properties.getEncoding().name());
      }
      resolver.setCacheable(this.properties.isCache());
      Integer order = this.properties.getTemplateResolverOrder();
      if (order != null) {
        resolver.setOrder(order);
      }
      return resolver;
    }

  }

  @Configuration
  @ConditionalOnMissingBean(SpringTemplateEngine.class)
  protected static class SpringTemplateEngineConfiguration {

    @Autowired
    private Thymeleaf3Properties properties;

    @Autowired
    private final Collection<ITemplateResolver> templateResolvers = Collections.emptySet();

    @Autowired(required = false)
    private final Collection<IDialect> dialects = Collections.emptySet();

    @Bean
    public SpringTemplateEngine springTemplateEngine() {
      SpringTemplateEngine engine = new SpringTemplateEngine();
      engine.setEnableSpringELCompiler(this.properties.isEnableSpringELCompiler());
      for (ITemplateResolver templateResolver : this.templateResolvers) {
        engine.addTemplateResolver(templateResolver);
      }
      for (IDialect dialect : this.dialects) {
        engine.addDialect(dialect);
      }
      return engine;
    }

  }

  @Configuration
  @ConditionalOnClass({ Servlet.class })
  @ConditionalOnWebApplication
  protected static class ThymeleafViewResolverConfiguration {

    @Autowired
    private Thymeleaf3Properties properties;

    @Autowired
    private SpringTemplateEngine engine;

    @Bean
    @ConditionalOnMissingBean(name = "thymeleafViewResolver")
    @ConditionalOnProperty(name = "spring.thymeleaf.enabled", matchIfMissing = true)
    public ThymeleafViewResolver thymeleafViewResolver() {
      ThymeleafViewResolver resolver = new ThymeleafViewResolver();
      resolver.setTemplateEngine(this.engine);
      resolver.setCharacterEncoding(this.properties.getEncoding().name());
      resolver.setContentType(appendCharset(this.properties.getContentType(), resolver.getCharacterEncoding()));
      resolver.setExcludedViewNames(this.properties.getExcludedViewNames());
      resolver.setViewNames(this.properties.getViewNames());
      // This resolver acts as a fallback resolver (e.g. like a
      // InternalResourceViewResolver) so it needs to have low precedence
      resolver.setOrder(Ordered.LOWEST_PRECEDENCE - 5);
      resolver.setCache(this.properties.isCache());
      return resolver;
    }

    private String appendCharset(MimeType type, String charset) {
      if (type.getCharSet() != null) {
        return type.toString();
      }
      LinkedHashMap<String, String> parameters = new LinkedHashMap<String, String>();
      parameters.put("charset", charset);
      parameters.putAll(type.getParameters());
      return new MimeType(type, parameters).toString();
    }

  }

  @Configuration
  @ConditionalOnWebApplication
  protected static class ThymeleafResourceHandlingConfig {

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnEnabledResourceChain
    public ResourceUrlEncodingFilter resourceUrlEncodingFilter() {
      return new ResourceUrlEncodingFilter();
    }

  }

}