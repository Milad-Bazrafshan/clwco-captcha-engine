package org.clwco.captcha.config;

import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenApiCustomizer openApiCustomizer() {
        return openApi -> {
            // Info configuration
            Contact contact = new Contact()
                    .name("Support Team")
                    .email("api@clwco.ir")
                    .url("https://clwco.ir");

            License license = new License()
                    .name("Apache 2.0")
                    .url("https://www.apache.org/licenses/LICENSE-2.0.html");

            Info info = new Info()
                    .title("Captcha API Platform")
                    .version("1.0")
                    .description("API documentation for Captcha system")
                    .contact(contact)
                    .license(license);

            openApi.setInfo(info);

            // Security requirement
            openApi.addSecurityItem(new SecurityRequirement().addList("Authorization"));
        };
    }

    @Bean
    public GroupedOpenApi api() {
        return GroupedOpenApi.builder()
                .group("api")
                .displayName("captcha-api-v1")
                .packagesToScan("org.clwco.captcha.resource")
                .pathsToMatch("/api/**")
                .addOpenApiCustomizer(openApiCustomizer())
                .build();
    }
}