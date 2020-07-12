package com.ernestas.bankingapp.persistence.entities;


import com.ernestas.bankingapp.domain.StatementType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "t_statement")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Statement {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Enumerated(value = EnumType.STRING)
  private StatementType statementType;

  private BigDecimal amount;

  @ManyToOne
  @JoinColumn(name = "client_id")
  @JsonIgnore
  private Client client;

  @Basic
  private ZonedDateTime statementTime;


}
