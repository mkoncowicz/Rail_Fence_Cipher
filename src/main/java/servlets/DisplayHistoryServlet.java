package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import java.util.List;
import jakarta.servlet.http.Cookie;
import model.RailFenceModel;
import model.OperationHistory;

/**
 * Servlet responsible for handling requests to display the history of
 * operations in the Rail Fence Cipher application. It primarily processes GET
 * requests, retrieving the operation history from the model and forwarding it
 * to the history.jsp page for display. Additionally, this servlet handles
 * incrementing the visit count for the history page using cookies.
 *
 * @author Magdalena Koncowicz
 * @version 1.0
 */
@WebServlet(name = "DisplayHistoryServlet", urlPatterns = {"/DisplayHistoryServlet"})
public class DisplayHistoryServlet extends HttpServlet {

    /**
     * The model object for the Rail Fence Cipher application. 
     */
    private final RailFenceModel model = new RailFenceModel();

    /**
     * Handles the HTTP GET request. Retrieves and displays the history of
     * operations.
     *
     * @param request HttpServletRequest object containing the client's request.
     * @param response HttpServletResponse object for sending the response.
     * @throws ServletException if a servlet-specific error occurs.
     * @throws IOException if an I/O error occurs.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        incrementHistoryPageVisitCount(request, response);

        List<OperationHistory> history = model.getOperationHistory();

        if (history == null || history.isEmpty()) {
            request.setAttribute("historyMessage", "No history found.");
        }

        request.setAttribute("history", history);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/history.jsp");
        dispatcher.forward(request, response);
    }

    /**
     * Increments the count of visits to the history page, stored in a cookie.
     *
     * @param request HttpServletRequest object containing the client's request.
     * @param response HttpServletResponse object for sending the response.
     */
    private void incrementHistoryPageVisitCount(HttpServletRequest request, HttpServletResponse response) {
        Cookie visitCookie = getCookie(request, "visitCount");
        int visitCount = visitCookie != null ? Integer.parseInt(visitCookie.getValue()) : 0;
        visitCount++;
        response.addCookie(new Cookie("visitCount", String.valueOf(visitCount)));
    }

    /**
     * Retrieves a cookie by its name from the request.
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
