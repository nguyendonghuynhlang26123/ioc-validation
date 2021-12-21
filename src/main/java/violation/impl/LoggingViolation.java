package violation.impl;

import violation.Violation;
import violation.ViolationListener;

import java.util.Collection;

public class LoggingViolation implements ViolationListener {

    @Override
    public void update(Collection<Violation> data) {
        data.forEach(System.out::println);
    }
}
