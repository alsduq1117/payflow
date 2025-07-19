package com.payflow.payflow.service.wallet;

import com.payflow.payflow.domain.payment.PaymentConfirmationSuccessEvent;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WalletService {



    @Transactional
    public void processSettlement(PaymentConfirmationSuccessEvent walletEvent) {

    }
}
