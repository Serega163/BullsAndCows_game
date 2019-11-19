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
import javax.xml.transform.*;
import java.io.IOException;
import java.util.Random;

@WebServlet(name = "GameServlet")
public class GameServlet extends HttpServlet {
    private String numberGenerated = createNumber();
    private int tryCount = 0;
    private String allTry = "";
    private boolean isWin = false;
    private String currentUser = "";
    private int tryCountOld = 0;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if ((currentUser == "")|| allTry =="") {
            currentUser = request.getParameter("login");
        }
        System.out.println(currentUser);
        if (request.getParameter("newGame") != null) {//Если нажата кнопка "Новая ига" - убрать предыдущие попытки
            isWin = false;
            allTry = "";
            numberGenerated = createNumber();
            tryCount = 0;
            request.setAttribute("allTry", allTry);
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("views/game.jsp");
            requestDispatcher.forward(request, response);
        } else {
            String numberOffer = request.getParameter("numberOffer");

            allTry = compare(numberOffer, numberGenerated) + "\n" + allTry;
            request.setAttribute("allTry", allTry);
            request.setAttribute("isWin", this.isWin);
            if (isWin) {
                try {
                    addScore(tryCountOld, currentUser);
                } catch (ParserConfigurationException | SAXException | TransformerException e) {
                    e.printStackTrace();
                }
            }
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("views/game.jsp");
            requestDispatcher.forward(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String number = request.getParameter("numeric");
        request.setAttribute("number", number);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("views/game.jsp");
        requestDispatcher.forward(request, response);
    }

    private String createNumber() {
        Random random = new Random();
        String numberString = Integer.toString(random.nextInt(9) + 1);//Первая цифра не 0
        for (int i = 0; i < 3; ) {
            int randomNum = random.nextInt(10);
            //Проверка, что такой цифры уже нет в наборе
            if (numberString.contains(Integer.toString(randomNum))) {
                continue;
            }
            numberString += randomNum;
            i++;
        }
        System.out.println(numberString);
        return numberString;
    }

    private String compare(String numberOffer, String numberGenerated) {
        int b = 0;
        int k = 0;
        tryCount++;
        for (int i = 0; i < 4; i++) {
            if (numberOffer.charAt(i) == numberGenerated.charAt(i)) {
                b++;
                if (b == 4) {
                    this.numberGenerated = createNumber();
                    tryCountOld = tryCount;
                    tryCount = 0;
                    isWin = true;
                    return "Поздравляю! Ты угадал число с " + tryCountOld + " попыток(ки)";

                }
            } else if (numberOffer.contains(Character.toString(numberGenerated.charAt(i)))) {
                k++;
            }
        }
        return numberOffer + ": " + b + "Б " + k + "К";

    }

    private void addScore(int tryCount, String currentUser) throws ParserConfigurationException, IOException, SAXException, TransformerException {
        String log;
        Document document = FileEdit.openFile();
        document.getDocumentElement().normalize();
        NodeList nList = document.getElementsByTagName("user");
        int countGames = 0;
        int avgScore = 0;
        for (int i = 0; i < nList.getLength(); i++) {
            Node node = nList.item(i);
            Element eElement = (Element) node;
            log = eElement.getElementsByTagName("login").item(0).getTextContent();
            if (log.equals(currentUser)) {
                // получение текущего счета
                avgScore = Integer.parseInt(eElement.getElementsByTagName("avgScore").item(0).getTextContent());
                countGames = Integer.parseInt(eElement.getElementsByTagName("countGames").item(0).getTextContent());
                float newScore = (float)(avgScore * countGames + tryCount) / (countGames + 1);
                int avg = Math.round(newScore);
                int totalGames = countGames + 1;
                eElement.getElementsByTagName("avgScore").item(0).setTextContent(Integer.toString(avg));// запись среднего числа угадываний
                eElement.getElementsByTagName("countGames").item(0).setTextContent(Integer.toString(totalGames));

                FileEdit.saveFile(document);
            }
        }

    }
}
