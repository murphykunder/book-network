package com.alimurph.book.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

/**
 * The OpenAPI specification lets us define a set of security schemes for the API.
 * We can configure the security requirements of the API globally or apply/remove them per endpoint.
 * http://localhost:8088/api/v1/swagger-ui/index.html
 */
@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name = "AliMurph",
                        email = "contact@alimurphabc.com",
                        url = "https://some-url.com"
                ),
                description = "OpenApi documentation for Spring Security",
                title = "OpenApi Specification - AliMurph",
                version = "1.0",
                license = @License(
                        name = "License name",
                        url = "https://some-url.com"
                ),
                termsOfService = "Terms of Service"
        ),
        // with Swagger APi you can send request to different env
        servers = {
                @Server(
                        description = "Local ENV",
                        url = "http://localhost:8088/api/v1"   // mention the base url, rest will be added by swagger based on the route
                ),
                @Server(
                        description = "PROD ENV",
                        url = "https://alimurph.com/api"   // mention the base url, rest will be added by swagger based on the route
                )
        },
        security = {
                // For every controller in the application we need a security requirement. We can have multiple security requirements defined.
                @SecurityRequirement(
                        name="bearerAuth"
                )
        }
)
@SecurityScheme(
        /**
         * name should have the same value as that mentioned in @SecurityRequirement.
         * In this exmaple we have only 1 security scheme "bearerAuth" which will be applied commonly to all the controllers
         * But we can also have mutliple security scheme, specific to a route. Just ensure those are also added in the @SecurityRequirement
         */
        name = "bearerAuth",
        description = "JWT auth",
        scheme = "bearer",
        type = SecuritySchemeType.HTTP,  // this means it is a security scheme for all the http request
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER   //(mention the location to include the bearer token, in this case in the header)
)
public class OpenApiConfig {
}
