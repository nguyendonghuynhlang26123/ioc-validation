package handler;

import violation.Violation;

import java.util.Collection;

public interface ViolationListener {
    void process(Violation data);
    boolean shouldProcess(Violation data);
}
