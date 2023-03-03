package peaksoft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import peaksoft.entity.Department;

import java.util.List;
@Repository
public interface DepartmentRepository extends JpaRepository<Department,Long> {
    @Query("select d from Department d join d.hospital l where l.id=:id")
    List<Department>getAllDepartments(Long id);
}
