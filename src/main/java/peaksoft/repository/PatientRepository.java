package peaksoft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import peaksoft.entity.Patient;

import java.util.List;
@Repository
public interface PatientRepository extends JpaRepository<Patient,Long> {
    @Query("select p from Patient p join p.hospital h where h.id=:id")
    List<Patient>getAllPatient(Long id);
}
