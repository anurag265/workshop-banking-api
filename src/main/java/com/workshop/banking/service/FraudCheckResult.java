package com.workshop.banking.service;

public class FraudCheckResult {

    private final boolean flagged;
    private final String reason;
    private final int riskScore;

    public FraudCheckResult(boolean flagged, String reason, int riskScore) {
        this.flagged = flagged;
        this.reason = reason;
        this.riskScore = riskScore;
    }

    public boolean isFlagged() {
        return flagged;
    }

    public String getReason() {
        return reason;
    }

    public int getRiskScore() {
        return riskScore;
    }
}
