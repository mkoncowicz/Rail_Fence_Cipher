package model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Test class for RailFenceModel. It includes tests for both encryption and
 * decryption methods using the Rail Fence Cipher algorithm.
 *
 * @author Magdalena Koncowicz
 * @version 1.0
 */
public class RailFenceModelTest {

    /**
     * An instance of RailFenceModel used for testing. This model provides the
     * functionality for encrypting and decrypting text using the Rail Fence
     * Cipher algorithm.
     */
    private final RailFenceModel model = new RailFenceModel();

    // Correct Situations
    /**
     * Tests the encryption functionality with valid input and expected output.
     * Verifies that the Rail Fence Cipher algorithm encrypts a string correctly
     * using the specified number of rails.
     *
     * @throws InvalidRailNumberException If the number of rails is invalid.
     */
    @Test
    public void testEncryptWithValidInput() throws InvalidRailNumberException {
        String input = "HelloWorld";
        int rails = 3;
        String expectedOutput = "HolelWrdlo";
        assertEquals(expectedOutput, model.encrypt(input, rails));
    }

    /**
     * Tests the decryption functionality with valid input and expected output.
     * Verifies that the Rail Fence Cipher algorithm decrypts a string correctly
     * using the specified number of rails.
     *
     * @throws InvalidRailNumberException If the number of rails is invalid.
     */
    @Test
    public void testDecryptWithValidInput() throws InvalidRailNumberException {
        String input = "HorelWdloL";
        int rails = 3;
        String expectedOutput = "HeoloWLdrl";
        assertEquals(expectedOutput, model.decrypt(input, rails));
    }

    //Incorrect Situations 
    /**
     * Tests the encryption method with an invalid number of rails. Verifies
     * that the method throws an InvalidRailNumberException when the number of
     * rails is less than the minimum required.
     */
    @Test
    public void testEncryptWithInvalidRails() {
        String input = "HelloWorld";
        int rails = 1;
        assertThrows(InvalidRailNumberException.class, () -> model.encrypt(input, rails));
    }

    /**
     * Tests the decryption method with an invalid number of rails. Verifies
     * that the method throws an InvalidRailNumberException when the number of
     * rails is less than the minimum required.
     */
    @Test
    public void testDecryptWithInvalidRails() {
        String input = "HorelWdloL";
        int rails = 1;
        assertThrows(InvalidRailNumberException.class, () -> model.decrypt(input, rails));
    }

    //Border Situations
    /**
     * Tests the encryption method with an empty string. Verifies that the
     * method returns an empty string when the input is empty.
     *
     * @throws InvalidRailNumberException If the number of rails is invalid.
     */
    @Test
    public void testEncryptWithEmptyString() throws InvalidRailNumberException {
        String input = "";
        int rails = 3;
        String expected = "";
        assertEquals(expected, model.encrypt(input, rails));
    }

    /**
     * Tests the decryption method with an empty string. Verifies that the
     * method returns an empty string when the input is empty.
     *
     * @throws InvalidRailNumberException If the number of rails is invalid.
     */
    @Test
    public void testDecryptWithEmptyString() throws InvalidRailNumberException {
        String input = "";
        int rails = 3;
        String expected = "";
        assertEquals(expected, model.decrypt(input, rails));
    }

    /**
     * Tests the encryption method with a single character. Verifies that the
     * method returns the same single character when the input is a single
     * character.
     *
     * @throws InvalidRailNumberException If the number of rails is invalid.
     */
    @Test
    public void testEncryptWithOneCharacter() throws InvalidRailNumberException {
        String input = "H";
        int rails = 3;
        String expected = "H";
        assertEquals(expected, model.encrypt(input, rails));
    }

    /**
     * Tests the decryption method with a single character. Verifies that the
     * method returns the same single character when the input is a single
     * character.
     *
     * @throws InvalidRailNumberException If the number of rails is invalid.
     */
    @Test
    public void testDecryptWithOneCharacter() throws InvalidRailNumberException {
        String input = "H";
        int rails = 3;
        String expected = "H";
        assertEquals(expected, model.decrypt(input, rails));
    }

    /**
     * Tests the encryption method when the number of rails equals the length of
     * the input text. Verifies that the method returns the same text when the
     * number of rails equals the text length.
     *
     * @throws InvalidRailNumberException If the number of rails is invalid.
     */
    @Test
    public void testEncryptWithRailsEqualToTextLength() throws InvalidRailNumberException {
        String input = "Hello";
        int rails = 5;
        String expected = "Hello";
        assertEquals(expected, model.encrypt(input, rails));
    }

    /**
     * Tests the decryption method when the number of rails equals the length of
     * the input text. Verifies that the method returns the same text when the
     * number of rails equals the text length.
     *
     * @throws InvalidRailNumberException If the number of rails is invalid.
     */
    @Test
    public void testDecryptWithRailsEqualToTextLength() throws InvalidRailNumberException {
        String input = "Hello";
        int rails = 5;
        String expected = "Hello";
        assertEquals(expected, model.decrypt(input, rails));
    }

    //Parametrized Tests
    /**
     * Parameterized test to verify the encryption functionality of the
     * RailFenceModel with various inputs. This test checks the correctness of
     * the Rail Fence Cipher encryption for different combinations of input text
     * and number of rails. It ensures that the encrypted output matches the
     * expected result for each set of inputs.
     *
     * @param input The input string to be encrypted.
     * @param rails The number of rails to use in the Rail Fence Cipher.
     * @param expected The expected encrypted output for the given input and
     * rail combination.
     * @throws InvalidRailNumberException If the number of rails is invalid.
     */
    @ParameterizedTest
    @CsvSource({
        "HelloWorld, 3, HolelWrdlo",
        "SingleRow, 2, SnlRwigeo",
        "'', 3, ''",
        "A, 2, A"
    })
    public void testEncryptWithVariousInputs(String input, int rails, String expected) throws InvalidRailNumberException {
        assertEquals(expected, model.encrypt(input, rails));
    }

    /**
     * Tests the encryption functionality of the RailFenceModel with invalid
     * number of rails. This test ensures that an InvalidRailNumberException is
     * thrown when the number of rails provided for encryption is less than the
     * minimum required.
     *
     * @param input The input string to be encrypted.
     * @param rails The number of rails to use in the Rail Fence Cipher,
     * expected to be invalid (less than 2).
     */
    @ParameterizedTest
    @CsvSource({
        "HelloWorld, 0",
        "InvalidRails, -1"
    })
    public void testEncryptWithInvalidRails(String input, int rails) {
        assertThrows(InvalidRailNumberException.class, () -> model.encrypt(input, rails));
    }
}
