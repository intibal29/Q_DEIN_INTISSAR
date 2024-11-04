package com.intissar.q;

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

/**
 * La clase HelloController representa una aplicación de JavaFX que muestra un temporizador de cuenta regresiva
 * en formato gráfico. Incluye métodos para configurar el tiempo, iniciar el temporizador y actualizar la visualización.
 * Extiende la clase Application de JavaFX para iniciar y mostrar la interfaz gráfica.
 */
public class Temporizador extends Application {

    /** GridPane que contiene las etiquetas de minutos y segundos. */
    private GridPane gridPane;

    /** Propiedad que almacena el tiempo en segundos. */
    private IntegerProperty tiempo = new SimpleIntegerProperty();

    /** Propiedad que indica si el temporizador ha finalizado. */
    private BooleanProperty fin = new SimpleBooleanProperty(false);

    @FXML
    private Label minutosDecenas;
    @FXML
    private Label minutosUnidades;
    @FXML
    private Label segundosDecenas;
    @FXML
    private Label segundosUnidades;

    /** Timeline que controla el ciclo de la cuenta regresiva. */
    private Timeline timeline;

    /**
     * Constructor de la clase HelloController.
     * Inicializa el GridPane y las etiquetas de minutos y segundos con su formato y posición.
     * Configura el estilo visual de las etiquetas.
     */
    public Temporizador() {
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

    /**
     * Establece el tiempo inicial del temporizador en minutos y actualiza la visualización.
     * Convierte los minutos en segundos y limita el rango permitido entre 1 y 99 minutos.
     *
     * @param minutos Tiempo en minutos, entre 1 y 99.
     * @throws IllegalArgumentException si el tiempo está fuera del rango permitido.
     */
    public void setTiempo(int minutos) {
        if (minutos < 1 || minutos > 99) {
            throw new IllegalArgumentException("El tiempo debe estar entre 1 y 99 minutos.");
        }
        tiempo.set(minutos * 60);  // Convertimos los minutos a segundos
        actualizarEtiquetas();
    }

    /**
     * Inicia la cuenta regresiva y actualiza el tiempo cada segundo.
     * Cuando el tiempo llega a cero, detiene la cuenta y marca la propiedad fin como true.
     */
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

    /**
     * Actualiza las etiquetas de minutos y segundos en la interfaz gráfica
     * para reflejar el tiempo actual en el formato MM:SS.
     */
    private void actualizarEtiquetas() {
        int minutos = tiempo.get() / 60;
        int segundos = tiempo.get() % 60;

        minutosDecenas.setText(String.valueOf(minutos / 10));
        minutosUnidades.setText(String.valueOf(minutos % 10));
        segundosDecenas.setText(String.valueOf(segundos / 10));
        segundosUnidades.setText(String.valueOf(segundos % 10));
    }

    /**
     * Método principal de JavaFX para configurar y mostrar la escena del temporizador.
     * Establece un tiempo de ejemplo y comienza la cuenta regresiva.
     *
     * @param primaryStage El escenario principal para la aplicación JavaFX.
     */
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

    /**
     * Método principal que inicia la aplicación JavaFX.
     *
     * @param args Argumentos de línea de comandos.
     */
    /*public static void main(String[] args) {
        launch(args);
    }*/
}
