package servlets;

import model.RailFenceModel;
import model.InvalidRailNumberException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.Cookie;
import java.io.IOException;

/**
 * Servlet for handling encryption requests in the Rail Fence Cipher
 * application. This servlet processes POST requests for encryption and displays
 * the form on GET requests.
 *
 * @author Magdalena Koncowicz
 * @version 1.0
 */
@WebServlet(name = "EncryptServlet", urlPatterns = {"/EncryptServlet"})
public class EncryptServlet extends HttpServlet {

    /**
     * The model for the Rail Fence Cipher. This field is an instance of the
     * {@link RailFenceModel} class, which contains the logic for encrypting and
     * decrypting text using the Rail Fence algorithm.
     */
    private final RailFenceModel model = new RailFenceModel();

    /**
     * Handles the HTTP GET request. Forwards to the encryption form.
     *
     * @param request HttpServletRequest object containing the client's request
     * @param response HttpServletResponse object for sending the response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/index.html");
        dispatcher.forward(request, response);
    }

    /**
     * Handles the HTTP POST request. Processes the encryption request and
     * forwards to the result page.
     *
     * @param request HttpServletRequest object containing the client's request
     * @param response HttpServletResponse object for sending the response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String inputText = request.getParameter("inputText");
        int rails;
        try {
            rails = Integer.parseInt(request.getParameter("rails"));
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid number format for rails.");
            return;
        }

        if (!isValidInput(inputText) || rails < 2) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid input data.");
            return;
        }

        String encryptedText;
        try {
            encryptedText = model.encrypt(inputText, rails);
            model.createOperationHistory("Encrypt", inputText, encryptedText, rails);
        } catch (InvalidRailNumberException e) {
            incrementErrorCount(request, response);
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
            return;
        }

        request.setAttribute("encryptedText", encryptedText);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/encryptionResult.jsp");
        dispatcher.forward(request, response);
    }

    /**
     * Validates the input text to ensure it contains only English alphabet
     * characters and spaces.
     *
     * @param inputText The text to be validated.
     * @return true if the input text is valid, false otherwise.
     */
    private boolean isValidInput(String inputText) {
        return inputText != null && inputText.matches("[a-zA-Z ]+");
    }

    /**
     * Increments the error count stored in a cookie.
     *
     * @param request HttpServletRequest object containing the client's request.
     * @param response HttpServletResponse object for sending the response.
     */
    private void incrementErrorCount(HttpServletRequest request, HttpServletResponse response) {
        Cookie errorCookie = getCookie(request, "errorCount");
        int errorCount = errorCookie != null ? Integer.parseInt(errorCookie.getValue()) : 0;
        errorCount++;
        response.addCookie(new Cookie("errorCount", String.valueOf(errorCount)));
    }

    /**
     * Retrieves a cookie by its name.
     *
     * @param request HttpServletRequest object containing the client's request.
     * @param name The name of the cookie to retrieve.
     * @return The Cookie object if found, null otherwise.
     */
    private Cookie getCookie(HttpServletRequest request, String name) {
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if (cookie.getName().equals(name)) {
                    return cookie;
                }
            }
        }
        return null;
    }
}
