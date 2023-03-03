package peaksoft.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import peaksoft.entity.Appointment;
import peaksoft.entity.Department;
import peaksoft.entity.Hospital;
import peaksoft.repository.AppointmentRepository;
import peaksoft.repository.DepartmentRepository;
import peaksoft.repository.DoctorRepository;
import peaksoft.repository.HospitalRepository;
import peaksoft.service.DepartmentService;

import java.util.List;
@Service
@Transactional
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {
    private final DepartmentRepository departmentRepository;
    private final HospitalRepository hospitalRepository;
    private final AppointmentRepository appointmentRepository;
    private final DoctorRepository doctorRepository;

    @Override
    public void save(Long id, Department department) {
        Hospital hospital = hospitalRepository.findById(id).get();
        department.setHospital(hospital);
        departmentRepository.save(department);

    }

    @Override
    public List<Department> getAllDepartments(Long id) {

        return departmentRepository.getAllDepartments(id);
    }

    @Override
    public Department getDepartmentById(Long id) {

        return departmentRepository.findById(id).orElseThrow(()->new RuntimeException("Not found like this id"));
    }

    @Override
    public void deleteDepartment(Long id) {
        Department department = departmentRepository.findById(id).get();
        List<Appointment> appointments = department.getHospital().getAppointments();
        List<Appointment> appointmentList = appointments.stream().filter(s -> s.getDepartment().getId().equals(id)).toList();
        appointmentList.forEach(s -> appointmentRepository.deleteById(s.getId()));
        List<Department> departments = department.getHospital().getDepartments();
        departments.removeIf(s -> s.getId().equals(id));
        departmentRepository.deleteById(id);

    }

    @Override
    public void updateDepartment(Long id, Department newDepartment) {
        Department department = departmentRepository.findById(id).get();
        department.setName(newDepartment.getName());
        department.setPhoto(newDepartment.getPhoto());
        departmentRepository.save(department);



    }
}
