package ua.volosiuk.mytokenservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;

@SpringBootApplication(exclude = { HibernateJpaAutoConfiguration.class })
public class MyTokenServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyTokenServiceApplication.class, args);
    }
}
