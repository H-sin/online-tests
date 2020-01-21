package GUI.etudiant;

import client.actionEmitters.EtudiantActionEmitter;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import models.Etudiant;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class App extends Application {

    private Stage stage;
    private Etudiant loggedEtudiant;
    private static App instance;
    private static EtudiantActionEmitter emitter;

    public App() {
        instance = this;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Socket socket = new Socket("localhost", 5000);
        ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
        emitter = new EtudiantActionEmitter(socket, inputStream, outputStream);
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                System.out.println("Closing...");
                if (emitter.getClientOnline()) {
                    emitter.exit();
                }
            }
        });

        stage = primaryStage;
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("Login.fxml"));
        Parent root = fxmlLoader.load();
        LoginController loginController = fxmlLoader.getController();
        loginController.setApp(getInstance());
        primaryStage.setTitle("Online Tests");
        Scene scene = new Scene(root, 240, 480);
        scene.getStylesheets().add(getClass().getResource("gui.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static App getInstance() {
        return instance;
    }

    public static EtudiantActionEmitter getEmitter() {
        return emitter;
    }

    public Etudiant getLoggedEtudiant() {
        return loggedEtudiant;
    }

    public void setLoggedEtudiant(Etudiant etudiant) {
        this.loggedEtudiant = etudiant;
    }

    public void gotoLogin() {
        try {
            replaceSceneContent("Login.fxml", 240, 480);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void gotoDashboard() {
        try {
            replaceSceneContent("Dashboard.fxml", 960, 672);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Parent replaceSceneContent(String fxml, int v, int v1) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource(fxml));
        Parent page = fxmlLoader.load();
        Scene scene = stage.getScene();

        if (scene == null) {
            scene = new Scene(page, v, v1);
            scene.getStylesheets().add(getClass().getResource("gui.css").toExternalForm());
            stage.setScene(scene);
        } else {
            stage.getScene().setRoot(page);
        }
        stage.setWidth(v);
        stage.setHeight(v1);
        stage.centerOnScreen();
        return page;
    }
}
