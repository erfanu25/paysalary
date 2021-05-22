package org.paysalary.mapper;

import org.paysalary.domain.BankAccount;
import org.paysalary.domain.Enum.AccountType;
import org.paysalary.domain.Enum.OwnerType;
import org.paysalary.entity.BankAccountEntity;
import org.springframework.stereotype.Component;

@Component
public class BankAccountMapper {

    public ResultMapper<BankAccountEntity, BankAccount> entityToDomainMapping() {
        return entity ->
                new BankAccount().setAccountName(entity.getAccountName())
                        .setAccountNumber(entity.getAccountNumber())
                        .setAccountType(AccountType.valueOf(entity.getAccountType()))
                        .setBankName(entity.getBankName())
                        .setBranchName(entity.getBranchName())
                        .setId(entity.getId())
                        .setOwnerType(OwnerType.valueOf(entity.getOwnerType()));
    }


    public ResultMapper<BankAccount, BankAccountEntity> domainToEntityMapping() {
        return domain ->
                new BankAccountEntity()
                        .setAccountName(domain.getAccountName())
                        .setAccountNumber(domain.getAccountNumber())
                        .setAccountType(domain.getAccountType().toString())
                        .setBankName(domain.getBankName())
                        .setBranchName(domain.getBranchName())
                        .setOwnerType(domain.getOwnerType().toString());
    }
}
