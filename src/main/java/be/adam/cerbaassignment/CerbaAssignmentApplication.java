package be.adam.cerbaassignment;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class CerbaAssignmentApplication {

    public static void main(String[] args) {
        SpringApplication.run(CerbaAssignmentApplication.class, args);
    }

    // TODO: in aparte config klasse
    @Bean
    public ModelMapper getModelMapper() {
        return new ModelMapper();
    }

}
