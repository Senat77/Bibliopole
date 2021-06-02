package rest.bibliopole;

import org.h2.tools.Server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import rest.bibliopole.repository.BookRepository;
import rest.bibliopole.util.DemoData;

import java.sql.SQLException;

@SpringBootApplication
public class App {

    private final BookRepository repository;

    @Autowired
    public App(BookRepository repository) {
        this.repository = repository;
    }

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
    public CommandLineRunner demo(DemoData data) {
        return (args) -> data.populate();
    }
}
