package com.ericmong.school_system.config

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Contact
import io.swagger.v3.oas.annotations.info.Info
import io.swagger.v3.oas.annotations.info.License
import io.swagger.v3.oas.annotations.servers.Server

@OpenAPIDefinition(
    info = Info(
        contact = Contact(
            name = "Creator",
            email = "creator@example.com",
            url = "https://www.google.com"
        ),
        description = "School System",
        title = "School System Specifications",
        version = "1.0",
        license = License(
            name = "Licence name",
            url = "https://www.github.com"
        ),
        termsOfService = "Terms of Service"
    ),
    servers = [
        Server(
            description = "LOCAL DEV",
            url = "http://localhost:8080"
        ),
        Server(
            description = "STAGING",
            url = "http://localhost:8080"
        ),
        Server(
            description = "PROD",
            url = "http://localhost:8080"
        ),
    ]
)
class OpenApiConfig {
}