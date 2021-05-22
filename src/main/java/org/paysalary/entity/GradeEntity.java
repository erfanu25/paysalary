package org.paysalary.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.paysalary.domain.Enum.GradeNo;

import javax.persistence.*;

@Getter
@Setter
@Accessors(chain = true)
@Entity
@Table(name = "gradeInfo")
public class GradeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GRADE_SEQ")
    @SequenceGenerator(sequenceName = "grade_seq", allocationSize = 1, name = "GRADE_SEQ")
    Long id;

    @Column(name = "GRADE_NO")
    String gradeNo;
    @Column(name = "BASIC_SALARY")
    long basicSalary;
}
