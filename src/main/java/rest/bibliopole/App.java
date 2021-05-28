package rest.bibliopole;

import rest.bibliopole.model.Book;
import rest.bibliopole.repository.BookRepository;
import org.h2.tools.Server;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

import java.sql.SQLException;
import java.util.Arrays;

@SpringBootApplication
public class App {

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    // https://stackoverflow.com/questions/9318116/how-to-run-h2-database-in-server-mode
    // url : jdbc:h2:tcp://localhost:9092/mem:bibliopole
    @Bean(initMethod = "start", destroyMethod = "stop")
    @Profile("!test")
    public Server h2Server() throws SQLException {
        return Server.createTcpServer("-tcp", "-tcpAllowOthers", "-tcpPort", "9092");
    }

    @Profile("h2")
    @Bean
    public CommandLineRunner demo(BookRepository repository) {
        return (args) -> {
            repository.saveAll(Arrays.asList(
                    new Book("Кобзарь", "Тарас Шевченко", "Украинские сувениры", 2021, 500, 5018d),
                    new Book("Хребты безумия", "Говард Лавкрафт", "Азбука", 2020, 64, 333.51),
                    new Book("Книга власти", "Шан Ян", "Бука", 1999, 312, 51.99d),
                    new Book("Облачный атлас", "Дэвид Митчелл", "Укркнига", 1998, 544, 128d)
            ));
        };
    }
}
