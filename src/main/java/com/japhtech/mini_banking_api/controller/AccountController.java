package com.japhtech.mini_banking_api.controller;

import com.japhtech.mini_banking_api.dto.DepositRequest;
import com.japhtech.mini_banking_api.dto.TransferRequest;
import com.japhtech.mini_banking_api.model.Transaction;
import com.japhtech.mini_banking_api.model.User;
import com.japhtech.mini_banking_api.repository.TransactionRepository;
import com.japhtech.mini_banking_api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/account")
@RequiredArgsConstructor
public class AccountController {

    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;

    @GetMapping("/balance")
    public Double balance(@RequestParam String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return user.getBalance();
    }

    @PostMapping("/deposit")
    public User deposit(@RequestParam String email, @RequestBody DepositRequest request) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setBalance(user.getBalance() + request.getAmount());

        Transaction transaction = Transaction.builder()
                .senderEmail("BANK")
                .receiverEmail(email)
                .amount(request.getAmount())
                .transactionType("DEPOSIT")
                .createdAt(LocalDateTime.now())
                .build();

        transactionRepository.save(transaction);
        return userRepository.save(user);
    }

    @PostMapping("/transfer")
    public String transfer(@RequestParam String senderEmail, @RequestBody TransferRequest request) {
        User sender = userRepository.findByEmail(senderEmail)
                .orElseThrow(() -> new RuntimeException("Sender not found"));

        User receiver = userRepository.findByEmail(request.getReceiverEmail())
                .orElseThrow(() -> new RuntimeException("Receiver not found"));

        if (sender.getBalance() < request.getAmount()) {
            throw new RuntimeException("Insufficient balance");
        }

        sender.setBalance(sender.getBalance() - request.getAmount());
        receiver.setBalance(receiver.getBalance() + request.getAmount());

        userRepository.save(sender);
        userRepository.save(receiver);

        Transaction transaction = Transaction.builder()
                .senderEmail(senderEmail)
                .receiverEmail(request.getReceiverEmail())
                .amount(request.getAmount())
                .transactionType("TRANSFER")
                .createdAt(LocalDateTime.now())
                .build();

        transactionRepository.save(transaction);

        return "Transfer successful";
    }

    @GetMapping("/transactions")
    public List<Transaction> transactions(@RequestParam String email) {
        return transactionRepository.findBySenderEmailOrReceiverEmail(email, email);
    }
}
