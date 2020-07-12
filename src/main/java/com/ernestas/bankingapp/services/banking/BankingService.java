package com.ernestas.bankingapp.services.banking;

import com.ernestas.bankingapp.domain.DepositRequest;
import com.ernestas.bankingapp.domain.WithdrawRequest;
import com.ernestas.bankingapp.persistence.entities.Balance;
import java.math.BigDecimal;

/* Interface for the banking application
 *


 */
public interface BankingService {

  Balance depositMoney(String username, DepositRequest depositRequest);

  BigDecimal withdrawMoney(String username, WithdrawRequest withdrawRequest);

  Balance getBalance(String username);

}
