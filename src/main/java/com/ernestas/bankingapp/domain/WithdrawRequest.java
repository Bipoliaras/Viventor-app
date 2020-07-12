package com.ernestas.bankingapp.domain;

import java.math.BigDecimal;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WithdrawRequest {

  @DecimalMin(value = "0.0", inclusive = false)
  @Digits(integer = 4, fraction = 2)
  private BigDecimal amount;

}
