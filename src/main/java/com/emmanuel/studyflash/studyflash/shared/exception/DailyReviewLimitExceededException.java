package com.emmanuel.studyflash.studyflash.shared.exception;

public class DailyReviewLimitExceededException extends RuntimeException {
    public DailyReviewLimitExceededException() {
        super("Daily review limit reached. Try again tomorrow.");
    }
}
