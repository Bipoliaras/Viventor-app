package com.ernestas.bankingapp.persistence.repositories;

import com.ernestas.bankingapp.persistence.entities.Balance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BalanceRepository extends JpaRepository<Balance, Long> {

}
