package org.paysalary.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.paysalary.domain.Enum.AccountType;
import org.paysalary.domain.Enum.OwnerType;

@Getter
@Setter
@Accessors(chain = true)
public class BankAccount {
    Long id;
    String accountNumber;
    String accountName;
    AccountType accountType;
    OwnerType ownerType;
    String bankName;
    String branchName;
}
