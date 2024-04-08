package org.library;

import javafx.application.Application;
import javafx.stage.Stage;
import org.library.view.GirisEkrani;

public class Main extends Application {

	@Override
	public void start(Stage primaryStage) {
		new GirisEkrani(primaryStage);

		primaryStage.setTitle("Kütüphane Yönetim Sistemi");
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}