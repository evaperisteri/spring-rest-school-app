package gr.aueb.cf.schoolapp.repository;

import gr.aueb.cf.schoolapp.model.static_data.EducationalUnit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface EducationalUnitRepository extends JpaRepository<EducationalUnit, Long>, JpaSpecificationExecutor<EducationalUnit> {

}
