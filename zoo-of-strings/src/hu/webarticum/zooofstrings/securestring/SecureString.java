package hu.webarticum.zooofstrings.securestring;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Random;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;

/**
 * A clearable view of a character array, can be used for passwords
 */
public class SecureString implements CharSequence {
    
    private final char[] characters;
    
    private final int start;
    
    private final int length;
    
    private volatile boolean cleared = false;

    public SecureString(Iterator<Character> iterator) {
        this(iterateCharacters(iterator));
    }
    
    public SecureString(char[] characters) {
        this(characters, 0, characters.length);
    }
    
    public SecureString(char[] characters, int start, int length) {
        this.characters = characters;
        this.start = start;
        this.length = length;
    }
    
    private static char[] iterateCharacters(Iterator<Character> iterator) {
        final int BUFFER_CAPACITY = 10;
        char[] characters = new char[0];
        char[] buffer = new char[BUFFER_CAPACITY];
        int bufferSize = 0;
        while (iterator.hasNext()) {
            buffer[bufferSize] = iterator.next();
            bufferSize++;
            if (bufferSize == BUFFER_CAPACITY) {
                characters = moveBuffer(characters, buffer, bufferSize);
                bufferSize = 0;
            }
        }
        if (bufferSize > 0) {
            characters = moveBuffer(characters, buffer, bufferSize);
        }
        return characters;
    }
    
    private static char[] moveBuffer(char[] characters, char[] buffer, int bufferSize) {
        char[] result = Arrays.copyOf(characters, characters.length + bufferSize);
        for (int i = 0; i < bufferSize; i++) {
            result[characters.length + i] = buffer[i];
        }
        clearCharArray(characters);
        clearCharArray(buffer, 0, bufferSize);
        return result;
    }
    
    @Override
    public int length() {
        return length;
    }

    @Override
    public char charAt(int index) {
        return characters[start + index];
    }

    @Override
    public CharSequence subSequence(int start, int end) {
        return new SecureString(characters, this.start + start, end - start);
    }
    
    public boolean isCleared() {
        return cleared;
    }
    
    synchronized public void clear() {
        if (!cleared) {
            clearCharArray(characters, start, start + length);
            cleared = true;
        }
    }

    public byte[] encrypt(Cipher initedEncryptionCipher, Charset charset) throws BadPaddingException, IllegalBlockSizeException {
        CharBuffer charBuffer = CharBuffer.wrap(characters);
        ByteBuffer byteBuffer = charset.encode(charBuffer);
        byte[] sourceBytes = Arrays.copyOfRange(
            byteBuffer.array(),
            byteBuffer.position(),
            byteBuffer.limit()
        );
        clearByteArray(byteBuffer.array());
        
        byte[] encryptedBytes;
        try {
            encryptedBytes = initedEncryptionCipher.doFinal(sourceBytes);
        } catch (IllegalBlockSizeException e) {
            throw e;
        } catch (BadPaddingException e) {
            throw e;
        } finally {
            clearByteArray(sourceBytes);
        }
        
        return encryptedBytes;
    }
    
    @Override
    public String toString() {
        return "@secure";
    }
    
    @Override
    protected void finalize() throws Throwable {
        clear();
        super.finalize();
    }
    
    static public SecureString decrypt(Cipher initedDecryptionCipher, Charset charset, byte[] bytes) throws IllegalBlockSizeException, BadPaddingException {
        byte[] decodedBytes = initedDecryptionCipher.doFinal(bytes);
        
        ByteBuffer byteBuffer = ByteBuffer.wrap(decodedBytes);
        CharBuffer charBuffer = charset.decode(byteBuffer);
        clearByteArray(decodedBytes);
        char[] characters = Arrays.copyOfRange(
            charBuffer.array(),
            charBuffer.position(),
            charBuffer.limit()
        );
        clearCharArray(charBuffer.array());
        return new SecureString(characters);
    }

    static public void clearCharArray(char[] characters) {
        clearCharArray(characters, 0, characters.length);
    }
    
    static public void clearCharArray(char[] characters, int from, int to) {
        Random random = new SecureRandom();
        for (int i = from; i < to; i++) {
            characters[i] = (char)(random.nextInt(254) + 1);
        }
    }
    
    static public void clearByteArray(byte[] bytes) {
        Random random = new SecureRandom();
        random.nextBytes(bytes);
    }
    
}