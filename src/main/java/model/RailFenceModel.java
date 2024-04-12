package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Collectors;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.PersistenceException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Logger;
import java.util.logging.Level;
import util.EntityManagerFactoryListener;

/**
 * Model class in the MVC (Model-View-Controller) pattern for the Rail Fence
 * Cipher application. It contains the logic for encrypting and decrypting text
 * using the Rail Fence algorithm and manages database operations related to the
 * history of these operations.
 *
 * @author Magdalena Koncowicz
 * @version 1.0
 */
public class RailFenceModel {

    /**
     * The EntityManagerFactory used for creating EntityManager instances for
     * database operations.
     */
    private final EntityManagerFactory emf = EntityManagerFactoryListener.getEntityManagerFactory();

    /**
     * Logger for logging information, warnings, and errors.
     */
    private static final Logger LOGGER = Logger.getLogger(RailFenceModel.class.getName());

    /**
     * Stores an operation history record in the database.
     *
     * @param type The type of operation (either "Encrypt" or "Decrypt").
     * @param original The original text before processing.
     * @param processed The processed text after the operation.
     * @param rails The number of rails used in the Rail Fence Cipher.
     */
    public void createOperationHistory(String type, String original, String processed, int rails) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();

            OperationHistory history = new OperationHistory(type, original, processed, rails);
            history.setTimestamp(LocalDateTime.now());

            em.persist(history);
            em.getTransaction().commit();
        } catch (PersistenceException e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            LOGGER.log(Level.SEVERE, "Error creating operation history", e);
        } finally {
            em.close();
        }
    }

    /**
     * Retrieves the operation history from the database.
     *
     * @return A list of OperationHistory objects representing the history of
     * operations.
     */
    public List<OperationHistory> getOperationHistory() {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<OperationHistory> query = em.createQuery("SELECT h FROM OperationHistory h", OperationHistory.class);
            return query.getResultList();
        } catch (PersistenceException e) {
            LOGGER.log(Level.SEVERE, "Error retrieving operation history", e);
            return new ArrayList<>();
        } finally {
            em.close();
        }
    }

    /**
     * Encrypts the input text using Rail Fence Cipher with the specified number
     * of rails.
     *
     * @param text Input text to be encrypted
     * @param rails Number of rails for the Rail Fence Cipher
     * @return Encrypted text
     * @throws model.InvalidRailNumberException If the number of rails is
     * invalid.
     */
    public String encrypt(String text, int rails) throws InvalidRailNumberException {

        if (rails <= 1) {
            throw new InvalidRailNumberException("Number of rails must be at least 2.");
        }

        ArrayList<ArrayList<Character>> matrix = new ArrayList<>();
        for (int i = 0; i < rails; i++) {
            matrix.add(new ArrayList<>(Collections.nCopies(text.length(), '\0')));
        }

        int row = 0;
        boolean down = true;
        int col = 0;

        for (char c : text.toCharArray()) {
            matrix.get(row).set(col, c);

            if (row == 0) {
                down = true;
            } else if (row == rails - 1) {
                down = false;
            }

            if (down) {
                row++;
            } else {
                row--;
            }

            col++;
        }

        return matrix.stream()
                .flatMap(ArrayList::stream)
                .filter(c -> c != '\0')
                .map(Object::toString)
                .collect(Collectors.joining());
    }

    /**
     * Decrypts the input text encrypted using Rail Fence Cipher with the
     * specified number of rails.
     *
     * @param encryptedText Encrypted text to be decrypted
     * @param rails Number of rails used for encryption
     * @return Decrypted text
     * @throws model.InvalidRailNumberException If the number of rails is
     * invalid.
     */
    public String decrypt(String encryptedText, int rails) throws InvalidRailNumberException {

        if (rails <= 1) {
            throw new InvalidRailNumberException("Number of rails must be at least 2.");
        }

        ArrayList<ArrayList<Character>> matrix = new ArrayList<>();
        for (int i = 0; i < rails; i++) {
            matrix.add(new ArrayList<>(Collections.nCopies(encryptedText.length(), '\0')));
        }

        int row = 0;
        boolean down = true;

        for (int i = 0; i < encryptedText.length(); i++) {
            matrix.get(row).set(i, '*');

            if (row == 0) {
                down = true;
            } else if (row == rails - 1) {
                down = false;
            }
            row += down ? 1 : -1;
        }

        int index = 0;
        for (ArrayList<Character> rail : matrix) {
            for (int j = 0; j < rail.size(); j++) {
                if (rail.get(j) == '*' && index < encryptedText.length()) {
                    rail.set(j, encryptedText.charAt(index++));
                }
            }
        }

        StringBuilder decryptedText = new StringBuilder();
        row = 0;
        down = true;

        for (int i = 0; i < encryptedText.length(); i++) {
            decryptedText.append(matrix.get(row).get(i));

            if (row == 0) {
                down = true;
            } else if (row == rails - 1) {
                down = false;
            }
            row += down ? 1 : -1;

        }
        return decryptedText.toString();
    }
}
