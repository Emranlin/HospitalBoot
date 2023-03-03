package peaksoft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import peaksoft.entity.Doctor;

import java.util.List;

@Repository

public interface DoctorRepository extends JpaRepository<Doctor,Long> {
    @Query("select d from Doctor d join d.hospital h where h.id=:id")
    List<Doctor>getAllDoctors(Long id);
}
