package com.ernestas.bankingapp.domain;

import java.math.BigDecimal;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DepositRequest {

  @DecimalMin(value = "0.0", inclusive = false)
  @Digits(integer = 4, fraction = 2)
  private BigDecimal amount;

}
