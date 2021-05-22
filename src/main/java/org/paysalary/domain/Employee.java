package org.paysalary.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.paysalary.domain.Enum.Gender;
import org.paysalary.domain.Enum.GradeNo;

@Getter
@Setter
@Accessors(chain = true)
public class Employee {
    Long id;
    String employeeId;
    String name;
    Gender gender;
    GradeNo gradeNo;
    String address;
    String mobileNo;
    String accountNumber;
    Long salary;
}
