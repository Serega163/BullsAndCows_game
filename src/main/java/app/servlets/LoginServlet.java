package app.servlets;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

@WebServlet(name = "LoginServlet")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            if (new File("users.xml").exists()) {
                if (isExist(request.getParameter("login"), request.getParameter("password"))) {
                    String login = request.getParameter("login");
                    request.setAttribute("login", login);

                    RequestDispatcher requestDispatcher = request.getRequestDispatcher("views/game.jsp");
                    requestDispatcher.forward(request, response);
                } else {
                    RequestDispatcher requestDispatcher = request.getRequestDispatcher("views/errorLogin.jsp");
                    requestDispatcher.forward(request, response);
                }
            }
            else {
                RequestDispatcher requestDispatcher = request.getRequestDispatcher("views/registration.jsp");
                requestDispatcher.forward(request, response);
            }
        } catch (ParserConfigurationException | SAXException e) {
            e.printStackTrace();
        }
    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) {

    }

    private boolean isExist(String login, String password) throws ParserConfigurationException, IOException, SAXException {
        Document document = FileEdit.openFile();
        document.getDocumentElement().normalize();
        NodeList nList = document.getElementsByTagName("user");
        String log;
        String pass;
        boolean loginIsSame = false;
        boolean passwordIsSame = false;

        for (int i = 0; i < nList.getLength(); i++) {
            Node node = nList.item(i);
            Element eElement = (Element) node;
            log = eElement.getElementsByTagName("login").item(0).getTextContent();
            if (log.equals(login))
                loginIsSame = true;
            else
                loginIsSame = false;
            pass = eElement.getElementsByTagName("password").item(0).getTextContent();
            if (pass.equals(password))
                passwordIsSame = true;
            else
                passwordIsSame = false;
            if (loginIsSame && passwordIsSame)
                break;
        }
        if (loginIsSame && passwordIsSame)
            return true;
        else
            return false;
    }

}
