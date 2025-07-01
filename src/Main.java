// File: Main.java

import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.nio.file.Files;
import java.util.List;

public class Main extends Application {
    private TextArea summaryArea = new TextArea();
    private Label fileNameLabel = new Label("No file selected");
    private ToggleButton themeToggle = new ToggleButton("🌙 Dark Mode");
    private VBox container;
    private Scene scene;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("AI Document Summarizer");

        Button openButton = new Button("📂 Choose File");
        styleButton(openButton);
        styleToggle(themeToggle);

        fileNameLabel.setFont(Font.font("Segoe UI", 14));
        fileNameLabel.setTextFill(Color.web("#555"));

        openButton.setOnAction(e -> chooseFile(primaryStage));
        themeToggle.setOnAction(e -> toggleTheme());

        summaryArea.setWrapText(true);
        summaryArea.setEditable(false);
        summaryArea.setFont(Font.font("Consolas", 15));
        summaryArea.setStyle("-fx-background-color: transparent;");
        summaryArea.setPrefHeight(400);
        summaryArea.setPrefWidth(600);
        summaryArea.setMaxHeight(Double.MAX_VALUE);
        summaryArea.setMaxWidth(Double.MAX_VALUE);
        VBox.setVgrow(summaryArea, Priority.ALWAYS);

        VBox sidebar = new VBox(10, openButton, themeToggle);
        sidebar.setPadding(new Insets(10));
        sidebar.setPrefWidth(200);

        VBox contentArea = new VBox(10, fileNameLabel, summaryArea);
        contentArea.setPadding(new Insets(20));
        contentArea.setPrefWidth(600);
        contentArea.setPrefHeight(500);

        HBox layout = new HBox(sidebar, contentArea);
        layout.setSpacing(10);

        container = new VBox(layout);
        container.setPadding(new Insets(10));

        scene = new Scene(container, 900, 600);
        applyLightTheme();

        addEntranceAnimation(container);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void chooseFile(Stage stage) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select a Text File");
        fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("Text Files", "*.txt")
        );
        File file = fileChooser.showOpenDialog(stage);

        if (file != null) {
            fileNameLabel.setText("Selected File: " + file.getName());
            summaryArea.clear();

            try {
                List<String> lines = Files.readAllLines(file.toPath());
                String content = String.join("\n", lines);

                List<String> summary = Summarizer.improvedSummarize(content, 5);
                if (summary.isEmpty()) {
                    summaryArea.setText("⚠️ No meaningful content found to summarize.");
                } else {
                    summaryArea.setText(String.join("\n\n", summary));
                }
            } catch (Exception ex) {
                summaryArea.setText("❌ Error reading file: " + ex.getMessage());
            }
        }
    }

    private void styleButton(Button button) {
        button.setFont(Font.font("Segoe UI Semibold", 14));
        button.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white; -fx-background-radius: 8;");
        button.setEffect(new DropShadow());
    }

    private void styleToggle(ToggleButton toggle) {
        toggle.setFont(Font.font("Segoe UI", 13));
        toggle.setStyle("-fx-background-radius: 8;");
        toggle.setMaxWidth(Double.MAX_VALUE);
    }

    private void toggleTheme() {
        if (themeToggle.isSelected()) {
            themeToggle.setText("☀️ Light Mode");
            applyDarkTheme();
        } else {
            themeToggle.setText("🌙 Dark Mode");
            applyLightTheme();
        }
    }

    private void applyLightTheme() {
        container.setStyle("-fx-background-color: #f5f5f5;");
        summaryArea.setStyle("-fx-control-inner-background: white; -fx-text-fill: #333;");
        fileNameLabel.setTextFill(Color.web("#333"));
    }

    private void applyDarkTheme() {
        container.setStyle("-fx-background-color: #1e1e1e;");
        summaryArea.setStyle("-fx-control-inner-background: #2d2d2d; -fx-text-fill: #e0e0e0;");
        fileNameLabel.setTextFill(Color.web("#bbb"));
    }

    private void addEntranceAnimation(VBox box) {
        FadeTransition fade = new FadeTransition(Duration.seconds(1.2), box);
        fade.setFromValue(0);
        fade.setToValue(1);
        fade.play();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
