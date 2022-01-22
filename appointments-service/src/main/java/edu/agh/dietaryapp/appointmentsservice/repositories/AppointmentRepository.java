package edu.agh.dietaryapp.appointmentsservice.repositories;

import edu.agh.dietaryapp.appointmentsservice.domain.Appointment;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    List<Appointment> findAllByClientId(Long userId);

    List<Appointment> findAllByDietitianId(Long dietitianId);

    Appointment findAllById(Long id);

    List<Appointment> findAll();

    void deleteAppointmentById(Long id);

}
