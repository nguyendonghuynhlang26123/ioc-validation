package handler;

import violation.Violation;

import java.util.ArrayList;
import java.util.Collection;

public class ViolationHandler {
    private final Collection<ViolationListener> listeners = new ArrayList<>();

    public void subscribe(ViolationListener subscriber){
        listeners.add(subscriber);
    }

    public void unsubscribe(ViolationListener subscriber){
        listeners.remove(subscriber);
    }

    public void notify(Violation data){
        listeners.forEach(violationListener -> {
            if (violationListener.shouldProcess(data))
                violationListener.process(data);
        });
    }
}
