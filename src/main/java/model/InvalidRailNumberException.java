package model;

/**
 * Custom exception class for handling invalid rail number scenarios in the Rail
 * Fence Cipher application. It is thrown when the number of rails provided for
 * the cipher is not valid.
 *
 * @author Magdalena Koncowicz
 * @version 1.0
 */
public class InvalidRailNumberException extends Exception {

    /**
     * Constructs a new exception with the specified detail message.
     *
     * @param message The detail message.
     */
    public InvalidRailNumberException(String message) {
        super(message);
    }
}
