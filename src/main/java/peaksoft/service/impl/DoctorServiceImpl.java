package peaksoft.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import peaksoft.entity.Appointment;
import peaksoft.entity.Department;
import peaksoft.entity.Doctor;
import peaksoft.entity.Hospital;
import peaksoft.repository.DepartmentRepository;
import peaksoft.repository.DoctorRepository;
import peaksoft.repository.HospitalRepository;
import peaksoft.service.DepartmentService;
import peaksoft.service.DoctorService;

import java.util.List;
@Service
@Transactional
@RequiredArgsConstructor
public class DoctorServiceImpl implements DoctorService {
private final DoctorRepository doctorRepository;
private final HospitalRepository hospitalRepository;
private final DepartmentRepository departmentRepository;

    @Override
    public Doctor save(Long id, Doctor doctor) {
        Hospital hospital = hospitalRepository.findById(id).get();
        doctor.setHospital(hospital);
        return doctorRepository.save(doctor);


    }

    @Override
    public List<Doctor> getAllDoctors(Long id) {
        return doctorRepository.getAllDoctors(id);
    }

    @Override
    public Doctor getDoctorById(Long id) {
        return doctorRepository.findById(id).orElseThrow(()->new RuntimeException("Not found this id"));
    }

    @Override
    public void deleteDoctor(Long doctorId) {

        Doctor doctor = doctorRepository.findById(doctorId).get();
        List<Appointment> appointments = doctor.getAppointments();
        List<Appointment> appointments1 = appointments.stream().filter(a -> a.getDoctor().getId().equals(doctorId)).toList();
        appointments1.forEach(a->doctorRepository.deleteById(a.getId()));
        List<Doctor> doctors = doctor.getHospital().getDoctors();
        doctors.removeIf(d->d.getId().equals(doctorId));
        doctorRepository.deleteById(doctorId);
    }

    @Override
    public void updateDoctor(Long id, Doctor doctor) {
        Doctor doctor1 = doctorRepository.findById(id).get();
        doctor1.setFirstName(doctor.getFirstName());
        doctor1.setLastName(doctor.getLastName());
        doctor1.setPosition(doctor.getPosition());
        doctor1.setEmail(doctor.getEmail());
        doctor1.setPhoto(doctor.getPhoto());
        doctorRepository.save(doctor1);

    }

    @Override
    public List<Department> getAllDepartmentByDoctorId(Long doctorId) {
        Doctor doctor = doctorRepository.findById(doctorId).get();
        List<Department> allDepartment = departmentRepository.getAllDepartments(doctor.getHospital().getId());
        for (Department department : doctor.getDepartments()) {
            allDepartment.remove(department);
        }
        return allDepartment;
    }
    @Override
    public void assignDepartmentsToDoctor(List<Long> departmentId, Long doctorId) {
        Doctor doctor = doctorRepository.findById(doctorId).get();
        List<Department> departments = departmentRepository.findAllById(departmentId);
        for (Department department : departments) {
            doctor.addDepartment(department);
            department.addDoctor(doctor);
        }
        doctorRepository.save(doctor);
    }
}
