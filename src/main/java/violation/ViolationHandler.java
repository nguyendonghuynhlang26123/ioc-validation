package violation;

import java.util.ArrayList;
import java.util.Collection;

public class ViolationHandler {
    private Collection<ViolationListener> listeners = new ArrayList<>();

    public void subscribe(ViolationListener subscriber){
        listeners.add(subscriber);
    }

    public void unsubscribe(ViolationListener subscriber){
        listeners.remove(subscriber);
    }

    public void notify(Collection<Violation> data){
        listeners.forEach(violationListener -> violationListener.update(data));
    }
}
