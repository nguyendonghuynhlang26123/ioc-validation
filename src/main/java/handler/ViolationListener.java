package handler;

import violation.Violation;

import java.util.Collection;

public interface ViolationListener {
    void handle(Violation data);
    boolean shouldHandle(Violation data);
}
