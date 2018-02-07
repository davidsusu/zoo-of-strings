package hu.webarticum.zooofstrings.stringmutator;

public interface StringProvider {
    
    public char[] getContent();
    
    public void addListener(Listener listener);

    public void removeListener(Listener listener);
    
    public interface Listener {
        
        public void updated();
        
    }
    
}
