package violation;

import java.util.Collection;

public interface ViolationListener {
    void update(Collection<Violation> data);
}
