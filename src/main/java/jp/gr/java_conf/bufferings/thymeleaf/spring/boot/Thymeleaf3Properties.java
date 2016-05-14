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

import java.nio.charset.Charset;

import org.springframework.boot.autoconfigure.thymeleaf.ThymeleafProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.util.MimeType;

import lombok.Getter;
import lombok.Setter;

/**
 * Modify {@link ThymeleafProperties} for Thymeleaf3.
 * 
 * Only added {@code enableSpringELCompiler} field.
 * 
 * @author Mitsuyuki Shiiba
 */
@Getter
@Setter
@ConfigurationProperties("spring.thymeleaf3")
public class Thymeleaf3Properties {

  private static final Charset DEFAULT_ENCODING = Charset.forName("UTF-8");

  private static final MimeType DEFAULT_CONTENT_TYPE = MimeType.valueOf("text/html");

  public static final String DEFAULT_PREFIX = "classpath:/templates/";

  public static final String DEFAULT_SUFFIX = ".html";

  private boolean checkTemplateLocation = true;

  private String prefix = DEFAULT_PREFIX;

  private String suffix = DEFAULT_SUFFIX;

  private String mode = "HTML";

  private Charset encoding = DEFAULT_ENCODING;

  private MimeType contentType = DEFAULT_CONTENT_TYPE;

  private boolean cache = true;

  private Integer templateResolverOrder;

  private String[] viewNames;

  private String[] excludedViewNames;

  private boolean enableSpringELCompiler = true;

  private boolean enabled = true;

}
