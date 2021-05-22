package org.paysalary.repository;

import org.paysalary.domain.Enum.OwnerType;
import org.paysalary.entity.BankAccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BankRepository extends JpaRepository<BankAccountEntity, Long> {
    List<BankAccountEntity> findAllByOwnerType(@Param("ownerType") String ownerType);

    BankAccountEntity findByAccountNumber(@Param("accountNumber") String accountNumber);

    BankAccountEntity findByAccountNumberAndOwnerType(@Param("accountNumber") String accountNumber,
                                                      @Param("ownerType") String ownerType);
}
