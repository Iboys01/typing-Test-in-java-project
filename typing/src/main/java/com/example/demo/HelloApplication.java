package com.example.demo;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.paint.Color;


public class HelloApplication extends Application {
    private String sampleText = "The quick brown fox jumps over the lazy dog.";
    private long startTime;
    private boolean testStarted = false;
    private AnimationTimer timer;
    private Label timerLabel;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Typing Test App");

        Label instructionLabel = new Label("Type the following text below:");
        instructionLabel.setFont(new Font("Arial", 18));
        instructionLabel.setTextFill(Color.DARKBLUE);

        // Sample text area (read-only)
        TextArea sampleTextArea = new TextArea(sampleText);
        sampleTextArea.setFont(new Font("Verdana", 16));
        sampleTextArea.setWrapText(true);
        sampleTextArea.setEditable(false);
        sampleTextArea.setStyle("-fx-control-inner-background: #f0f8ff; -fx-font-size: 16px;");

        // User input area
        TextArea userInputArea = new TextArea();
        userInputArea.setFont(new Font("Verdana", 16));
        userInputArea.setWrapText(true);
        userInputArea.setDisable(true); // Initially disabled until 'Start Test' is clicked
        userInputArea.setStyle("-fx-border-color: #4682b4; -fx-border-radius: 5px;");

        // Timer label
        timerLabel = new Label("Time: 0.0 seconds");
        timerLabel.setFont(new Font("Arial", 16));
        timerLabel.setTextFill(Color.DARKRED);

        // Buttons
        Button startButton = new Button("Start Test");
        startButton.setStyle("-fx-background-color: #6a5acd; -fx-text-fill: white; -fx-font-size: 14px; -fx-cursor: hand;");

        Button submitButton = new Button("Submit");
        submitButton.setStyle("-fx-background-color: #32cd32; -fx-text-fill: white; -fx-font-size: 14px; -fx-cursor: hand;");
        submitButton.setDisable(true); // Initially disabled

        Label resultLabel = new Label();
        resultLabel.setFont(new Font("Arial", 16));
        resultLabel.setTextFill(Color.DARKRED);

        startButton.setOnAction(e -> {
            userInputArea.clear();
            resultLabel.setText("");
            timerLabel.setText("Time: 0.0 seconds");
            userInputArea.setDisable(false);
            userInputArea.requestFocus();
            startTime = System.currentTimeMillis();
            testStarted = true;
            startTimer();
            submitButton.setDisable(false);
        });

        submitButton.setOnAction(e -> {
            if (testStarted) {
                stopTimer();
                long endTime = System.currentTimeMillis();
                String userText = userInputArea.getText().trim();

                if (userText.equals(sampleText)) {
                    long timeTaken = (endTime - startTime) / 1000;
                    int words = sampleText.split(" ").length;
                    double wpm = (words / (timeTaken / 60.0));
                    resultLabel.setText("Well done! Time: " + timeTaken + " seconds, Speed: " + String.format("%.2f", wpm) + " WPM");
                } else {
                    resultLabel.setText("Text doesn't match! Try again.");
                }

                userInputArea.setDisable(true);
                testStarted = false;
                submitButton.setDisable(true);
            }
        });

        VBox layout = new VBox(15);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(instructionLabel, sampleTextArea, userInputArea, timerLabel, startButton, submitButton, resultLabel);

        Scene scene = new Scene(layout, 700, 500);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void startTimer() {
        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                long currentTime = System.currentTimeMillis();
                double elapsedTime = (currentTime - startTime) / 1000.0;
                timerLabel.setText(String.format("Time: %.1f seconds", elapsedTime));
            }
        };
        timer.start();
    }

    private void stopTimer() {
        if (timer != null) {
            timer.stop();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
