package hu.webarticum.zooofstrings.stringadapter;

/**
 * Adapts a CharSequnece to StringInterface
 */
public class StringAdapter implements StringInterface {

    private final CharSequence charSequence;
    
    public StringAdapter(CharSequence charSequence) {
        this.charSequence = charSequence;
    }

    @Override
    public int length() {
        
        // TODO
        
        return 0;
    }

    @Override
    public char charAt(int index) {
        
        // TODO
        
        return 0;
    }

    @Override
    public CharSequence subSequence(int start, int end) {
        
        // TODO
        
        return null;
    }
    
    // TODO
    
}
