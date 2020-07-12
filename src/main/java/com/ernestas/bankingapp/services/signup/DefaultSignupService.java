package com.ernestas.bankingapp.services.signup;

import com.ernestas.bankingapp.domain.SignupRequest;
import com.ernestas.bankingapp.persistence.entities.Balance;
import com.ernestas.bankingapp.persistence.entities.Client;
import com.ernestas.bankingapp.persistence.repositories.BalanceRepository;
import com.ernestas.bankingapp.persistence.repositories.ClientRepository;
import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Primary
@Service
public class DefaultSignupService implements SignupService {

  private final ClientRepository clientRepository;

  private final BalanceRepository balanceRepository;

  private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

  @Autowired
  public DefaultSignupService(
      ClientRepository clientRepository,
      BalanceRepository balanceRepository) {
    this.clientRepository = clientRepository;
    this.balanceRepository = balanceRepository;
  }

  @Override
  public void signup(SignupRequest signupRequest) {

    if(!clientRepository.findByEmail(signupRequest.getEmail()).isPresent()) {
      clientRepository.saveAndFlush(Client.builder()
          .email(signupRequest.getEmail())
          .password(bCryptPasswordEncoder.encode(signupRequest.getPassword()))
          .balance(balanceRepository.saveAndFlush(Balance.builder().money(BigDecimal.ZERO).build()))
          .build());
    } else {
      throw new RuntimeException("User with email already exists");
    }
  }
}