package org.paysalary.mapper;

import org.paysalary.domain.Employee;
import org.paysalary.domain.Enum.Gender;
import org.paysalary.domain.Enum.GradeNo;
import org.paysalary.domain.Enum.OwnerType;
import org.paysalary.entity.EmployeeEntity;
import org.paysalary.repository.BankRepository;
import org.paysalary.repository.GradeRepository;
import org.paysalary.services.BankService;
import org.springframework.stereotype.Component;

@Component
public class EmployeeMapper {
    private final BankRepository bankRepository;
    private final GradeRepository gradeRepository;
    private final BankService bankService;

    public EmployeeMapper(BankRepository bankRepository,
                          GradeRepository gradeRepository,
                          BankService bankService) {
        this.bankRepository = bankRepository;
        this.gradeRepository = gradeRepository;
        this.bankService = bankService;
    }

    public ResultMapper<EmployeeEntity, Employee> entityToDomainMapping() {
        return entity ->
                new Employee().setEmployeeId(entity.getEmployeeId())
                        .setName(entity.getName())
                        .setAddress(entity.getAddress())
                        .setGender(Gender.valueOf(entity.getGender()))
                        .setMobileNo(entity.getMobileNo())
                        .setGradeNo(GradeNo.valueOf(entity.getGradeNo()))
                        .setId(entity.getId())
                        .setSalary( this.bankService.calculateGrossSalary(
                                gradeRepository.findByGradeNo(entity.getGradeNo()).getBasicSalary()))
                        .setAccountNumber(entity.getBankAccountEntity().getAccountNumber());
    }


    public ResultMapper<Employee, EmployeeEntity> domainToEntityMapping() {
        return domain ->
                new EmployeeEntity()
                        .setEmployeeId(domain.getEmployeeId())
                        .setName(domain.getName())
                        .setAddress(domain.getAddress())
                        .setGender(domain.getGender().toString())
                        .setMobileNo(domain.getMobileNo())
                        .setGradeNo(domain.getGradeNo().toString())
                        .setBankAccountEntity(
                        bankRepository.findByAccountNumberAndOwnerType(domain.getAccountNumber(),
                                OwnerType.Employee.toString()));
    }
}
