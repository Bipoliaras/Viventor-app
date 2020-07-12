package com.ernestas.bankingapp;

import com.ernestas.bankingapp.persistence.entities.Balance;
import com.ernestas.bankingapp.persistence.entities.Client;
import com.ernestas.bankingapp.persistence.repositories.BalanceRepository;
import com.ernestas.bankingapp.persistence.repositories.ClientRepository;
import io.restassured.RestAssured;
import java.math.BigDecimal;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.junit4.SpringRunner;

@Ignore
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@Sql(scripts = {"/db/delete-data.sql"}, executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = {"/db/delete-data.sql"}, executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
public class ITBase {
  @LocalServerPort
  protected int serverPort;

  @Autowired
  ClientRepository clientRepository;

  @Autowired
  BalanceRepository balanceRepository;

  private final BCryptPasswordEncoder bCryptPasswordEncoder = new
      BCryptPasswordEncoder();

  public void createUser() {

      clientRepository.saveAndFlush(Client.builder()
          .email("banker@gmail.com")
          .password(bCryptPasswordEncoder.encode("banker"))
          .balance(balanceRepository.saveAndFlush(Balance.builder().amount(BigDecimal.TEN).build()))
          .build());
  }

  @Before
  public void setup() {
    RestAssured.port = serverPort;
  }
}


