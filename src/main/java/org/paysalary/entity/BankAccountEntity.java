package org.paysalary.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.paysalary.domain.Enum.AccountType;
import org.paysalary.domain.Enum.OwnerType;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Accessors(chain = true)
@Entity
@Table(name = "bankInfo")
public class BankAccountEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BANK_SEQ")
    @SequenceGenerator(sequenceName = "bank_seq", allocationSize = 1, name = "BANK_SEQ")
    Long id;

    @Column(name = "ACCOUNT_NUMBER")
    String accountNumber;
    @Column(name = "ACCOUNT_NAME")
    String accountName;
    @Column(name = "ACCOUNT_TYPE")
    String accountType;

    @Column(name = "OWNER_TYPE")
    String ownerType;

    @Column(name = "CREATED_DATE", updatable = false)
    @CreationTimestamp
    LocalDateTime date;

    @Column(name = "UPDATED_DATE")
    @UpdateTimestamp
    LocalDateTime updateDate;
    @Column(name = "BALANCE")
    long balance;
    @Column(name = "BANK_NAME")
    String bankName;
    @Column(name = "BRANCH_NAME")
    String branchName;
}
