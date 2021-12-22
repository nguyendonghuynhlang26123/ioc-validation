package handler;

import violation.Violation;

import java.util.Collection;

public interface ViolationListener {
    void update(Collection<Violation> data);
}
