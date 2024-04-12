<%-- 
    Document   : history
    Created on : 11 gru 2023, 01:32:32
    Author     : Magdalena Koncowicz
--%>

<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="model.OperationHistory" %>  <!-- Update with the correct package name -->

<!DOCTYPE html>
<html>
    <head>
        <!-- Page Metadata -->
        <title>Operation History</title>
    </head>
    <body>
        <!-- Page heading -->
        <h1>History of Operations</h1>
        <!-- 
            History Table Display:
            This section is responsible for displaying the history of operations 
            stored in the database. It checks if the 'history' attribute is present 
            and not empty, and then iterates over the list of OperationHistory objects
            to display each operation's details in a table format. 
            If the history is empty, it displays a message to the user.
        -->
        <% 
        List<OperationHistory> history = (List<OperationHistory>) request.getAttribute("history");
        String historyMessage = (String) request.getAttribute("historyMessage");
        if (history != null && !history.isEmpty()) {
        %>
        <table border='1'>
            <tr>
                <th>Operation Type</th>
                <th>Original Text</th>
                <th>Processed Text</th>
                <th>Rails</th>
                <th>Timestamp</th>
            </tr>

            <% for (OperationHistory operation : history) { %>
            <tr>
                <td><%= operation.getOperationType() %></td>
                <td><%= operation.getOriginalText() %></td>
                <td><%= operation.getProcessedText() %></td>
                <td><%= operation.getRails() %></td>
                <td><%= operation.getTimestamp() %></td>
            </tr>
            <% } %>
        </table>
        <%
        }
        else {
            out.print(historyMessage != null ? historyMessage : "No operation history available.");
        }
        %>
        <!-- Link to go back to the main page -->
        <a href="index.html">Back</a>
    </body>
</html>