package com.assignment.rewardsapi.exception;

/**
 * Custom exception for business rule or validation errors
 * in reward calculations.
 */
public class RewardException extends RuntimeException {
    public RewardException(String message) {
        super(message);
    }
}
