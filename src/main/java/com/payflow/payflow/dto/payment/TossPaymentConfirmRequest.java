package com.payflow.payflow.dto.payment;


import com.payflow.payflow.domain.payment.PaymentConfirmCommand;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TossPaymentConfirmRequest {

    @NotBlank(message = "paymentKey 값이 없습니다.")
    private String paymentKey;

    @NotBlank(message = "orderId 값이 없습니다.")
    private String orderId;

    @NotNull(message = "금액을 입력해주세요.")
    @Positive(message = "금액은 양수여야 합니다.")
    private Long amount;


    @Builder
    public TossPaymentConfirmRequest(String paymentKey, String orderId, Long amount) {
        this.paymentKey = paymentKey;
        this.orderId = orderId;
        this.amount = amount;
    }

    public PaymentConfirmCommand toCommand() {
        return PaymentConfirmCommand.builder()
                .paymentKey(paymentKey)
                .orderId(orderId)
                .amount(amount)
                .build();
    }
}
