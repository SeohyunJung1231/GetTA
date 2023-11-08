package com.jeong.getta.config

import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SwaggerConfig {
    @Bean
    fun opneApi(): OpenAPI = OpenAPI()
        .components(Components())
        .info(apiInfo())

    private fun apiInfo(): Info = Info()
        .title("GetTA Rest API Documentation")
        .description("GetTA 프로젝트를 위한 백앤드 API 설명 및 실행 화면입니다.")
        .version("0.1")
}