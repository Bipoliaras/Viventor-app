package com.ernestas.bankingapp.services.helper;

import com.ernestas.bankingapp.persistence.entities.Balance;
import com.ernestas.bankingapp.persistence.entities.Client;
import com.ernestas.bankingapp.persistence.repositories.BalanceRepository;
import com.ernestas.bankingapp.persistence.repositories.ClientRepository;
import java.math.BigDecimal;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class InitialDataLoader {


  private final BCryptPasswordEncoder bCryptPasswordEncoder = new
      BCryptPasswordEncoder();

  @Autowired
  private ClientRepository clientRepository;

  @Autowired
  private BalanceRepository balanceRepository;

  @PostConstruct
  public void createUser() {

    clientRepository.saveAndFlush(Client.builder()
        .email("banker@gmail.com")
        .password(bCryptPasswordEncoder.encode("banker"))
        .balance(balanceRepository.saveAndFlush(Balance.builder().amount(BigDecimal.TEN).build()))
        .build());

  }
}
