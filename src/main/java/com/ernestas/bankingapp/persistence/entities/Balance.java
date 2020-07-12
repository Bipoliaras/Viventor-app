package com.ernestas.bankingapp.persistence.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.math.BigDecimal;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "t_balance")
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Data
public class Balance {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @JsonIgnore
  private Long id;

  private BigDecimal amount;

  @OneToOne(mappedBy = "balance")
  @JsonIgnore
  private Client client;

}
