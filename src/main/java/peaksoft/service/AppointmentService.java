package peaksoft.service;

import peaksoft.entity.Appointment;

import java.util.List;

public interface AppointmentService {
    void save(Long hospitalId, Appointment appointment);
    List<Appointment> getAllAppointmentByHospital(Long hospitalId);
    Appointment getAppointmentById(Long id);
    void deleteAppointment(Long id,Long hospitalId);
    void updateAppointment(Long id, Appointment appointment);
}
