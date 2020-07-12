package com.ernestas.bankingapp.services.security;

import com.ernestas.bankingapp.exception.NotFoundException;
import com.ernestas.bankingapp.persistence.entities.Client;
import com.ernestas.bankingapp.persistence.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class DatabaseUserDetailsService implements UserDetailsService {

  private final ClientRepository clientRepository;

  @Autowired
  public DatabaseUserDetailsService(ClientRepository clientRepository) {
    this.clientRepository = clientRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String s) {
    Client client = clientRepository.findByEmail(s)
        .orElseThrow(() -> new NotFoundException("Client not found"));

    return User.builder()
        .username(client.getEmail())
        .password(client.getPassword())
        .authorities("READ", "WRITE")
        .build();
  }
}
