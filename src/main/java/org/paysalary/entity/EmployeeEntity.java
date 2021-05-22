package org.paysalary.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.paysalary.domain.Enum.Gender;
import org.paysalary.domain.Enum.GradeNo;
import org.paysalary.domain.Grade;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Accessors(chain = true)
@Entity
@Table(name = "employeeInfo")
public class EmployeeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "EMP_SEQ")
    @SequenceGenerator(sequenceName = "emp_seq", allocationSize = 1, name = "EMP_SEQ")
    Long id;

    @Column(name = "EMPLOYEE_ID")
    String employeeId;

    @Column(name = "NAME")
    String name;
    @Column(name = "GENDER")
    String gender;

    @Column(name = "CREATED_DATE", updatable = false)
    @CreationTimestamp
    LocalDateTime date;

    @Column(name = "UPDATED_DATE")
    @UpdateTimestamp
    LocalDateTime updateDate;
    @Column(name = "GRADE")
    String gradeNo;
    @Column(name = "ADDRESS")
    String address;
    @Column(name = "MOBILE")
    String mobileNo;

    @OneToOne
    @JoinColumn(name = "BANK_AC_ID")
    BankAccountEntity bankAccountEntity;
}
