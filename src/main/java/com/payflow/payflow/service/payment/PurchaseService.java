package com.payflow.payflow.service.payment;

import com.payflow.payflow.repository.payment.PurchaseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PurchaseService {

    private final PurchaseRepository purchaseRepository;

    @Transactional(readOnly = true)
    public Boolean hasPurchasedProduct(Long productId, Long userId) {
        return purchaseRepository.hasPurchasedProduct(productId, userId);
    }
}
