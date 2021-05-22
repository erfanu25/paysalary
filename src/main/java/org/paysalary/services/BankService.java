package org.paysalary.services;

import org.paysalary.domain.BankAccount;
import org.paysalary.domain.Enum.GradeNo;
import org.paysalary.domain.Enum.OwnerType;
import org.paysalary.entity.BankAccountEntity;
import org.paysalary.entity.EmployeeEntity;
import org.paysalary.entity.GradeEntity;
import org.paysalary.mapper.BankAccountMapper;
import org.paysalary.repository.BankRepository;
import org.paysalary.repository.EmployeeRepository;
import org.paysalary.repository.GradeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BankService {
    private final BankRepository bankRepository;
    private final EmployeeRepository employeeRepository;
    private final GradeRepository gradeRepository;
    private final BankAccountMapper bankAccountMapper;

    public BankService(BankRepository bankRepository,
                       EmployeeRepository employeeRepository,
                       GradeRepository gradeRepository,
                       BankAccountMapper bankAccountMapper) {
        this.bankRepository = bankRepository;
        this.employeeRepository = employeeRepository;
        this.gradeRepository = gradeRepository;
        this.bankAccountMapper = bankAccountMapper;
    }

    public List<BankAccount> getEmployeeBankList() {
        return bankRepository.findAllByOwnerType(OwnerType.Employee.toString())
                .stream()
                .map(employeeEntity -> bankAccountMapper
                        .entityToDomainMapping()
                        .map(employeeEntity))
                .collect(Collectors.toList());
    }

    public List<BankAccount> getEmployerBankList() {
        return bankRepository.findAllByOwnerType(OwnerType.Employer.toString())
                .stream()
                .map(employeeEntity -> bankAccountMapper
                        .entityToDomainMapping()
                        .map(employeeEntity))
                .collect(Collectors.toList());
    }

    public List<BankAccount> getAllBankInfoList() {
        return bankRepository.findAll()
                .stream()
                .map(employeeEntity -> bankAccountMapper
                        .entityToDomainMapping()
                        .map(employeeEntity))
                .collect(Collectors.toList());
    }

    public BankAccount getBankAccountById(long id) {
        return bankAccountMapper.entityToDomainMapping().map(bankRepository.findById(id).get());
    }

    public boolean isBankAccountExist(String accountNumber) {
        BankAccountEntity bankAccountEntity = bankRepository.findByAccountNumber(accountNumber);
        return bankAccountEntity != null;
    }

    public void delete(long id) {
        bankRepository.deleteById(id);
    }

    public BankAccount saveBankAccount(BankAccount bankAccount) {
        return bankAccountMapper.entityToDomainMapping().map(
                bankRepository.save(bankAccountMapper.domainToEntityMapping().map(bankAccount)));
    }

    public String updateBankAccount(BankAccount bankAccount, long id) {
        Optional<BankAccountEntity> bankAccountEntityOptional = bankRepository.findById(id);
        if (!bankAccountEntityOptional.isPresent()) {
            return "Bank Account Not Found";
        }
        BankAccountEntity bankAccountEntity = bankAccountMapper.domainToEntityMapping().map(bankAccount);
        bankAccountEntity.setId(id);
        bankRepository.save(bankAccountEntity);
        return "Update Success";
    }

    public String updateCompanyBankBalance(String accountNumber, long balance) {
        BankAccountEntity bankAccountEntity =
                bankRepository.findByAccountNumberAndOwnerType(accountNumber, OwnerType.Employer.toString());
        long prevBalance = bankAccountEntity.getBalance();
        bankAccountEntity.setBalance(prevBalance + balance);
        bankRepository.save(bankAccountEntity);
        return "Balance Update Success";
    }

    public long getCompanyAccountBalance(String accountNumber) {
        BankAccountEntity bankAccountEntity =
                bankRepository.findByAccountNumberAndOwnerType(accountNumber, OwnerType.Employer.toString());

        return bankAccountEntity.getBalance();
    }

    public String transferSalary(String companyAccountNumber) throws Exception {

        List<EmployeeEntity> employeeEntities = employeeRepository.findAll();

        long totalSalaryToDisburse = employeeEntities.stream().mapToLong(
                employeeEntity -> {
                    long basicSalary = gradeRepository.findByGradeNo(employeeEntity.getGradeNo().toString()).getBasicSalary();
                    return this.calculateGrossSalary(basicSalary);
                }
        ).sum();
        if (totalSalaryToDisburse < bankRepository.findByAccountNumberAndOwnerType(companyAccountNumber, OwnerType.Employer.toString()).getBalance()) {
            employeeEntities.stream().forEach(
                    employeeEntity -> {
                        long basicSalary = gradeRepository.findByGradeNo(employeeEntity.getGradeNo().toString()).getBasicSalary();
                        this.disburseSalary(employeeEntity.getBankAccountEntity().getAccountNumber(),
                                companyAccountNumber,this.calculateGrossSalary(basicSalary));
                    }
            );
        } else{
            throw new Exception("Balance not sufficient");
        }

        return "ok";
    }

    public void disburseSalary(String employeeAccount, String companyAccount, long grossSalary) {
        BankAccountEntity bankAccountEntity =
                bankRepository.findByAccountNumberAndOwnerType(companyAccount, OwnerType.Employer.toString());
        long balance = bankAccountEntity.getBalance();
        bankAccountEntity.setBalance(balance - grossSalary);
        bankRepository.save(bankAccountEntity);

        BankAccountEntity employeeBankAccount =
                bankRepository.findByAccountNumberAndOwnerType(employeeAccount, OwnerType.Employee.toString());
        long empBalance = employeeBankAccount.getBalance();
        employeeBankAccount.setBalance(empBalance + grossSalary);
        bankRepository.save(employeeBankAccount);
    }

    public long calculateGrossSalary(long basicSalary) {
        long houseRent = (basicSalary * 20) / 100;
        long medicalAllowance = (basicSalary * 15) / 100;
        return basicSalary + houseRent + medicalAllowance;
    }

    public String updateBasicSalaryOfLowestGrade(long basicSalary) {
        this.updateBasicSalaryGradeWise(GradeNo.Six, basicSalary);
        this.updateBasicSalaryGradeWise(GradeNo.Five, basicSalary + 5000);
        this.updateBasicSalaryGradeWise(GradeNo.Four, basicSalary + 10000);
        this.updateBasicSalaryGradeWise(GradeNo.Three, basicSalary + 15000);
        this.updateBasicSalaryGradeWise(GradeNo.Two, basicSalary + 20000);
        this.updateBasicSalaryGradeWise(GradeNo.One, basicSalary + 25000);
        return "Basic Salary Update Success";
    }

    public void updateBasicSalaryGradeWise(GradeNo gradeNo, long basicSalary) {
        GradeEntity lowestGrade = gradeRepository.findByGradeNo(gradeNo.toString());
        lowestGrade.setBasicSalary(basicSalary);
        gradeRepository.save(lowestGrade);
    }
}
