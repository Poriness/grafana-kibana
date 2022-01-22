package edu.agh.dietaryapp.appointmentsservice.services;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-service")
public interface UserServiceProxy {

    @GetMapping("/user/assigned/client/{clientId}/dietitian/{dietitianId}")
    boolean isDietitianConnectedWithClient(@PathVariable int clientId, @PathVariable int dietitianId);

}
