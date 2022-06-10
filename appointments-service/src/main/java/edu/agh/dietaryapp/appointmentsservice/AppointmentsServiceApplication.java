package edu.agh.dietaryapp.appointmentsservice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
@Slf4j
public class AppointmentsServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AppointmentsServiceApplication.class, args);
        log.info("Appointments service has started!");
    }

}
