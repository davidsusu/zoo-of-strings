package hu.webarticum.zooofstrings.stringmutator;

/**
 * Handles a String reference which can be modified via a StringProvider
 * 
 * Idea from: https://stackoverflow.com/questions/11146255/create-a-mutable-java-lang-string
 */
public class StringMutator {

    private final StringProvider provider;
    
    private String stringReference = null;
    
    public StringMutator(StringProvider provider) {
        this.provider = provider;
    }
    
    public String getStringReference() {
        if (stringReference == null) {
            
            // TODO
            stringReference = "ASDF";
            
        }
        return stringReference;
    }
    
}
