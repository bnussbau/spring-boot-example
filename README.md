MongoDB docker
--------------

```sh
docker run --name mongo-0 -p 27017:27017 -d mongo:4.2.1
```

Clone & Compile vinnsl schema
-----------------------------

```sh
git clone https://github.com/a00908270/vinnsl-schema.git    
cd vinnsl-schema
mvn package
```

Create new Project (maven.pom)
------------------------------

```xml
<?xml version="1.0" encoding="UTF-8" standalone="no"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>at.ac.univie.a0908270.nncloud</groupId>
    <artifactId>vinnsl-service</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <packaging>jar</packaging>

    <name>vinnsl-service</name>
    <description>Service to import/export vinnslDefinition xml files</description>

    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>1.5.8.RELEASE</version>
        <relativePath/> <!-- lookup parent from repository -->
    </parent>

    <properties>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>
        <java.version>1.8</java.version>
    </properties>

    <dependencies>
        <dependency>
            <groupId>at.ac.univie.a00908270</groupId>
            <artifactId>vinnsl-schema</artifactId>
            <version>1.0-SNAPSHOT</version>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-mongodb</artifactId>
        </dependency>

        <dependency>
            <groupId>org.json</groupId>
            <artifactId>json</artifactId>
            <version>20180130</version>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-actuator</artifactId>
        </dependency>

        <dependency>
            <groupId>io.springfox</groupId>
            <artifactId>springfox-swagger2</artifactId>
            <version>2.8.0</version>
        </dependency>

        <dependency>
            <groupId>io.springfox</groupId>
            <artifactId>springfox-swagger-ui</artifactId>
            <version>2.8.0</version>
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

</project>
```

Spring Application Swagger Config
---------------------------------

```java
@EnableSwagger2
public class SwaggerConfig {
   @Bean
   public Docket api() {
      return new Docket(DocumentationType.SWAGGER_2)
            .select()
            .apis(RequestHandlerSelectors.basePackage("at.ac.univie.a0908270.nncloud.vinnsl"))
            .paths(PathSelectors.any())
            .build()
            .apiInfo(apiInfo());
   }

   private ApiInfo apiInfo() {
      return new ApiInfo(
            "ViNNSL Service",
            "Webservice to view, import, train and evaluate ViNNSL Networks",
            "0.0.1-SNAPSHOT",
            null,
            new Contact("a00908270", "https://a00908270.github.io", "a00908270@unet.univie.ac.at"),
            "MIT", "https://github.com/a00908270/vinnsl-nn-cloud/LICENSE", Collections.emptyList());
   }
}
```

resources/application.properties
----------------------

```
spring.data.mongodb.uri=mongodb://localhost:27017/nn
logging.level.org.springframework.web=DEBUG
management.security.enabled=false
```

Vinnsl Tempate
--------------

```xml
<vinnsl>
  <description>
    <identifier></identifier>
    <metadata>
      <paradigm>Backpropagation Network</paradigm>
      <name>New Backpropagration Network</name>
      <description>New Backpropagration Network</description>
      <version>
        <major>100</major>
        <minor>100</minor>
      </version>
    </metadata>
    <creator>
      <name>Vinnsl NN REST SERVICE</name>
      <contact>a00908270</contact>
    </creator>
    <problemDomain>
      <propagationType type="feedforward">
        <learningType>supervised</learningType>
      </propagationType>
      <networkType>Backpropagation</networkType>
      <problemType></problemType>
    </problemDomain>
    <endpoints>
      <train>true</train>
      <retrain>false</retrain>
      <evaluate>false</evaluate>
    </endpoints>
  </description>
</vinnsl>
```

Swagger UI
----------

<http://localhost:8080/swagger-ui.html>