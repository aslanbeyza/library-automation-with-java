package org.library.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.library.helper.CommonFactory;
import org.library.model.Kullanici;

public class GirisEkrani {
	private VBox panel;
	private TextField kullaniciAdiField;
	private PasswordField parolaField;
	private Button girisButonu;
	private Stage primaryStage;

	public GirisEkrani(Stage primaryStage) {
		this.primaryStage = primaryStage;
		panel = new VBox(10);
		panel.setAlignment(Pos.CENTER);
		panel.setPadding(new Insets(20));
		panel.setStyle("-fx-background-color: #2f4f4f;");

		Label kullaniciAdiLabel = new Label("Kullanıcı Adı:");
		kullaniciAdiLabel.setTextFill(Color.WHITE);
		kullaniciAdiField = new TextField();

		Label parolaLabel = new Label("Parola:");
		parolaLabel.setTextFill(Color.WHITE);
		parolaField = new PasswordField();

		girisButonu = new Button("Giriş Yap");
		girisButonu.setOnAction(e -> girisYap());

		panel.getChildren().addAll(kullaniciAdiLabel, kullaniciAdiField, parolaLabel, parolaField, girisButonu);

		Scene scene = new Scene(panel, 400, 700);
		primaryStage.setScene(scene);
	}

	private void girisYap() {
		String kullaniciAdi = kullaniciAdiField.getText();
		String parola = parolaField.getText();

		Kullanici kullanici = CommonFactory.getInstance().kullaniciRepositoryOlustur().girisYap(kullaniciAdi, parola);
		CommonFactory.getInstance().setAktifKullanici(kullanici);
		if (kullanici != null) {
			if (kullanici.getTur() == 1 || kullanici.getTur() == 3) {
				OgrenciEkrani ogrenciEkrani = CommonFactory.getInstance().ogrenciEkraniOlustur();
				Scene ogrenciScene = new Scene(ogrenciEkrani.getPanel(), 1000, 1000);
				primaryStage.setScene(ogrenciScene);
			} else if (kullanici.getTur() == 2) {
				PersonelEkrani personelEkrani = CommonFactory.getInstance().personelEkraniOlustur();
				Scene personelScene = new Scene(personelEkrani.getPanel(), 1000, 1000);
				primaryStage.setScene(personelScene);
			}
		} else {
			System.out.println("yanlış parola");
		}
	}

	public VBox getPanel() {
		return panel;
	}
}
