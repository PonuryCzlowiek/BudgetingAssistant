package org.ametyst.budgeting;

import java.io.Serializable;

public class ErrorResponseMessage implements Serializable {
    private final String message;

    public ErrorResponseMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
