package com.japhtech.mini_banking_api.dto;

import lombok.Data;

@Data
public class TransferRequest {
    private String receiverEmail;
    private Double amount;
}
