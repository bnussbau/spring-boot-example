package at.ac.univie.a0908270.nncloud.vinnsl;

import at.ac.univie.a0908270.nncloud.db.NeuronalNetworkRepository;
import at.ac.univie.a0908270.nncloud.db.Vinnsl;
import com.mongodb.MongoClientOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@SpringBootApplication
@EnableMongoRepositories({"at.ac.univie.a0908270.nncloud.db"})
public class VinnslServiceApplication {

	@Autowired
	NeuronalNetworkRepository nnRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(VinnslServiceApplication.class);
	}

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
}
