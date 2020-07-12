package com.ernestas.bankingapp.persistence.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import com.ernestas.bankingapp.ITBase;
import com.ernestas.bankingapp.domain.StatementType;
import com.ernestas.bankingapp.persistence.entities.Statement;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class StatementRepositoryIT extends ITBase {


  @Autowired
  private StatementRepository statementRepository;

  @Test
  public void saveStatementOk() {

    Statement expected = statementRepository.saveAndFlush(
        Statement.builder()
        .amount(BigDecimal.TEN)
        .statementType(StatementType.WITHDRAWAL)
        .statementTime(ZonedDateTime.now())
        .build()
    );

    assertThat(statementRepository.findAll().size()).isEqualTo(1);

    Statement actual = statementRepository.findById(expected.getId()).get();

    assertThat(actual.getStatementType()).isEqualTo(expected.getStatementType());
    assertThat(actual.getAmount()).isEqualTo(new BigDecimal("10.00"));

  }

  @Test
  public void deleteStatementOk() {
    Statement statement = statementRepository.saveAndFlush(
        Statement.builder()
            .amount(BigDecimal.TEN)
            .statementType(StatementType.WITHDRAWAL)
            .statementTime(ZonedDateTime.now())
            .build()
    );

    statementRepository.deleteById(statement.getId());

    assertThat(statementRepository.findAll().size()).isEqualTo(0);
  }



}
