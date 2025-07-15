package com.payflow.payflow.client.toss;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class TossPaymentConfirmationResponse {

    private String version;
    private String paymentKey;
    private String type;
    private String orderId;
    private String orderName;
    private String mId;
    private String currency;
    private String method;
    private int totalAmount;
    private int balanceAmount;
    private String status;
    private String requestedAt;
    private String approvedAt;
    private boolean useEscrow;
    private String lastTransactionKey;
    private int suppliedAmount;
    private int vat;
    private boolean cultureExpense;
    private int taxFreeAmount;
    private int taxExemptionAmount;
    private List<Cancel> cancels;
    private Card card;
    private VirtualAccount virtualAccount;
    private MobilePhone mobilePhone;
    private GiftCertificate giftCertificate;
    private Transfer transfer;
    private Receipt receipt;
    private Checkout checkout;
    private EasyPay easyPay;
    private String country;
    private TossFailureResponse failure;
    private CashReceipt cashReceipt;
    private List<CashReceipt> cashReceipts;
    private Discount discount;

    @Getter
    @NoArgsConstructor
    public static class Cancel {
        private int cancelAmount;
        private String cancelReason;
        private int taxFreeAmount;
        private int taxExemptionAmount;
        private int refundableAmount;
        private int easyPayDiscountAmount;
        private String canceledAt;
        private String transactionKey;
        private String receiptKey;
        private boolean isPartialCancelable;
    }

    @Getter
    @NoArgsConstructor
    public static class Card {
        private int amount;
        private String issuerCode;
        private String acquirerCode;
        private String number;
        private int installmentPlanMonths;
        private String approveNo;
        private boolean useCardPoint;
        private String cardType;
        private String ownerType;
        private String acquireStatus;
        private boolean isInterestFree;
        private String interestPayer;
    }

    @Getter
    @NoArgsConstructor
    public static class VirtualAccount {
        private String accountType;
        private String accountNumber;
        private String bankCode;
        private String customerName;
        private String dueDate;
        private String refundStatus;
        private boolean expired;
        private String settlementStatus;
        private RefundReceiveAccount refundReceiveAccount;
        private String secret;
    }

    @Getter
    @NoArgsConstructor
    public static class RefundReceiveAccount {
        private String bankCode;
        private String accountNumber;
        private String holderName;
    }

    @Getter
    @NoArgsConstructor
    public static class MobilePhone {
        private String customerMobilePhone;
        private String settlementStatus;
        private String receiptUrl;
    }

    @Getter
    @NoArgsConstructor
    public static class GiftCertificate {
        private String approveNo;
        private String settlementStatus;
    }

    @Getter
    @NoArgsConstructor
    public static class Transfer {
        private String bankCode;
        private String settlementStatus;
    }

    @Getter
    @NoArgsConstructor
    public static class Receipt {
        private String url;
    }

    @Getter
    @NoArgsConstructor
    public static class Checkout {
        private String url;
    }

    @Getter
    @NoArgsConstructor
    public static class EasyPay {
        private String provider;
        private int amount;
        private int discountAmount;
    }

    @Getter
    @NoArgsConstructor
    public static class TossFailureResponse {
        private String code;
        private String message;
    }

    @Getter
    @NoArgsConstructor
    public static class CashReceipt {
        private String type;
        private String receiptKey;
        private String issueNumber;
        private String receiptUrl;
        private int amount;
        private int taxFreeAmount;
    }

    @Getter
    @NoArgsConstructor
    public static class Discount {
        private int amount;
    }
}
