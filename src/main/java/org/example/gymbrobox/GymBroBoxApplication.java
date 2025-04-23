package org.example.gymbrobox;

import org.example.gymbrobox.database.TestRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class GymBroBoxApplication {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(GymBroBoxApplication.class, args);


        TestRepository testRepository = context.getBean(TestRepository.class);
        System.out.println("Kunde: " + testRepository.getKundeCount());
        System.out.println("Lieferant: " + testRepository.getLieferantCount());
    }

}
