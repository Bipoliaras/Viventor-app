package com.ernestas.bankingapp.persistence.entities;


import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "t_client")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Client {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "column_id")
  private Long id;

  @OneToOne
  private Balance balance;

  @NotNull
  private String email;

  @NotNull
  private String password;

  @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
  private List<Statement> bankStatements = new ArrayList<>();

}
