package com.ernestas.bankingapp;

import com.ernestas.bankingapp.persistence.repositories.BalanceRepository;
import com.ernestas.bankingapp.persistence.repositories.ClientRepository;
import com.ernestas.bankingapp.persistence.repositories.StatementRepository;
import io.restassured.RestAssured;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class ClientIT extends ITBase {

  @Autowired
  private ClientRepository clientRepository;

  @Autowired
  private BalanceRepository balanceRepository;

  @Autowired
  private StatementRepository statementRepository;

  @Test
  public void singleStatement_whenGetStatements_returnsCorrectStatements() {

    RestAssured.given()
        .auth()
        .preemptive()
        .basic("banker@gmail.com", "banker")
        .when()
        .get("http://localhost:8080/clients/statements")
        .then()
        .statusCode(200);

  }

}
