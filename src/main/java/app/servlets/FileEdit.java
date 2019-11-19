package app.servlets;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;

class FileEdit {
    static Document openFile() throws ParserConfigurationException, IOException, SAXException {
        File usersFile = new File("users.xml");
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(usersFile);
        return document;
    }

    static void createFile(String getLogin, String getPassword) throws ParserConfigurationException, IOException, SAXException, TransformerException {

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.newDocument();

        Element rootUsers = document.createElement("users");
        Element newUser = document.createElement("user");
        Element login = document.createElement("login");
        Element password = document.createElement("password");
        Element avgScore = document.createElement("avgScore");
        Element countGames = document.createElement("countGames");

        Text loginText = document.createTextNode(getLogin);
        Text passwordText = document.createTextNode(getPassword);
        Text scoreText = document.createTextNode("0");
        Text countGamesText = document.createTextNode("0");


        document.appendChild(rootUsers);
        rootUsers.appendChild(newUser);
        newUser.appendChild(login);
        newUser.appendChild(password);
        newUser.appendChild(avgScore);
        newUser.appendChild(countGames);
        login.appendChild(loginText);
        password.appendChild(passwordText);
        avgScore.appendChild(scoreText);
        countGames.appendChild(countGamesText);

        saveFile(document);
    }


    static void saveFile(Document document) throws TransformerException {
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        StreamResult result = new StreamResult(new File("users.xml"));
        DOMSource source = new DOMSource(document);
        transformer.transform(source, result);
    }
}
