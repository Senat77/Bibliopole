package group.bibliopole;

import group.bibliopole.model.Book;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

import java.util.Arrays;

@SpringBootApplication
public class App {

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @Profile("h2")
    @Bean
    public CommandLineRunner demo(BookRepository repository) {
        return (args) -> {
            repository.saveAll(Arrays.asList(
                    new Book("Материнская плата", true, 3L)
            ));
        };
    }
}
