package peaksoft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import peaksoft.entity.Hospital;

import java.util.List;

@Repository

public interface HospitalRepository  extends JpaRepository<Hospital,Long> {
    @Query("select h from Hospital h where h.name ILIKE (:word)")
    List<Hospital> getAllBy(String word);
}
