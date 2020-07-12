package com.ernestas.bankingapp.exception;

import java.time.ZonedDateTime;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExceptionResponse {

  @NotNull
  private String message;

  private String debug;
  @NotNull
  private ZonedDateTime timestamp;

}
