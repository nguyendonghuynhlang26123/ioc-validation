package handler.impl;

import violation.Violation;
import handler.ViolationListener;

import java.util.Collection;

public class LoggingViolation implements ViolationListener {

    @Override
    public void update(Collection<Violation> data) {
        data.forEach(System.out::println);
    }
}
