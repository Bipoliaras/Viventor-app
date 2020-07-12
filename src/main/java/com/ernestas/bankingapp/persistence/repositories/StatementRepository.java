package com.ernestas.bankingapp.persistence.repositories;

import com.ernestas.bankingapp.persistence.entities.Statement;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatementRepository extends JpaRepository<Statement, Long> {

  List<Statement> findAllByClient_Email(String email);
}
