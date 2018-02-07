package hu.webarticum.zooofstrings.stringmutator;

import java.util.ArrayList;

public abstract class AbstractStringProvider implements StringProvider {

    ArrayList<Listener> listeners = new ArrayList<Listener>();
    
    public void addListener(Listener listener) {
        listeners.add(listener);
        listeners.trimToSize();
    }

    public void removeListener(Listener listener) {
        listeners.remove(listener);
        listeners.trimToSize();
    }
    
    protected void fireUpdated() {
        for (Listener listener: listeners) {
            listener.updated();
        }
    }
    
}
