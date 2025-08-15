package com.payflow.payflow.dto.admin;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DashboardMetricResponse {

    private long todaySuccessCount;
    private long todaySuccessAmount;
    private long todayFailureCount;
    private int todayFailureRate;   // 실패율 = fail / (succ+fail) * 100
    private int todayApproveRate;   // 승인률 = 100 - todayFailureRate
    private int lastHourApproveRate;
    private long approvalDelayExceededCount;
    private long highAmountCount;
    private long highAmountSum;


    @Builder
    public DashboardMetricResponse(long todaySuccessCount, long todaySuccessAmount, long todayFailureCount, int todayFailureRate, int todayApproveRate, int lastHourApproveRate, long approvalDelayExceededCount, long highAmountCount, long highAmountSum) {
        this.todaySuccessCount = todaySuccessCount;
        this.todaySuccessAmount = todaySuccessAmount;
        this.todayFailureCount = todayFailureCount;
        this.todayFailureRate = todayFailureRate;
        this.todayApproveRate = todayApproveRate;
        this.lastHourApproveRate = lastHourApproveRate;
        this.approvalDelayExceededCount = approvalDelayExceededCount;
        this.highAmountCount = highAmountCount;
        this.highAmountSum = highAmountSum;
    }
}
