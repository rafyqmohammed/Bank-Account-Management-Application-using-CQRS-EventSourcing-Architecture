package com.example.demo.appbankevensoucqrs.query.repository;

import com.example.demo.appbankevensoucqrs.query.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, String> {
}