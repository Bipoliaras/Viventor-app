package com.ernestas.bankingapp.domain;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class SignupRequest {

  @NotNull
  private String password;

  @NotNull
  @Email(message = "Email should be valid")
  private String email;

}
