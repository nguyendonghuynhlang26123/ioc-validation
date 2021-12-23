package handler.impl;

import violation.Violation;
import handler.ViolationListener;

import java.util.Collection;

public class LoggingViolation implements ViolationListener {
    @Override
    public void handle(Violation data) {
        System.out.println(data);
    }

    @Override
    public boolean shouldHandle(Violation data) {
        //Handle every cases
        return true;
    }
}
