package org.paysalary.controller;

import org.paysalary.domain.BankAccount;
import org.paysalary.services.BankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class BankAccountController {
    @Autowired
    private BankService bankService;

    @GetMapping(value = "/employeeBankList")
    public List<BankAccount> employeeBankAccounts() {
        return bankService.getEmployeeBankList();
    }

    @GetMapping(value = "/employerBankList")
    public List<BankAccount> employerBankAccounts() {
        return bankService.getEmployerBankList();
    }

    @GetMapping(value = "/allBankInfo")
    public List<BankAccount> allBankAccountInfo() {
        return bankService.getAllBankInfoList();
    }

    @GetMapping("/bankInfo/{id}")
    public BankAccount getBankInfoById(@PathVariable long id) {
        return bankService.getBankAccountById(id);
    }

    @DeleteMapping("/bankInfo/{id}")
    public void deleteUser(@PathVariable long id) {
        bankService.delete(id);
    }

    @PostMapping("/bankInfo")
    public ResponseEntity<Object> createUser(@Valid @RequestBody BankAccount bankAccount) {
        if(!bankService.isBankAccountExist(bankAccount.getAccountNumber())) {
            return ResponseEntity.ok(bankService.saveBankAccount(bankAccount));
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("Bank Account Already Inserted");
        }
    }

    @PutMapping("/bankInfo/{id}")
    public ResponseEntity<Object> updateUser(@RequestBody BankAccount bankAccount, @PathVariable long id) {
        return ResponseEntity.ok(bankService.updateBankAccount(bankAccount,id));
    }

    @GetMapping(value = "/getCompanyAccountBalance/{accountNumber}")
    public long getCompanyAccountBalance(@PathVariable String accountNumber) {
        return bankService.getCompanyAccountBalance(accountNumber);
    }

    @PutMapping("/updateCompanyBankBalance/{accountNumber}/{balance}")
    public ResponseEntity<Object> updateCompanyBankBalance(@PathVariable String accountNumber, @PathVariable long balance) {
        return ResponseEntity.ok(bankService.updateCompanyBankBalance(accountNumber,balance));
    }

    @PutMapping("/updateBasicSalaryOfLowestGrade/{basicSalary}")
    public ResponseEntity<Object> updateBasicSalaryOfLowestGrade(@PathVariable long basicSalary) {
        return ResponseEntity.ok(bankService.updateBasicSalaryOfLowestGrade(basicSalary));
    }

    @GetMapping(value = "/transferSalary/{companyAccount}")
    public ResponseEntity<Object> transferSalary(@PathVariable String companyAccount) throws Exception {
        return ResponseEntity.ok(bankService.transferSalary(companyAccount));
    }


}
