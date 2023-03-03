package peaksoft.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import peaksoft.entity.Appointment;
import peaksoft.entity.Hospital;
import peaksoft.repository.*;
import peaksoft.service.AppointmentService;

import java.util.List;
@Service
@Transactional
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {
    private final AppointmentRepository appointmentRepository;
    private final HospitalRepository hospitalRepository;
    private final PatientRepository patientRepository;
    private  final DoctorRepository doctorRepository;
    private final DepartmentRepository departmentRepository;
    @Override
    public void save(Long hospitalId, Appointment appointment) {
        Hospital hospital = hospitalRepository.findById(hospitalId).get();
        Appointment appointment1 = new Appointment();
        appointment1.setId(appointment.getId());
        appointment1.setLocalDate(appointment.getLocalDate());
        appointment1.setPatient(patientRepository.findById(appointment.getPatientId()).get());
        appointment1.setDoctor(doctorRepository.findById(appointment.getDoctorId()).get());
        appointment1.setDepartment(departmentRepository.findById(appointment.getDepartmentId()).get());
        hospital.addAppointment(appointment1);
        appointmentRepository.save(appointment1);


    }

    @Override
    public List<Appointment> getAllAppointmentByHospital(Long hospitalId) {
        return appointmentRepository.getAppointmentByHospital(hospitalId);
    }

    @Override
    public Appointment getAppointmentById(Long id) {
        return appointmentRepository.findById(id).get();
    }

    @Override
    public void deleteAppointment(Long id, Long hospitalId) {
        List<Hospital> hospitals = hospitalRepository.findAll();
        hospitals.forEach(h -> h.getAppointments().removeIf(a -> a.getId().equals(id)));
        appointmentRepository.deleteById(id);
    }



    @Override
    public void updateAppointment(Long id, Appointment appointment) {
        Appointment appointment1 = appointmentRepository.findById(id).get();
        appointment1.setLocalDate(appointment.getLocalDate());
        appointment1.setPatient(patientRepository.findById(appointment.getPatientId()).get());
        appointment1.setDoctor(doctorRepository.findById(appointment.getDoctorId()).get());
        appointment1.setDepartment(departmentRepository.findById(appointment.getDepartmentId()).get());



    }
}
