package yacekbass.ear

import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport

@Configuration
class CorsConfig : WebMvcConfigurationSupport() {
    override fun addCorsMappings(registry: CorsRegistry) {
        registry.addMapping("*").allowedOrigins("*")
        super.addCorsMappings(registry)
    }
}