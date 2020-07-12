package com.ernestas.bankingapp.persistence.repositories;

import com.ernestas.bankingapp.persistence.entities.Client;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, String> {

  Optional<Client> findByEmail(String email);

}
