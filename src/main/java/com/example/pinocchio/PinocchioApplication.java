package com.example.pinocchio;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.ArrayList;

public class PinocchioApplication extends Application {

    public static final Font DEFAULT_FONT = new Font(18);

    private static Chapter chapter;
    private static ArrayList<Chapter> loadedChapters;

    private static Label italianLabel;
    private static Label englishLabel;
    private static Label previousPageLabel;
    private static Label nextPageLabel;

    public void start(Stage stage) {

        chapter = new Chapter(1);
        loadedChapters = new ArrayList<>();
        loadedChapters.add(chapter);

        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setResizable(false);
        stage.setWidth(1400);
        stage.setHeight(800);

        VBox vBox = new VBox();
        vBox.setAlignment(Pos.TOP_CENTER);
        vBox.setBackground(new Background(new BackgroundFill(Color.valueOf("0xA98467"), CornerRadii.EMPTY, Insets.EMPTY)));



        BorderPane topBarBorderPane = new BorderPane();
        topBarBorderPane.setMinHeight(32);
        topBarBorderPane.setMaxHeight(32);
        topBarBorderPane.setBackground(new Background(new BackgroundFill(Color.valueOf("0xADC178"), CornerRadii.EMPTY, Insets.EMPTY)));
        topBarBorderPane.setOnMousePressed(pressedEvent -> topBarBorderPane.setOnMouseDragged(draggedEvent -> {
            stage.setX(draggedEvent.getScreenX() - pressedEvent.getSceneX());
            stage.setY(draggedEvent.getScreenY() - pressedEvent.getSceneY());
        }));

        Label label = new Label("Pinocchio");
        label.setMaxHeight(32);
        label.setFont(DEFAULT_FONT);
        label.setPadding(new Insets(0, 0, 0, 8));
        topBarBorderPane.setLeft(label);

        ImageView minimizeButtonImageView = new ImageView("minimize_icon.png");
        minimizeButtonImageView.setPreserveRatio(true);
        minimizeButtonImageView.setFitWidth(24);
        minimizeButtonImageView.setFitHeight(24);
        Button minimizeButton = createButton(minimizeButtonImageView, 24, 32, Background.EMPTY, event -> stage.setIconified(true));

        ImageView exitButtonImageView = new ImageView("exit_icon.png");
        exitButtonImageView.setPreserveRatio(true);
        exitButtonImageView.setFitWidth(24);
        exitButtonImageView.setFitHeight(24);
        Button exitButton = createButton(exitButtonImageView, 24, 32, Background.EMPTY, event -> System.exit(0));

        HBox buttonsHBox = new HBox();
        buttonsHBox.getChildren().addAll(minimizeButton, exitButton);
        buttonsHBox.setPadding(new Insets(0, 8, 0, 0));
        buttonsHBox.setSpacing(8);
        topBarBorderPane.setRight(buttonsHBox);

        vBox.getChildren().add(topBarBorderPane);



        Region topLine = new Region();
        topLine.setMinHeight(10);
        topLine.setBackground(new Background(new BackgroundFill(Color.valueOf("0xDDE5B6"), CornerRadii.EMPTY, Insets.EMPTY)));
        vBox.getChildren().add(topLine);



        HBox bookHBox = new HBox();

        Pane italianPane = new Pane();
        italianPane.setPrefHeight(700);
        italianPane.setPrefWidth(660);
        italianLabel = new Label(chapter.getCurrentPage().getItalianText());
        italianLabel.setPadding(new Insets(10, 10, 10, 10));
        italianLabel.setFont(DEFAULT_FONT);
        italianLabel.setPrefHeight(690);
        italianLabel.setPrefWidth(650);
        italianLabel.setAlignment(Pos.TOP_LEFT);
        italianPane.getChildren().add(italianLabel);

        Region middleLine = new Region();
        middleLine.setPrefHeight(700);
        middleLine.setPrefWidth(8);
        middleLine.setBackground(new Background(new BackgroundFill(Color.valueOf("0x6C584C"), CornerRadii.EMPTY, Insets.EMPTY)));

        Pane englishPane = new Pane();
        englishPane.setPrefHeight(700);
        englishPane.setPrefWidth(660);
        englishLabel = new Label(chapter.getCurrentPage().getEnglishText());
        englishLabel.setPadding(new Insets(10, 10, 10, 10));
        englishLabel.setFont(DEFAULT_FONT);
        englishLabel.setPrefHeight(690);
        englishLabel.setPrefWidth(650);
        englishLabel.setAlignment(Pos.TOP_LEFT);
        englishPane.getChildren().add(englishLabel);

        bookHBox.getChildren().addAll(italianPane, middleLine, englishPane);
        bookHBox.setBackground(new Background(new BackgroundFill(Color.valueOf("0xF0EAD2"), CornerRadii.EMPTY, Insets.EMPTY)));
        bookHBox.setBorder(new Border(new BorderStroke(Color.valueOf("0x6C584C"), BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(6))));

        BorderPane bookPane = new BorderPane();
        bookPane.setMaxWidth(1328);
        bookPane.setCenter(bookHBox);
        vBox.getChildren().add(bookPane);



        BorderPane bottomBorderPane = new BorderPane();
        bottomBorderPane.setPadding(new Insets(0, 8, 0, 8));

        HBox previousPageHbox = new HBox();
        previousPageHbox.setSpacing(4);
        previousPageHbox.getChildren().add(createButton(new ImageView("previous_page_icon.png"), 64, 32, Background.EMPTY, event -> updatePage(chapter.previousPage())));
        previousPageLabel = new Label("Previous page");
        previousPageLabel.setFont(DEFAULT_FONT);
        Insets pageLabelPadding = new Insets(2, 0, 0, 0);
        previousPageLabel.setPadding(pageLabelPadding);
        previousPageHbox.getChildren().add(previousPageLabel);
        bottomBorderPane.setLeft(previousPageHbox);

        HBox nextPageHbox = new HBox();
        nextPageHbox.setSpacing(4);
        nextPageLabel = new Label("Next page");
        nextPageLabel.setFont(DEFAULT_FONT);
        nextPageLabel.setPadding(pageLabelPadding);
        nextPageHbox.getChildren().add(nextPageLabel);
        nextPageHbox.getChildren().add(createButton(new ImageView("next_page_icon.png"), 64, 32, Background.EMPTY, event -> updatePage(chapter.nextPage())));
        bottomBorderPane.setRight(nextPageHbox);

        vBox.getChildren().add(bottomBorderPane);

        stage.setScene(new Scene(vBox));
        stage.show();

    }

