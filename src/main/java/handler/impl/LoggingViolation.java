package handler.impl;

import violation.Violation;
import handler.ViolationListener;

public class LoggingViolation implements ViolationListener {
    @Override
    public void process(Violation data) {
        System.out.println("Logging listener: " + data);
    }

    @Override
    public boolean shouldProcess(Violation data) {
        //Handle every cases
        //TODO: test
        return data.getRootClass().equals(String.class);
    }
}
