package ru.alina.languageCards;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.autoconfigure.web.servlet.MockMvcBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.setup.ConfigurableMockMvcBuilder;

@SpringBootApplication
public class LanguageCardsApplication {
    public static void main(String[] args) {
        SpringApplication.run(LanguageCardsApplication.class, args);
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Component
    class MockMvcCharacterEncodingCustomizer implements MockMvcBuilderCustomizer {
        @Override
        public void customize(ConfigurableMockMvcBuilder<?> builder) {
            builder.alwaysDo(result -> result.getResponse().setCharacterEncoding("UTF-8"));
        }
    }
}
