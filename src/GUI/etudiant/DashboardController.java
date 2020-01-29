package GUI.etudiant;

import GUI.Common;
import com.jfoenix.controls.JFXButton;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import models.Fiche;
import models.Test;

import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class DashboardController {

    @FXML
    private Label newTestCount;
    @FXML
    private Label nomEtudiant;
    @FXML
    private Label groupeEtudiant;
    @FXML
    private ScrollPane newTestsPane;
    @FXML
    private ScrollPane oldTestsPane;
    @FXML
    private JFXButton refreshButton;

    private TestController testController;

    public void initialize() {
        try {
            nomEtudiant.setText(App.getLoggedEtudiant().getPrenom() + " " + App.getLoggedEtudiant().getNom());
            groupeEtudiant.setText(App.getLoggedEtudiant().getNomGroupe());

            loadTests();

            Platform.runLater(() -> {
                Stage stage = (Stage) newTestsPane.getScene().getWindow();
                stage.setOnCloseRequest(e -> {
                    if (App.getLoggedEtudiant() != null &&
                            App.getActiveTest() != null &&
                            testController != null &&
                            !testController.showSaveAndExitDialog(true)) {
                        e.consume();
                    }
                });
            });

            Image refreshIcon = new Image(getClass().getResourceAsStream("icon-refresh.png"));
            ImageView refreshIconView = new ImageView(refreshIcon);
            refreshButton.setGraphic(refreshIconView);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadTests() throws Exception {
        ArrayList<Test> newTests = App.getEmitter().getNewEtudiantTests(App.getLoggedEtudiant().getCNE());
        ArrayList<Test> oldTests = App.getEmitter().getOldEtudiantTests(App.getLoggedEtudiant().getCNE());
        newTestCount.setText(Integer.toString(newTests.size()));

        VBox newTestsContent = new VBox();
        newTestsContent.setFillWidth(true);
        newTestsContent.setSpacing(8);
        newTestsContent.setPadding(new Insets(8));

        for (int i = newTests.size() - 1; i >= 0; i--) {
            Test test = newTests.get(i);
            newTestsContent.getChildren().add(createNewTestRow(test));
        }
        newTestsPane.setContent(newTestsContent);

        VBox oldTestsContent = new VBox();
        oldTestsContent.setFillWidth(true);
        oldTestsContent.setSpacing(8);
        oldTestsContent.setPadding(new Insets(8));

        for (int i = oldTests.size() - 1; i >= 0; i--) {
            Test test = oldTests.get(i);
            oldTestsContent.getChildren().add(createOldTestRow(test));
        }
        oldTestsPane.setContent(oldTestsContent);
    }

    public JFXButton createNewTestRow(Test test) {
        JFXButton row = new JFXButton();
        row.setButtonType(JFXButton.ButtonType.RAISED);
        row.setRipplerFill(Paint.valueOf("#046dd5"));
        row.setStyle("-fx-background-color: #fff; -fx-margin-right:10px");
        row.setPrefHeight(56);
        row.setMinWidth(304);
        row.setPrefWidth(640);
        VBox vBox = new VBox();
        Label subtitleLabel = new Label(test.getDetails());
        Label titleLabel = new Label(test.getTitre());
        Label bylineLabel = new Label("Par : " + test.getNomProf());
        titleLabel.setStyle("-fx-font-weight: bold");
        subtitleLabel.setStyle("-fx-font-size: 12");
        bylineLabel.setStyle("-fx-font-size: 12");
        vBox.getChildren().addAll(titleLabel, subtitleLabel, bylineLabel);
        row.setGraphic(vBox);
        row.setOnAction(e -> {
            openTest(test.getId());
        });
        return row;
    }

    public JFXButton createOldTestRow(Test test) throws Exception {
        JFXButton row = new JFXButton();
        row.setButtonType(JFXButton.ButtonType.RAISED);
        row.setRipplerFill(Paint.valueOf("#046dd5"));
        row.setStyle("-fx-background-color: #ccc");
        row.setPrefHeight(56);
        row.setMinWidth(304);
        row.setPrefWidth(640);
        //row.setDisable(true);
        VBox vBox = new VBox();
        Label subtitleLabel = new Label(test.getDetails());
        Label titleLabel = new Label(test.getTitre());
        Label bylineLabel = new Label("Par : " + test.getNomProf());
        titleLabel.setStyle("-fx-font-weight: bold");
        subtitleLabel.setStyle("-fx-font-size: 12");
        bylineLabel.setStyle("-fx-font-size: 12");
        vBox.getChildren().addAll(titleLabel, subtitleLabel, bylineLabel);
        Fiche fiche = App.getEmitter().getFicheEtudiant(App.getLoggedEtudiant().getCNE(), test.getId());
        String note = (new DecimalFormat("#.##")).format(fiche.getNote());
        Label noteLabel = new Label(note);
        noteLabel.setTextFill(Paint.valueOf("#f00"));
        Label baremeLabel = new Label("20");
        baremeLabel.setTextFill(Paint.valueOf("#f00"));
        baremeLabel.setStyle("-fx-border-color: #f00; -fx-border-width: 1px 0 0 0");
        VBox noteBox = new VBox(noteLabel, baremeLabel);
        noteBox.setAlignment(Pos.BASELINE_CENTER);
        noteBox.setStyle("-fx-border-color: #f00; -fx-border-radius: 100%");
        noteBox.setPrefWidth(44);
//        noteBox.setPrefSize(32,32);
        row.setGraphic(new HBox(vBox, noteBox));
        HBox.setHgrow(vBox, Priority.ALWAYS);
        HBox.setHgrow(noteBox, Priority.NEVER);
        return row;
    }

    public void handleLogout() {
        App.setLoggedEtudiant(null);
        App.gotoLogin();
    }

    public void openTest(int idTest) {
        try {
            App.setActiveTest(App.getEmitter().getFullTestById(idTest));
            Stage testStage = new Stage();
            testStage.initOwner(App.getStage());
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("Test.fxml"));
            Parent root;
            root = fxmlLoader.load();
            testController = fxmlLoader.getController();
            Scene scene = new Scene(root, 1024, 720);
            scene.getStylesheets().add(getClass().getResource("/GUI/gui.css").toExternalForm());
            testStage.setScene(scene);
            testStage.setTitle("Online Tests");
            testStage.setResizable(false);
            testStage.show();
        } catch (Exception e) {
            Common.showErrorAlert(e.getMessage());
            e.printStackTrace();
        }
    }

    public boolean closeTest() {
        return testController.showSaveAndExitDialog(true);
    }


}
