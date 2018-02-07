package hu.webarticum.zooofstrings.stringmutator;

import java.util.Arrays;

public class DefaultStringProvider extends AbstractStringProvider {

    private char[] content;

    public DefaultStringProvider() {
        this.content = new char[0];
    }
    
    public DefaultStringProvider(CharSequence initialContent) {
        this.content = initialContent.toString().toCharArray();
    }
    
    public DefaultStringProvider(char[] initialContent) {
        this.content = Arrays.copyOf(initialContent, initialContent.length);
    }
    
    @Override
    public char[] getContent() {
        return Arrays.copyOf(content, content.length);
    }

    public void set(CharSequence newContent) {
        setInternal(newContent.toString().toCharArray());
    }

    public void set(char[] newContent) {
        setInternal(Arrays.copyOf(newContent, newContent.length));
    }
    
    private void setInternal(char[] newContentReference) {
        content = newContentReference;
        fireUpdated();
    }

}
