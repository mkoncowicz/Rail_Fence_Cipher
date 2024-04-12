package model;

/**
 * Functional interface defining a strategy for encryption and decryption in the
 * Rail Fence Cipher. It abstracts the process of applying a cipher strategy,
 * allowing different implementations for encryption and decryption.
 *
 * @author Magdalena Koncowicz
 * @version 1.0
 */
@FunctionalInterface
public interface CipherStrategy {

    /**
     * Applies the cipher strategy to the given text with the specified number
     * of rails.
     *
     * @param text The text to be processed.
     * @param rails The number of rails to use in the cipher.
     * @return The processed text after applying the cipher.
     * @throws InvalidRailNumberException If the number of rails is invalid.
     */
    String apply(String text, int rails) throws InvalidRailNumberException;
}
