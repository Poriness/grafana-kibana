package edu.agh.dietaryapp.appointmentsservice.services;

import edu.agh.dietaryapp.appointmentsservice.domain.Appointment;
import java.util.List;

public interface AppointmentService {

    List<Appointment> findAllByClientId(Long userId);

    List<Appointment> findAllByDietitianId(Long dietitianId);

    Appointment findAllById(Long id);

    List<Appointment> findAll();

    Appointment save(Appointment appointment);

    Appointment update(Appointment appointment);

    boolean deleteWithId(Long appointmentId);
}
