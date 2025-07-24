package com.payflow.payflow.domain.ledger;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AccountName {
    REVENUE("REVENUE"),
    ITEM_BUYER("ITEM_BUYER");

    private final String value;
}