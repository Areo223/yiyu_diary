package org.areo.yiyu.config;


import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration

public class SwaggerConfig {
    @Bean
    public OpenAPI swaggerOpenApi(){
        return new OpenAPI()
                .components(new Components())
                .info(new Info().title("Yiyu日记 API文档").description("werun后端作业")
                        .version("v1.0.0"))
                .externalDocs(new ExternalDocumentation()
                        .description("设计文档").url("https://vwvt19hb5q9.feishu.cn/docx/BLtOdsUjpoXUmCxGsJZc7lOsn0d"));
    }

}
