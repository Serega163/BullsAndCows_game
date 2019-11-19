package app.servlets;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;

public class ListServlet extends HttpServlet {
    private int usersCount;
    private String[][] usersMass;
    boolean isFile = false;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        isFile = (new File("users.xml").exists());
        if (isFile) {
            try {
                userInfo();
                usersMass = new String[usersCount][3];
                for (int i = 0; i < userInfo().length; i++)
                    for (int j = 0; j < 3; j++) {
                        this.usersMass[i][j] = userInfo()[i][j];
                    }
            } catch (ParserConfigurationException | SAXException e) {
                e.printStackTrace();
            }

            for (int i = 0; i < usersMass.length; i++) {
                String a = usersMass[i][0];
                request.setAttribute("countGames" + i, usersMass[i][0]);
                request.setAttribute("login" + i, usersMass[i][1]);
                request.setAttribute("avgScore" + i, usersMass[i][2]);
            }
            request.setAttribute("usersCount", usersCount);
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("views/list.jsp");
            requestDispatcher.forward(request, response);
        } else {
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("views/errorRating.jsp");
            requestDispatcher.forward(request, response);
        }
    }


    private String[][] userInfo() throws ParserConfigurationException, IOException, SAXException {
        Document document = FileEdit.openFile();
        document.getDocumentElement().normalize();
        NodeList nList = document.getElementsByTagName("user");
        String login;
        String avgScore;
        String contGames;
        String[][] usersMass = new String[nList.getLength()][3];

        usersCount = nList.getLength();

        for (int i = 0; i < nList.getLength(); i++) {
            Node node = nList.item(i);
            Element eElement = (Element) node;
            login = eElement.getElementsByTagName("login").item(0).getTextContent();
            avgScore = eElement.getElementsByTagName("avgScore").item(0).getTextContent();
            contGames = eElement.getElementsByTagName("countGames").item(0).getTextContent();

            usersMass[i][0] = avgScore;
            usersMass[i][1] = login;
            usersMass[i][2] = contGames;

        }
        int n = 0;
        //сортировка пользователей по угадываниям
        Arrays.sort(usersMass, Comparator.comparingInt(avgScore2 -> Integer.parseInt(avgScore2[n])));
        return usersMass;
    }
}
