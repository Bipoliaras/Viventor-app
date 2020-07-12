package com.ernestas.bankingapp.api;

import com.ernestas.bankingapp.domain.DepositRequest;
import com.ernestas.bankingapp.domain.SignupRequest;
import com.ernestas.bankingapp.domain.WithdrawRequest;
import com.ernestas.bankingapp.persistence.entities.Balance;
import com.ernestas.bankingapp.persistence.entities.Statement;
import com.ernestas.bankingapp.services.banking.BankingService;
import com.ernestas.bankingapp.services.banking.StatementService;
import com.ernestas.bankingapp.services.signup.SignupService;
import java.math.BigDecimal;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/clients")
public class ClientController {

  private BankingService bankingService;

  private SignupService signupService;

  private StatementService statementService;

  @GetMapping
  public Balance getBalance(Authentication authentication) {
    return bankingService.getBalance(authentication.getName());
  }

  @PostMapping("/signup")
  public void signup(@RequestBody @Valid SignupRequest signupRequest) {
    signupService.signup(signupRequest);
  }

  @PostMapping("/deposit")
  public Balance depositMoney(Authentication authentication, @RequestBody @Valid DepositRequest depositRequest) {
    return bankingService.depositMoney(authentication.getName(), depositRequest);
  }

  @PostMapping("/withdraw")
  public BigDecimal withdrawMoney(Authentication authentication, @RequestBody @Valid WithdrawRequest withdrawRequest) {
    return bankingService.withdrawMoney(authentication.getName(), withdrawRequest);
  }

  @GetMapping("/statements")
  public List<Statement> getStatements(Authentication authentication) {
    return statementService.getStatements(authentication.getName());
  }

  @Autowired
  public void setBankingService(BankingService bankingService) {
    this.bankingService = bankingService;
  }

  @Autowired
  public void setSignupService(SignupService signupService) {
    this.signupService = signupService;
  }

  @Autowired
  public void setStatementService(
      StatementService statementService) {
    this.statementService = statementService;
  }
}
