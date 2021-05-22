package org.paysalary.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.paysalary.domain.Enum.GradeNo;

@Getter
@Setter
@Accessors(chain = true)
public class Grade {
    GradeNo gradeNo;
    long salary;
}
