package peaksoft.service;

import jakarta.transaction.Transactional;
import peaksoft.entity.Department;
import peaksoft.entity.Doctor;

import java.util.List;

public interface DoctorService {


    @Transactional
    Doctor save(Long id, Doctor doctor);

    List<Doctor> getAllDoctors(Long id);
    Doctor getDoctorById(Long id);
    void deleteDoctor(Long doctorId);
    void updateDoctor( Long id,Doctor doctor);

    List<Department> getAllDepartmentByDoctorId(Long doctorId);
    void assignDepartmentsToDoctor(List<Long> departmentIds, Long doctorId);


}
