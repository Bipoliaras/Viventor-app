package com.ernestas.bankingapp.services.banking;

import com.ernestas.bankingapp.persistence.entities.Statement;
import com.ernestas.bankingapp.persistence.repositories.StatementRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StatementService {

  private StatementRepository statementRepository;

  public List<Statement> getStatements(String email) {
    return statementRepository.findAllByClient_Email(email);
  }

  @Autowired
  public void setStatementRepository(
      StatementRepository statementRepository) {
    this.statementRepository = statementRepository;
  }
}
