package edu.agh.dietaryapp.appointmentsservice.services;

import edu.agh.dietaryapp.appointmentsservice.domain.Appointment;
import edu.agh.dietaryapp.appointmentsservice.repositories.AppointmentRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final UserServiceProxy userServiceProxy;

    public AppointmentServiceImpl(AppointmentRepository appointmentRepository, UserServiceProxy userServiceProxy) {
        this.appointmentRepository = appointmentRepository;
        this.userServiceProxy = userServiceProxy;
    }

    @Override
    public List<Appointment> findAllByClientId(Long userId) {
        return appointmentRepository.findAllByClientId(userId);
    }

    @Override
    public List<Appointment> findAllByDietitianId(Long dietitianId) {
        return appointmentRepository.findAllByDietitianId(dietitianId);
    }

    @Override
    public Appointment findAllById(Long id) {
        return appointmentRepository.findAllById(id);
    }

    @Override
    public List<Appointment> findAll() {
        return appointmentRepository.findAll();
    }

    @Override
    public Appointment save(Appointment appointment) {
        long clientId = appointment.getClientId();
        long dietitianId = appointment.getDietitianId();
        userServiceProxy.isDietitianConnectedWithClient((int) clientId, (int) dietitianId);
        return appointmentRepository.save(appointment);
    }

    @Override
    public Appointment update(Appointment appointment) {
        if (appointment.getId() == null) {
            throw new RuntimeException("Id cannot be null!");
        }
        long clientId = appointment.getClientId();
        long dietitianId = appointment.getDietitianId();
        userServiceProxy.isDietitianConnectedWithClient((int) clientId, (int) dietitianId);
        return appointmentRepository.save(appointment);
    }

    @Override
    public boolean deleteWithId(Long appointmentId) {
        appointmentRepository.deleteAppointmentById(appointmentId);
        return appointmentRepository.findAllById(appointmentId) == null;
    }


}