    public static Button createButton(ImageView imageView, double width, double height, Background background, EventHandler<ActionEvent> eventHandler) {
        Button button = new Button();
        button.setGraphic(imageView);
        button.setMinSize(width, height);
        button.setMaxSize(width, height);
        button.setBackground(background);
        button.setOnAction(eventHandler);
        return button;
    }

    public static void updatePage(Page page) {
        if (page == null) return;
        if (chapter.isOnFirstPage()) {
            if (chapter.getChapterNumber() != 1) {
                previousPageLabel.setText("Chapter " + (chapter.getChapterNumber() - 1));
            }
        } else if (chapter.isOnLastPage()) {
            if (chapter.getChapterNumber() != 36) {
                nextPageLabel.setText("Chapter " + (chapter.getChapterNumber() + 1));
            }
        } else {
            if (previousPageLabel.getText().startsWith("C")) {
                previousPageLabel.setText("Previous page");
            }
            if (nextPageLabel.getText().startsWith("C")) {
                nextPageLabel.setText("Next page");
            }
        }
        italianLabel.setText(page.getItalianText());
        englishLabel.setText(page.getEnglishText());
    }

    public static void switchChapter(int chapterNumber) {
        boolean forward = chapter.getChapterNumber() < chapterNumber;
        if (chapterNumber > loadedChapters.size()) {
            chapter = new Chapter(chapterNumber);
            loadedChapters.add(chapter);
        } else {
            chapter = loadedChapters.get(chapterNumber - 1);
        }
        if (forward) {
            nextPageLabel.setText("Next page");
        } else {
            previousPageLabel.setText("Previous page");
        }
        updatePage(forward ? chapter.getFirstPage() : chapter.getLastPage());
    }

    public static void main(String[] args) {
        launch();
    }
}
