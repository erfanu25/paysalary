package org.paysalary.repository;

import org.paysalary.domain.Enum.GradeNo;
import org.paysalary.entity.GradeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface GradeRepository extends JpaRepository<GradeEntity, Long> {
    GradeEntity findByGradeNo(@Param("gradeNo") String gradeNo);
}
