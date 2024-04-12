package model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;

/**
 * Entity class representing an operation history in the Rail Fence Cipher
 * application. This class stores details of each encryption or decryption
 * operation, including the type of operation, the original text, the processed
 * text, the number of rails used, and the timestamp of the operation.
 */
@Entity
public class OperationHistory {

    /**
     * The unique identifier for each operation history record.
     * This field is automatically generated and assigned.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The type of operation performed, e.g., "Encrypt" or "Decrypt".
     */
    private String operationType;

    /**
     * The original text that was input for the operation.
     */
    private String originalText;

    /**
     * The resulting text after the operation was performed.
     */
    private String processedText;

    /**
     * The number of rails used in the Rail Fence Cipher for the operation.
     */
    private int rails;

    /**
     * The timestamp indicating when the operation was performed.
     */
    private LocalDateTime timestamp;

    /**
     * Default constructor for creating an instance of OperationHistory.
     */
    public OperationHistory() {
    }

    /**
     * Parameterized constructor for creating an instance of OperationHistory
     * with specific details.
     *
     * @param operationType The type of operation (e.g., "Encrypt" or
     * "Decrypt").
     * @param originalText The original text before processing.
     * @param processedText The processed text after applying the operation.
     * @param rails The number of rails used in the Rail Fence Cipher.
     */
    public OperationHistory(String operationType, String originalText, String processedText, int rails) {
        this.operationType = operationType;
        this.originalText = originalText;
        this.processedText = processedText;
        this.rails = rails;
        this.timestamp = LocalDateTime.now();
    }

    /**
     * Gets the ID of the operation.
     *
     * @return The ID of the operation.
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the ID of the operation.
     *
     * @param id The ID of the operation.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets the type of operation.
     *
     * @return The operation type.
     */
    public String getOperationType() {
        return operationType;
    }

    /**
     * Sets the type of operation.
     *
     * @param operationType The operation type.
     */
    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }

    /**
     * Gets the original text of the operation.
     *
     * @return The original text.
     */
    public String getOriginalText() {
        return originalText;
    }

    /**
     * Sets the original text of the operation.
     *
     * @param originalText The original text.
     */
    public void setOriginalText(String originalText) {
        this.originalText = originalText;
    }

    /**
     * Gets the processed text resulting from the operation.
     *
     * @return The processed text.
     */
    public String getProcessedText() {
        return processedText;
    }

    /**
     * Sets the processed text resulting from the operation.
     *
     * @param processedText The processed text.
     */
    public void setProcessedText(String processedText) {
        this.processedText = processedText;
    }

    /**
     * Gets the number of rails used in the operation.
     *
     * @return The number of rails.
     */
    public int getRails() {
        return rails;
    }

    /**
     * Sets the number of rails used in the operation.
     *
     * @param rails The number of rails.
     */
    public void setRails(int rails) {
        this.rails = rails;
    }

    /**
     * Gets the timestamp of when the operation was performed.
     *
     * @return The timestamp of the operation.
     */
    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    /**
     * Sets the timestamp of when the operation was performed.
     *
     * @param timestamp The timestamp of the operation.
     */
    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * Returns a string representation of the OperationHistory object.
     *
     * @return A string representation of the OperationHistory object.
     */
    @Override
    public String toString() {
        return "OperationHistory{"
                + "id=" + id
                + ", operationType='" + operationType + '\''
                + ", originalText='" + originalText + '\''
                + ", processedText='" + processedText + '\''
                + ", rails=" + rails
                + ", timestamp=" + timestamp
                + '}';
    }
}
