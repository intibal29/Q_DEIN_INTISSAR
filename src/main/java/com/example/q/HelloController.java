package com.example.q;

import javafx.application.Application;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;

public class HelloController extends Application {

    private GridPane gridPane;
    private IntegerProperty tiempo = new SimpleIntegerProperty();
    private BooleanProperty fin = new SimpleBooleanProperty(false);

    @FXML
    private Label minutosDecenas;
    @FXML
    private Label minutosUnidades;
    @FXML
    private Label segundosDecenas;
    @FXML
    private Label segundosUnidades;
    private Timeline timeline;

    public HelloController() {
        this.gridPane = new GridPane();
        this.gridPane.setAlignment(Pos.CENTER);
        this.gridPane.setHgap(5);

        // Inicializar etiquetas para las cifras del temporizador
        minutosDecenas = new Label("0");
        minutosUnidades = new Label("0");
        segundosDecenas = new Label("0");
        segundosUnidades = new Label("0");

        // Configurar estilo de las etiquetas para simular un temporizador
        minutosDecenas.setStyle("-fx-font-size: 40px;");
        minutosUnidades.setStyle("-fx-font-size: 40px;");
        segundosDecenas.setStyle("-fx-font-size: 40px;");
        segundosUnidades.setStyle("-fx-font-size: 40px;");

        // Añadir las etiquetas al GridPane en las posiciones correctas
        gridPane.add(minutosDecenas, 0, 0);
        gridPane.add(minutosUnidades, 1, 0);
        gridPane.add(new Label(":"), 2, 0);  // Separador de minutos y segundos
        gridPane.add(segundosDecenas, 3, 0);
        gridPane.add(segundosUnidades, 4, 0);

        // Configurar la propiedad fin para que esté inicialmente en false
        fin.set(false);
    }

    // Método para establecer el tiempo en minutos (1-99)
    public void setTiempo(int minutos) {
        if (minutos < 1 || minutos > 99) {
            throw new IllegalArgumentException("El tiempo debe estar entre 1 y 99 minutos.");
        }
        tiempo.set(minutos * 60);  // Convertimos los minutos a segundos
        actualizarEtiquetas();
    }

    // Método para iniciar la cuenta regresiva
    public void iniciarCuentaAtras() {
        timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            if (tiempo.get() > 0) {
                tiempo.set(tiempo.get() - 1);
                actualizarEtiquetas();
            } else {
                fin.set(true);
                timeline.stop();
            }
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    // Método para actualizar las etiquetas del temporizador
    private void actualizarEtiquetas() {
        int minutos = tiempo.get() / 60;
        int segundos = tiempo.get() % 60;

        minutosDecenas.setText(String.valueOf(minutos / 10));
        minutosUnidades.setText(String.valueOf(minutos % 10));
        segundosDecenas.setText(String.valueOf(segundos / 10));
        segundosUnidades.setText(String.valueOf(segundos % 10));
    }

    @Override
    public void start(Stage primaryStage) {
        // Configurar el escenario principal
        Scene scene = new Scene(gridPane, 200, 100);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Temporizador Gráfico");

        // Establecer tiempo inicial y comenzar cuenta atrás para pruebas
        setTiempo(1);  // 1 minuto de ejemplo
        iniciarCuentaAtras();

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
