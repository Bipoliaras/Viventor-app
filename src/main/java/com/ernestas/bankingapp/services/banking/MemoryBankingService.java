package com.ernestas.bankingapp.services.banking;

import com.ernestas.bankingapp.domain.DepositRequest;
import com.ernestas.bankingapp.domain.StatementType;
import com.ernestas.bankingapp.domain.WithdrawRequest;
import com.ernestas.bankingapp.persistence.entities.Statement;
import com.ernestas.bankingapp.persistence.repositories.BalanceRepository;
import com.ernestas.bankingapp.persistence.repositories.ClientRepository;
import com.ernestas.bankingapp.persistence.entities.Balance;
import com.ernestas.bankingapp.persistence.entities.Client;
import com.ernestas.bankingapp.persistence.repositories.StatementRepository;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Primary
@Service
public class MemoryBankingService implements BankingService {

  private ClientRepository clientRepository;

  private BalanceRepository balanceRepository;

  private StatementRepository statementRepository;

  @Override
  public Balance depositMoney(String email, DepositRequest depositRequest) {
    Client client = getClient(email);

    Balance balance = client.getBalance();

    balance.setMoney(balance.getMoney().add(depositRequest.getAmount()));

    createStatement(client, StatementType.DEPOSIT, depositRequest.getAmount());

    return balanceRepository.saveAndFlush(balance);

  }

  @Override
  public BigDecimal withdrawMoney(String email, WithdrawRequest withdrawRequest) {
    Client client = getClient(email);

    Balance balance = client.getBalance();

    if (balance.getMoney().subtract(withdrawRequest.getAmount()).compareTo(BigDecimal.ZERO) > 0) {

      createStatement(client, StatementType.WITHDRAWAL, withdrawRequest.getAmount());

      balance.setMoney(balance.getMoney().subtract(withdrawRequest.getAmount()));
      balanceRepository.saveAndFlush(balance);

      return withdrawRequest.getAmount();

    } else {
      throw new RuntimeException("Current balance insufficient");
    }

  }

  @Override
  public Balance getBalance(String email) {
    Client client = getClient(email);

    return client.getBalance();
  }

  private void createStatement(Client client, StatementType statementType, BigDecimal amount) {
    statementRepository.saveAndFlush(Statement.builder()
        .client(client)
        .statementType(statementType)
        .amount(amount)
        .statementTime(ZonedDateTime.now())
        .build()
    );
  }

  private Client getClient(String email) {
    return clientRepository.findByEmail(email)
        .orElseThrow(() -> new RuntimeException("Client not found"));
  }

  @Autowired
  public void setClientRepository(ClientRepository clientRepository) {
    this.clientRepository = clientRepository;
  }

  @Autowired
  public void setBalanceRepository(BalanceRepository balanceRepository) {
    this.balanceRepository = balanceRepository;
  }

  @Autowired
  public void setStatementRepository(
      StatementRepository statementRepository) {
    this.statementRepository = statementRepository;
  }
}