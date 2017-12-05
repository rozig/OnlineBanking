package com.services;

import com.models.CallResponse;

public interface BankingService {
    public CallResponse DoTransaction();
    public CallResponse CheckBalance();
}
