# Thymeleaf 3 Spring Boot

A library for using Thymeleaf 3 with Spring Boot.
This is a temporary library until official release of it.

## Current Version

`0.0.1`

## Spec

* Versions
  * Thymeleaf 3.0.0.RELEASE
  * Thymeleaf Spring4 3.0.0.RELEASE
  * Spring Boot 1.3.5.RELEASE
* Property prefix
  * spring.thymeleaf3

* Thymeleaf Layout Dialect is NOT supported

## How to use

### 1 Modify pom.xml

1 Need to override `thymeleaf.version` of parent

```xml
    <thymeleaf.version>3.0.0.RELEASE</thymeleaf.version>
```

2 Add this repository

```xml
    <repository>
      <id>bufferings</id>
      <url>https://raw.github.com/bufferings/thymeleaf3-spring-boot/mvn-repo/</url>
    </repository>
```

3 Add dependency

```xml
    <dependency>
      <groupId>jp.gr.java_conf.bufferings</groupId>
      <artifactId>thymeleaf3-spring-boot</artifactId>
      <version>0.0.1</version>
    </dependency>
```

### 2 Exclude default ThymeleafAutoConfiguration

```java
@SpringBootApplication(exclude = ThymeleafAutoConfiguration.class)
```

## Samples

### Application

```java
@SpringBootApplication(exclude = ThymeleafAutoConfiguration.class)
public class DemoApplication {

  public static void main(String[] args) {
    SpringApplication.run(DemoApplication.class, args);
  }
}
```

### pom.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
  <modelVersion>4.0.0</modelVersion>

  <groupId>com.example</groupId>
  <artifactId>demo</artifactId>
  <version>0.0.1-SNAPSHOT</version>
  <packaging>jar</packaging>

  <name>demo</name>
  <description>Demo project for Spring Boot</description>

  <parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-parent</artifactId>
    <version>1.3.5.RELEASE</version>
    <relativePath /> <!-- lookup parent from repository -->
  </parent>

  <properties>
    <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    <java.version>1.8</java.version>

    <!-- For overriding parent version -->
    <thymeleaf.version>3.0.0.RELEASE</thymeleaf.version>
  </properties>

  <dependencies>
    <dependency>
      <groupId>jp.gr.java_conf.bufferings</groupId>
      <artifactId>thymeleaf3-spring-boot</artifactId>
      <version>0.0.1</version>
    </dependency>

    <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-test</artifactId>
      <scope>test</scope>
    </dependency>
  </dependencies>

  <build>
    <plugins>
      <plugin>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-maven-plugin</artifactId>
      </plugin>
    </plugins>
  </build>

  <repositories>
    <repository>
      <id>bufferings</id>
      <url>https://raw.github.com/bufferings/thymeleaf3-spring-boot/mvn-repo/</url>
    </repository>
  </repositories>

</project>
```

## License

[Apache 2.0 license](http://www.apache.org/licenses/LICENSE-2.0.html)
