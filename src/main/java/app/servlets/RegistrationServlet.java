package app.servlets;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.File;
import java.io.IOException;

public class RegistrationServlet extends HttpServlet {
    private boolean isExist = false;
    boolean isFile = false;


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        isFile = (new File("users.xml").exists());
        try {
            if (isFile) {
                if (!searchUser(request.getParameter("login"))) {
                    addUser(request.getParameter("login"), request.getParameter("password1"));
                    RequestDispatcher requestDispatcher = request.getRequestDispatcher("views/succesRegistration.jsp");
                    requestDispatcher.forward(request, response);
                } else {
                    RequestDispatcher requestDispatcher = request.getRequestDispatcher("views/errorRegistration.jsp");
                    requestDispatcher.forward(request, response);
                }
            } else {
                addUser(request.getParameter("login"), request.getParameter("password1"));
                RequestDispatcher requestDispatcher = request.getRequestDispatcher("views/succesRegistration.jsp");
                requestDispatcher.forward(request, response);
            }
        } catch (ParserConfigurationException | SAXException | TransformerException e) {
            e.printStackTrace();
        }

        RequestDispatcher requestDispatcher = request.getRequestDispatcher("views/registration.jsp");
        requestDispatcher.forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("views/registration.jsp");
        requestDispatcher.forward(request, response);
    }

    private boolean searchUser(String login) throws ParserConfigurationException, IOException, SAXException {
        Document document = FileEdit.openFile();
        document.getDocumentElement().normalize();
        NodeList nList = document.getElementsByTagName("user");
        String name;

        for (int i = 0; i < nList.getLength(); i++) {
            Node node = nList.item(i);
            Element eElement = (Element) node;
            name = eElement.getElementsByTagName("login").item(0).getTextContent();
            if (name.equals(login))
                isExist = true;
        }
        if (isExist)
            return true;
        else
            return false;
    }

    private void addUser(String getLogin, String getPassword) throws ParserConfigurationException, IOException, SAXException, TransformerException {
        if (isFile) {
            FileEdit.openFile();
        } else {
            FileEdit.createFile(getLogin, getPassword);
            FileEdit.openFile();
            return;
        }


        Document document = FileEdit.openFile();
        document.getDocumentElement().normalize();

        Element userList = document.getDocumentElement();

        Element newUser = document.createElement("user");
        Element login = document.createElement("login");
        Element password = document.createElement("password");
        Element avgScore = document.createElement("avgScore");
        Element countGames = document.createElement("countGames");

        Text loginText = document.createTextNode(getLogin);
        Text passwordText = document.createTextNode(getPassword);
        Text scoreText = document.createTextNode("0");
        Text countGamesText = document.createTextNode("0");


        newUser.appendChild(login);
        newUser.appendChild(password);
        newUser.appendChild(avgScore);
        newUser.appendChild(countGames);
        login.appendChild(loginText);
        password.appendChild(passwordText);
        avgScore.appendChild(scoreText);
        countGames.appendChild(countGamesText);
        userList.appendChild(newUser);

        FileEdit.saveFile(document);

    }
}