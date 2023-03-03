package peaksoft.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import peaksoft.entity.Appointment;
import peaksoft.entity.Hospital;
import peaksoft.entity.Patient;
import peaksoft.repository.AppointmentRepository;
import peaksoft.repository.HospitalRepository;
import peaksoft.repository.PatientRepository;
import peaksoft.service.PatientService;

import java.util.List;
@Service
@Transactional
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {
    private  final PatientRepository patientRepository;
    private final HospitalRepository hospitalRepository;
    private  final AppointmentRepository appointmentRepository;
    @Override
    public void save(Long hospitalId, Patient patient) {
        Hospital hospital = hospitalRepository.findById(hospitalId).get();
        patient.setHospital(hospital);
        patientRepository.save(patient);
    }

    @Override
    public List<Patient> getAllPatients(Long hospitalId) {
        return patientRepository.getAllPatient(hospitalId);
    }

    @Override
    public Patient getPatientById(Long id) {
        return patientRepository.findById(id).get();
    }

    @Override
    public void deletePatient(Long id) {
        Patient patient = patientRepository.findById(id).get();
        List<Appointment> appointments = patient.getHospital().getAppointments();
        List<Appointment> appointments1 = appointments.stream().filter(a -> a.getPatient().getId().equals(id)).toList();
        appointments1.forEach(a->appointmentRepository.deleteById(a.getId()));
        List<Patient> patients = patient.getHospital().getPatients();
        patients.removeIf(a->a.getId().equals(id));
        patientRepository.deleteById(id);

    }

    @Override
    public void updatePatient(Long id, Patient patient) {
        Patient patient1 = patientRepository.findById(id).orElseThrow(()->new RuntimeException());
        patient1.setFirsName(patient.getFirsName());
        patient1.setLastName(patient.getLastName());
        patient1.setEmail(patient.getEmail());
        patient1.setGender(patient.getGender());
        patient1.setPhoneNumber(patient.getPhoneNumber());
        patientRepository.save(patient1);


    }
}
