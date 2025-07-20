package com.abhayCodez.ridesharing.service;

import com.abhayCodez.ridesharing.entity.Account;
import com.abhayCodez.ridesharing.enums.Role;
import com.abhayCodez.ridesharing.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AdminService {

    @Autowired
    private AccountRepository accountRepository;

    public Account getByEmail(String email) {
        return accountRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Account not found."));
    }

    public List<Account> getAll() {
        return accountRepository.findAll();
    }

    public void blockAccount(String email) {
        Account account = getByEmail(email);
        account.setRole(Role.BLOCKED_USER);
        account.setUpdatedAt(LocalDateTime.now());
        accountRepository.save(account);
    }
}
