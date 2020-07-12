package com.ernestas.bankingapp.api;

import static org.assertj.core.api.Assertions.assertThat;

import com.ernestas.bankingapp.ITBase;
import com.ernestas.bankingapp.domain.DepositRequest;
import com.ernestas.bankingapp.domain.SignupRequest;
import com.ernestas.bankingapp.domain.StatementType;
import com.ernestas.bankingapp.domain.WithdrawRequest;
import com.ernestas.bankingapp.persistence.entities.Balance;
import com.ernestas.bankingapp.persistence.repositories.StatementRepository;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.math.BigDecimal;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class ClientIT extends ITBase {

  @Autowired
  private StatementRepository statementRepository;

  @Test
  public void givenClient_whenGetStatements_returnsCorrectStatements() {

    createUser();

    RestAssured.given()
        .auth()
        .preemptive()
        .basic("banker@gmail.com", "banker")
        .when()
        .get("http://localhost:8080/clients/statements")
        .then()
        .statusCode(200);

  }

  @Test
  public void givenClient_whenGetBalance_returnsCorrectBalance() {

    createUser();

    Balance balance = RestAssured.given()
        .auth()
        .preemptive()
        .basic("banker@gmail.com", "banker")
        .when()
        .get("http://localhost:8080/clients/balance")
        .then()
        .statusCode(200)
        .extract()
        .as(Balance.class);

    assertThat(balance.getAmount()).isEqualTo(new BigDecimal("10.00"));

  }

  @Test
  public void givenClientWithBalance_whenDepositMoney_ok() {

    createUser();

    RestAssured.given()
        .accept(ContentType.JSON)
        .contentType(ContentType.JSON)
        .auth()
        .preemptive()
        .basic("banker@gmail.com", "banker")
        .body(DepositRequest.builder()
            .amount(BigDecimal.TEN)
            .build())
        .when()
        .post("http://localhost:8080/clients/deposit")
        .then()
        .statusCode(200);

    assertThat(clientRepository.findByEmail("banker@gmail.com").get().getBalance().getAmount())
        .isEqualTo(new BigDecimal("20.00"));

    assertThat(statementRepository.findAll().size()).isEqualTo(1);
  }

  @Test
  public void givenClientWithBalance_whenWithdrawMoney_ok() {

    createUser();

    RestAssured.given()
        .accept(ContentType.JSON)
        .contentType(ContentType.JSON)
        .auth()
        .preemptive()
        .basic("banker@gmail.com", "banker")
        .body(WithdrawRequest.builder()
            .amount(BigDecimal.ONE)
            .build())
        .when()
        .post("http://localhost:8080/clients/withdraw")
        .then()
        .statusCode(200);

    assertThat(clientRepository.findByEmail("banker@gmail.com").get().getBalance().getAmount())
        .isEqualTo(new BigDecimal("9.00"));

    assertThat(statementRepository.findAll().size()).isEqualTo(1);

  }
  @Test
  public void givenClientWithBalance_whenWithdrawMoneyAndGetStatements_ok() {

    createUser();

    RestAssured.given()
        .accept(ContentType.JSON)
        .contentType(ContentType.JSON)
        .auth()
        .preemptive()
        .basic("banker@gmail.com", "banker")
        .body(WithdrawRequest.builder()
            .amount(BigDecimal.ONE)
            .build())
        .when()
        .post("http://localhost:8080/clients/withdraw")
        .then()
        .statusCode(200);

    assertThat(clientRepository.findByEmail("banker@gmail.com").get().getBalance().getAmount())
        .isEqualTo(new BigDecimal("9.00"));

    assertThat(statementRepository.findAll().size()).isEqualTo(1);

    RestAssured.given()
        .auth()
        .preemptive()
        .basic("banker@gmail.com", "banker")
        .when()
        .get("http://localhost:8080/clients/statements")
        .then()
        .statusCode(200)
        .body("[0].statementType", Matchers.is(StatementType.WITHDRAWAL.name()));

  }

  @Test
  public void givenClientWithBalance_whenWithdrawExcessAmount_throwsError() {

    createUser();

    RestAssured.given()
        .accept(ContentType.JSON)
        .contentType(ContentType.JSON)
        .auth()
        .preemptive()
        .basic("banker@gmail.com", "banker")
        .body(WithdrawRequest.builder()
            .amount(new BigDecimal("100.00"))
            .build())
        .when()
        .post("http://localhost:8080/clients/withdraw")
        .then()
        .statusCode(400);

    assertThat(clientRepository.findByEmail("banker@gmail.com").get().getBalance().getAmount())
        .isEqualTo(new BigDecimal("10.00"));

    assertThat(statementRepository.findAll().size()).isEqualTo(0);

  }

  @Test
  public void givenNoClient_whenSignup_ok() {

    RestAssured.given()
        .accept(ContentType.JSON)
        .contentType(ContentType.JSON)
        .body(SignupRequest.builder()
            .email("donger@gmail.com")
            .password("BIGBANKER")
            .build())
        .when()
        .post("http://localhost:8080/clients/signup")
        .then()
        .statusCode(200);

    RestAssured.given()
        .auth()
        .preemptive()
        .basic("donger@gmail.com", "BIGBANKER")
        .when()
        .get("http://localhost:8080/clients/statements")
        .then()
        .statusCode(200);


  }

  @Test
  public void givenNoClient_whenSignupWithSameEmail_throwsError() {
    RestAssured.given()
        .accept(ContentType.JSON)
        .contentType(ContentType.JSON)
        .body(SignupRequest.builder()
            .email("donger@gmail.com")
            .password("BIGBANKER")
            .build())
        .when()
        .post("http://localhost:8080/clients/signup")
        .then()
        .statusCode(200);

    RestAssured.given()
        .accept(ContentType.JSON)
        .contentType(ContentType.JSON)
        .body(SignupRequest.builder()
            .email("donger@gmail.com")
            .password("BIGBANKER")
            .build())
        .when()
        .post("http://localhost:8080/clients/signup")
        .then()
        .log().all()
        .statusCode(400)
        .body("debug", Matchers.is("User with email already exists"));
  }

}
