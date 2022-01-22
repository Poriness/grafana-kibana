package edu.agh.dietaryapp.appointmentsservice.controllers;


import edu.agh.dietaryapp.appointmentsservice.domain.Appointment;
import edu.agh.dietaryapp.appointmentsservice.services.AppointmentService;
import java.util.List;
import javax.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Transactional
public class AppointmentController {

    private final Logger logger = LoggerFactory.getLogger(AppointmentController.class);

    private final AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @GetMapping("/appointments/client/{userId}")
    public List<Appointment> getAllAppointmentsForUser(@PathVariable Long userId) {
        logger.info("Getting all appointments for user {}", userId);
        return appointmentService.findAllByClientId(userId);
    }

    @GetMapping("/appointments/dietitian/{dietitianId}")
    public List<Appointment> getAllAppointmentsForDietitian(@PathVariable Long dietitianId) {
        logger.info("Getting all appointments for dietitian {}", dietitianId);
        return appointmentService.findAllByDietitianId(dietitianId);
    }

    @GetMapping("/appointments/{id}")
    public Appointment getAppointmentWithId(@PathVariable Long id) {
        logger.info("Getting appointment with id {}", id);
        return appointmentService.findAllById(id);
    }

    @GetMapping("/appointments/all")
    public List<Appointment> getAllAppointments() {
        logger.info("Getting all appointments");
        return appointmentService.findAll();
    }

    @PostMapping("appointments/create")
    public Appointment saveNewAppointment(@RequestBody Appointment appointment) {
        logger.info("Saving appointment");
        return appointmentService.save(appointment);
    }

    @PutMapping("appointments/update")
    public Appointment updateAppointment(@RequestBody Appointment appointment) {
        logger.info("Updating appointment");
        return appointmentService.update(appointment);
    }

    @DeleteMapping("appointments/delete/{appointmentId}")
    public ResponseEntity<String> removeAppointment(@PathVariable Long appointmentId) {
        logger.info("Removing appointment with id: {}", appointmentId);
        if (appointmentService.findAllById(appointmentId) == null) {
            return new ResponseEntity<>(String.format("Cannot find appointment with id %s", appointmentId),
                    HttpStatus.NOT_FOUND);
        }
        boolean isRemoved = appointmentService.deleteWithId(appointmentId);
        if (!isRemoved) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(String.format("Appointment with id %s removed", appointmentId), HttpStatus.OK);
    }

}
