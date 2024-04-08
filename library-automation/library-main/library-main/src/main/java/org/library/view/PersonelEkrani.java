package org.library.view;

// İçe aktarmalar (imports)
// ...

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.library.helper.CommonFactory;
import org.library.model.*;
import org.library.observer.KitapListesiGuncelleyici;
import org.library.state.KitapKayip;
import org.library.state.KitapOdunc;
import org.library.state.KitapRafta;

import java.util.List;
import java.util.Objects;

public class PersonelEkrani {
	private VBox panel;
	private TableView<Kitap> kutuphaneKitapListesi;
	private TableView<Kitap> kullaniciKitapListesi;
	private TableView<Kullanici> kullaniciListesi;
	private TableView<Kullanici> kullaniciListesiMini;
	private ComboBox<String> aramaKriteriComboBox;
	private TextField aramaKutusu;
	private ComboBox<String> kullaniciTipiComboBox;
	private VBox personelPaneli, ogrenciPaneli, ogretimUyesiPaneli;
	private TextField ogrenciKayitYiliTextField;
	private TextField ogrenciBolumTextField;
	private TextField personelPozisyonTextField;
	private TextField ogretimUyesiTextField;
	private CommonFactory commonFactory = CommonFactory.getInstance();
	private KitapYonetimi kitapYonetimi;

	public PersonelEkrani() {
		initUI();
	}

	private void initUI() {
		TabPane tabPane = new TabPane();
		Tab kitapTab = new Tab("Kitap İşlemleri", kitapPaneliOlustur());
		kitapTab.setClosable(false);
		Tab kullaniciTab = new Tab("Kullanıcı İşlemleri", kullaniciPaneliOlustur());
		kullaniciTab.setClosable(false);

		tabPane.getTabs().addAll(kitapTab, kullaniciTab);

		panel = new VBox(tabPane);
	}

	private Pane kitapPaneliOlustur() {
		VBox layout = new VBox(10);
		kutuphaneKitapListesi = new TableView<>();
		kutuphaneKitapListesi.setEditable(true);
		kutuphaneKitapListesi.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

		KitapListesiGuncelleyici kitapListesiGuncelleyici = new KitapListesiGuncelleyici(kutuphaneKitapListesi);
		kitapYonetimi = new KitapYonetimi();
		kitapYonetimi.addObserver(kitapListesiGuncelleyici);

		TableColumn<Kitap, Integer> kitapIDKolon = new TableColumn<>("ID");
		kitapIDKolon.setCellValueFactory(new PropertyValueFactory<>("kitapID"));

		TableColumn<Kitap, String> baslikKolon = new TableColumn<>("Başlık");
		baslikKolon.setCellValueFactory(new PropertyValueFactory<>("baslik"));

		TableColumn<Kitap, String> yazarKolon = new TableColumn<>("Yazar");
		yazarKolon.setCellValueFactory(new PropertyValueFactory<>("yazar"));

		TableColumn<Kitap, String> turKolon = new TableColumn<>("Tür");
		turKolon.setCellValueFactory(new PropertyValueFactory<>("tur"));

		TableColumn<Kitap, String> konuKolon = new TableColumn<>("Konu");
		konuKolon.setCellValueFactory(new PropertyValueFactory<>("konu"));

		TableColumn<Kitap, Boolean> oduncKolon = new TableColumn<>("OduncVerildi");
		oduncKolon.setCellValueFactory(new PropertyValueFactory<>("oduncVerildi"));

		TableColumn<Kitap, Double> puanKolon = new TableColumn<>("Puan");
		puanKolon.setCellValueFactory(new PropertyValueFactory<>("puan"));

		TableColumn<Kitap, String> durumKolon = new TableColumn<>("Durum");
		durumKolon.setCellValueFactory(new PropertyValueFactory<>("durum"));

		TableColumn<Kitap, String> islemKolon = new TableColumn<>("İşlem");
		islemKolon.setCellFactory(col -> new TableCell<Kitap, String>() {
			private final Button actionButton = new Button();

			@Override
			protected void updateItem(String item, boolean empty) {
				super.updateItem(item, empty);

				if (empty) {
					setGraphic(null);
					return;
				}

				Kitap kitap = getTableView().getItems().get(getIndex());
				if (kitap.getDurum().equals("Rafta")) {
					actionButton.setText("Kitap Ver");
					actionButton.setOnAction(e -> kitapVerModalGoster(kitap));
				} else if (kitap.getDurum().equals("Kayıp")) {
					actionButton.setText("Sil");
					actionButton.setOnAction(e -> kitapSil(kitap));
				} else {
					setGraphic(null);
					return;
				}

				setGraphic(actionButton);
			}
		});

		kutuphaneKitapListesi.getColumns().addAll(kitapIDKolon, baslikKolon, yazarKolon, turKolon, konuKolon, oduncKolon, puanKolon, durumKolon, islemKolon);

		kullaniciKitapListesiOlustur();
		kutuphaneKitapListele();
		Button ekleButton = new Button("Kitap Ekle");
		ekleButton.setOnAction(e -> kitapEkleModalGoster());
		Button guncelleButton = new Button("Kitap Güncelle");
		guncelleButton.setOnAction(e -> {
			kitapGuncelleModalGoster(kutuphaneKitapListesi.getSelectionModel().getSelectedItem());
		});
		Button silButton = new Button("Kitap Sil");
		silButton.setOnAction(e -> {
			kitapSil(kutuphaneKitapListesi.getSelectionModel().getSelectedItem());
		});

		aramaKriteriComboBox = new ComboBox<>();
		aramaKriteriComboBox.setItems(FXCollections.observableArrayList("Yazar", "Başlık", "Tür", "Konu", "Puan"));
		aramaKriteriComboBox.setValue("Yazar");

		aramaKutusu = new TextField();
		aramaKutusu.setPromptText("Aranacak kelimeyi giriniz.");
		aramaKutusu.setOnKeyReleased(e -> kitaplariAra());

		HBox butonBar = new HBox(10, ekleButton, guncelleButton, silButton, aramaKriteriComboBox, aramaKutusu);
		butonBar.setAlignment(Pos.CENTER);

		layout.getChildren().addAll(kutuphaneKitapListesi, butonBar);
		return layout;
	}

	private void kullaniciKitapListesiOlustur() {
		kullaniciKitapListesi = new TableView<>();
		kullaniciKitapListesi.setEditable(true);
		kullaniciKitapListesi.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);


		TableColumn<Kitap, Integer> kitapIDKolon = new TableColumn<>("ID");
		kitapIDKolon.setCellValueFactory(new PropertyValueFactory<>("kitapID"));

		TableColumn<Kitap, String> baslikKolon = new TableColumn<>("Başlık");
		baslikKolon.setCellValueFactory(new PropertyValueFactory<>("baslik"));

		TableColumn<Kitap, String> yazarKolon = new TableColumn<>("Yazar");
		yazarKolon.setCellValueFactory(new PropertyValueFactory<>("yazar"));

		TableColumn<Kitap, String> turKolon = new TableColumn<>("Tür");
		turKolon.setCellValueFactory(new PropertyValueFactory<>("tur"));

		TableColumn<Kitap, String> konuKolon = new TableColumn<>("Konu");
		konuKolon.setCellValueFactory(new PropertyValueFactory<>("konu"));

		TableColumn<Kitap, Boolean> oduncKolon = new TableColumn<>("OduncVerildi");
		oduncKolon.setCellValueFactory(new PropertyValueFactory<>("oduncVerildi"));

		TableColumn<Kitap, Double> puanKolon = new TableColumn<>("Puan");
		puanKolon.setCellValueFactory(new PropertyValueFactory<>("puan"));

		TableColumn<Kitap, String> durumKolon = new TableColumn<>("Durum");
		durumKolon.setCellValueFactory(new PropertyValueFactory<>("durum"));

		kullaniciKitapListesi.getColumns().addAll(kitapIDKolon, baslikKolon, yazarKolon, turKolon, konuKolon, oduncKolon, puanKolon, durumKolon);
	}


	private void kitapEkleModalGoster() {
		Stage modalStage = new Stage();
		modalStage.initModality(Modality.APPLICATION_MODAL);

		VBox modalLayout = new VBox(10);
		modalLayout.setPadding(new Insets(10));

		TextField baslikField = new TextField();
		baslikField.setPromptText("Başlık");
		TextField yazarField = new TextField();
		yazarField.setPromptText("Yazar");
		TextField turField = new TextField();
		turField.setPromptText("Tür");
		TextField konuField = new TextField();
		konuField.setPromptText("Konu");
		ComboBox<String> durumComboBox = new ComboBox<>();
		durumComboBox.setItems(FXCollections.observableArrayList("Rafta", "Ödünç Verildi", "Kayıp"));
		durumComboBox.setValue("Rafta");

		Button ekleButton = new Button("Ekle");
		ekleButton.setOnAction(e -> {
			kitapEkle(new Kitap(0, baslikField.getText(), yazarField.getText(), turField.getText(), konuField.getText(), false, 1.0, durumComboBox.getValue()));
			kitapYonetimi.kitapListesiGuncelle(true);
			modalStage.close();
		});

		modalLayout.getChildren().addAll(new Label("Kitap Ekle"), baslikField, yazarField, turField, konuField, durumComboBox, ekleButton);

		Scene modalScene = new Scene(modalLayout, 400, 300);
		modalStage.setScene(modalScene);
		modalStage.showAndWait();
	}

	private void kitapVerModalGoster(Kitap kitap) {
		Stage modalStage = new Stage();
		modalStage.initModality(Modality.APPLICATION_MODAL);
		modalStage.setTitle("Kitap Teslim Et");

		VBox layout = new VBox(10);
		layout.setPadding(new Insets(10));

		kullaniciListeGoster(kullaniciListesiMini);
		Button onaylaButton = new Button("Onayla");
		onaylaButton.setOnAction(e -> kitapVer(kitap, kullaniciListesiMini.getSelectionModel().getSelectedItem()));

		Button cikisButton = new Button("Çıkış");
		cikisButton.setOnAction(e -> modalStage.close());

		layout.getChildren().addAll(kullaniciListesiMini, onaylaButton, cikisButton);
		Scene scene = new Scene(layout, 400, 300);
		modalStage.setScene(scene);
		modalStage.showAndWait();
	}

	private void kitapAlModalGoster(Kullanici kullanici) {
		Stage modalStage = new Stage();
		modalStage.initModality(Modality.APPLICATION_MODAL);
		modalStage.setTitle("Kitap Teslim Al");

		VBox layout = new VBox(10);
		layout.setPadding(new Insets(10));

		kullaniciKitapListele(kullanici);
		Button onaylaButton = new Button("Onayla");
		onaylaButton.setOnAction(e -> kitapAl(kullaniciKitapListesi.getSelectionModel().getSelectedItem(), kullanici));

		Button cikisButton = new Button("Çıkış");
		cikisButton.setOnAction(e -> modalStage.close());

		layout.getChildren().addAll(kullaniciKitapListesi, onaylaButton, cikisButton);
		Scene scene = new Scene(layout, 400, 300);
		modalStage.setScene(scene);
		modalStage.showAndWait();
	}

	private void kitapVer(Kitap kitap, Kullanici kullanici) {
		commonFactory.businessKitapOlustur().kitapOduncAl(kullanici, kitap);
	}

	private void kitapAl(Kitap kitap, Kullanici kullanici) {
		commonFactory.businessKitapOlustur().kitapTeslimEt(kullanici, kitap);
	}

	private void kitapGuncelleModalGoster(Kitap kitap) {
		Stage modalStage = new Stage();
		modalStage.initModality(Modality.APPLICATION_MODAL);

		VBox modalLayout = new VBox(10);
		modalLayout.setPadding(new Insets(10));

		TextField baslikField = new TextField();
		baslikField.setPromptText("Başlık");
		TextField yazarField = new TextField();
		yazarField.setPromptText("Yazar");
		TextField turField = new TextField();
		turField.setPromptText("Tür");
		TextField konuField = new TextField();
		konuField.setPromptText("Konu");
		ComboBox<String> durumComboBox = new ComboBox<>();
		durumComboBox.setItems(FXCollections.observableArrayList("Rafta", "Ödünç Verildi", "Kayıp"));
		durumComboBox.setValue("Rafta");

		baslikField.setText(kitap.getBaslik());
		yazarField.setText(kitap.getYazar());
		turField.setText(kitap.getTur());
		konuField.setText(kitap.getKonu());
		durumComboBox.setValue(kitap.getDurum());


		Button guncelleButton = new Button("Güncelle");
		guncelleButton.setOnAction(e -> {
			if (!baslikField.getText().isEmpty())
				kitap.setBaslik(baslikField.getText());
			if (!yazarField.getText().isEmpty())
				kitap.setYazar(yazarField.getText());
			if (!turField.getText().isEmpty())
				kitap.setTur(turField.getText());
			if (!konuField.getText().isEmpty())
				kitap.setKonu(konuField.getText());
			if (durumComboBox.getValue() != null) {
				kitap.setDurum(durumComboBox.getValue());
				if (durumComboBox.getValue().equals("Ödünç Verildi")) {
					kitap.setDurum(new KitapOdunc()); // State tasarım deseni
					kitap.setOduncVerildi(true);
					kitap.durumDegistir();
				} else if (durumComboBox.getValue().equals("Rafta")) {
					kitap.setDurum(new KitapRafta());
					kitap.setOduncVerildi(false);
					kitap.durumDegistir();
				} else if (durumComboBox.getValue().equals("Kayıp")) {
					kitap.setDurum(new KitapKayip());
					kitap.setOduncVerildi(false);
					kitap.durumDegistir();
				}
			}
			kitapGuncelle(kitap);
			kitapYonetimi.kitapListesiGuncelle(true);
			modalStage.close();
		});

		modalLayout.getChildren().addAll(new Label("Kitap Güncelle"), baslikField, yazarField, turField, konuField, durumComboBox, guncelleButton);

		Scene modalScene = new Scene(modalLayout, 400, 300);
		modalStage.setScene(modalScene);
		modalStage.showAndWait();
	}

	private void kitapListeGuncelle(List<Kitap> kitaplar, TableView<Kitap> tableView) {
		ObservableList<Kitap> data = FXCollections.observableArrayList(
				kitaplar
		);
		tableView.setItems(data);
	}

	private void kutuphaneKitapListele() {
		List<Kitap> kitaplar = commonFactory.businessKitapOlustur().kitapListele();
		kitapListeGuncelle(kitaplar, kutuphaneKitapListesi);
	}

	private void kullaniciKitapListele(Kullanici kullanici) {
		List<Kitap> kitaplar = commonFactory.businessKitapOlustur().oduncKitapListele(kullanici);
		kitapListeGuncelle(kitaplar, kullaniciKitapListesi);
	}

	private void kitapEkle(Kitap kitap) {
		commonFactory.businessKitapOlustur().kitapEkle(kitap);
		kitapYonetimi.kitapListesiGuncelle(true);
	}

	private void kitapGuncelle(Kitap kitap) {
		commonFactory.businessKitapOlustur().kitapGuncelle(kitap);
		kitapYonetimi.kitapListesiGuncelle(true);
	}

	private void kitapSil(Kitap kitap) {
		commonFactory.businessKitapOlustur().oduncKitapSil(kitap);
		commonFactory.businessKitapOlustur().kitapSil(kitap);
		kitapYonetimi.kitapListesiGuncelle(true);
	}

	private void kitaplariAra() {
		String aramaKelimesi = aramaKutusu.getText();
		String secilenKriter = aramaKriteriComboBox.getValue();
		List<Kitap> kitaplar = null;
		if (aramaKelimesi != null && !aramaKelimesi.isEmpty()) {
			aramaKelimesi = aramaKelimesi.toLowerCase();
			if (Objects.equals(secilenKriter, "Yazar"))
				kitaplar = commonFactory.businessKitapOlustur().yazaraGoreKitapListele(aramaKelimesi);
			else if (Objects.equals(secilenKriter, "Başlık"))
				kitaplar = commonFactory.businessKitapOlustur().basligaGoreKitapListele(aramaKelimesi);
			else if (Objects.equals(secilenKriter, "Tür"))
				kitaplar = commonFactory.businessKitapOlustur().tureGoreKitapListele(aramaKelimesi);
			else if (Objects.equals(secilenKriter, "Konu"))
				kitaplar = commonFactory.businessKitapOlustur().konuyaGoreKitapListele(aramaKelimesi);
			else if (Objects.equals(secilenKriter, "Puan")) {
				if (isInteger(aramaKelimesi)) {
					kitaplar = commonFactory.businessKitapOlustur().puanaGoreKitapListele(aramaKelimesi);
				} else {
					System.out.println("Hatalı puan değeri");
				}
			}
			if (kitaplar != null) {
				kitapListeGuncelle(kitaplar, kutuphaneKitapListesi);
			}
		} else {
			kitapYonetimi.kitapListesiGuncelle(true);
		}
	}

	private boolean isInteger(String stringIfade) {
		try {
			Integer.parseInt(stringIfade);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	private Pane kullaniciPaneliOlustur() {
		VBox layout = new VBox(10);
		kullaniciListesi = new TableView<>();
		kullaniciListesi.setEditable(true);
		kullaniciListesi.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

		TableColumn<Kullanici, Integer> kullaniciIDKolon = new TableColumn<>("ID");
		kullaniciIDKolon.setCellValueFactory(new PropertyValueFactory<>("kullaniciID"));
		TableColumn<Kullanici, String> kullaniciAdiKolon = new TableColumn<>("Kullanıcı Adı");
		kullaniciAdiKolon.setCellValueFactory(new PropertyValueFactory<>("kullaniciAdi"));
		TableColumn<Kullanici, String> adKolon = new TableColumn<>("Ad");
		adKolon.setCellValueFactory(new PropertyValueFactory<>("ad"));
		TableColumn<Kullanici, String> soyadKolon = new TableColumn<>("Soyad");
		soyadKolon.setCellValueFactory(new PropertyValueFactory<>("soyad"));
		TableColumn<Kullanici, String> emailKolon = new TableColumn<>("Email");
		emailKolon.setCellValueFactory(new PropertyValueFactory<>("email"));
		TableColumn<Kullanici, String> turKolon = new TableColumn<>("Tür");
		turKolon.setCellValueFactory(new PropertyValueFactory<>("tur"));

		TableColumn<Kullanici, String> islemKolon = new TableColumn<>("İşlem");
		islemKolon.setCellFactory(col -> new TableCell<Kullanici, String>() {
			private final Button actionButton = new Button();

			@Override
			protected void updateItem(String item, boolean empty) {
				super.updateItem(item, empty);

				if (empty) {
					setGraphic(null);
					return;
				}

				Kullanici kullanici = getTableView().getItems().get(getIndex());
				actionButton.setText("Kitap Listesi");
				actionButton.setOnAction(e -> kitapAlModalGoster(kullanici));

				setGraphic(actionButton);
			}
		});

		kullaniciListesi.getColumns().addAll(kullaniciIDKolon, kullaniciAdiKolon, adKolon, soyadKolon, emailKolon, turKolon, islemKolon);
		kullaniciListeGoster(kullaniciListesi);
		miniKullaniciListesiOlustur();
		Button ekleButton = new Button("Kullanıcı Ekle");
		ekleButton.setOnAction(e -> kullaniciEkleModalGoster());

		Button silButton = new Button("Kullanıcı Sil");
		silButton.setOnAction(e -> kullaniciSil());

		Button duzenleButton = new Button("Kullanıcı Düzenle");
		duzenleButton.setOnAction(e -> kullaniciDuzenleModalGoster());

		if (kullaniciListesi.getSelectionModel().getSelectedItem() == null) {
			silButton.setDisable(true);
			duzenleButton.setDisable(true);
		}

		kullaniciListesi.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
			if (newSelection != null) {
				silButton.setDisable(false);
				duzenleButton.setDisable(false);
			}
		});

		HBox butonBar = new HBox(10, ekleButton, duzenleButton, silButton);
		butonBar.setAlignment(Pos.CENTER);

		layout.getChildren().addAll(kullaniciListesi, butonBar);
		return layout;
	}

	private void miniKullaniciListesiOlustur(){
		kullaniciListesiMini = new TableView<>();
		kullaniciListesiMini.setEditable(true);
		kullaniciListesiMini.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

		TableColumn<Kullanici, Integer> kullaniciIDKolon = new TableColumn<>("ID");
		kullaniciIDKolon.setCellValueFactory(new PropertyValueFactory<>("kullaniciID"));
		TableColumn<Kullanici, String> kullaniciAdiKolon = new TableColumn<>("Kullanıcı Adı");
		kullaniciAdiKolon.setCellValueFactory(new PropertyValueFactory<>("kullaniciAdi"));
		TableColumn<Kullanici, String> adKolon = new TableColumn<>("Ad");
		adKolon.setCellValueFactory(new PropertyValueFactory<>("ad"));
		TableColumn<Kullanici, String> soyadKolon = new TableColumn<>("Soyad");
		soyadKolon.setCellValueFactory(new PropertyValueFactory<>("soyad"));
		TableColumn<Kullanici, String> emailKolon = new TableColumn<>("Email");
		emailKolon.setCellValueFactory(new PropertyValueFactory<>("email"));
		TableColumn<Kullanici, String> turKolon = new TableColumn<>("Tür");
		turKolon.setCellValueFactory(new PropertyValueFactory<>("tur"));

		kullaniciListesiMini.getColumns().addAll(kullaniciIDKolon, kullaniciAdiKolon, adKolon, soyadKolon, emailKolon, turKolon);
		kullaniciListeGoster(kullaniciListesiMini);
	}

	private void kullaniciEkleModalGoster() {
		Stage modalStage = new Stage();
		modalStage.initModality(Modality.APPLICATION_MODAL);

		BorderPane borderPane = new BorderPane();

		VBox modalLayout = new VBox(10);
		modalLayout.setPadding(new Insets(10));
		TextField kullaniciAdiField = new TextField();
		kullaniciAdiField.setPromptText("Kullanıcı Adı");
		TextField adField = new TextField();
		adField.setPromptText("Ad");
		TextField soyadField = new TextField();
		soyadField.setPromptText("Soyad");
		TextField emailField = new TextField();
		emailField.setPromptText("Email");

		kullaniciTipiComboBox = new ComboBox<>();
		kullaniciTipiComboBox.setItems(FXCollections.observableArrayList("Personel", "Öğrenci", "Öğretim Üyesi"));
		kullaniciTipiComboBox.setValue("Öğrenci");
		kullaniciTipiComboBox.setOnAction(e -> kullaniciTipiDegistir());
		Button ekleButton = new Button("Ekle");

		ekleButton.setOnAction(e -> {
			String kullaniciTipi = kullaniciTipiComboBox.getValue();
			Kullanici yeniKullanici = new Kullanici();
			yeniKullanici.setKullaniciAdi(kullaniciAdiField.getText());
			yeniKullanici.setAd(adField.getText());
			yeniKullanici.setSoyad(soyadField.getText());
			yeniKullanici.setEmail(emailField.getText());
			yeniKullanici.setParola(adField.getText() + soyadField.getText());
			switch (kullaniciTipi) {
				case "Personel" -> {
					yeniKullanici.setTur(2);
					Personel personel = new Personel();
					personel.setPozisyon(personelPozisyonTextField.getText());
					personel.setKullanici(yeniKullanici);
					commonFactory.businessPersonelOlustur().personelEkle(personel, yeniKullanici);
				}
				case "Öğrenci" -> {
					yeniKullanici.setTur(1);
					Ogrenci ogrenci = new Ogrenci();
					ogrenci.setBolum(ogrenciBolumTextField.getText());
					ogrenci.setKayitYili(Integer.parseInt(ogrenciKayitYiliTextField.getText()));
					ogrenci.setKullanici(yeniKullanici);
					commonFactory.businessOgrenciOlustur().ogrenciEkle(ogrenci, yeniKullanici);
				}
				case "Öğretim Üyesi" -> {
					yeniKullanici.setTur(3);
					OgretimUyesi ogretimUyesi = new OgretimUyesi();
					ogretimUyesi.setBolum(ogretimUyesiTextField.getText());
					ogretimUyesi.setKullanici(yeniKullanici);
					commonFactory.businessOgretimUyesiOlustur().ogretimUyesiEkle(ogretimUyesi, yeniKullanici);
				}
			}
			kullaniciListeGoster(kullaniciListesi);
			modalStage.close();
		});

		HBox bottomLayout = new HBox(50);
		bottomLayout.setAlignment(Pos.CENTER);
		bottomLayout.getChildren().addAll(kullaniciTipiComboBox, ekleButton);
		bottomLayout.setPadding(new Insets(10, 10, 20, 10));

		ogrenciPaneli = olusturOgrenciPaneli();
		ogretimUyesiPaneli = olusturOgretimUyesiPaneli();
		personelPaneli = olusturPersonelPaneli();

		modalLayout.getChildren().addAll(new Label("Kullanıcı Ekle"), kullaniciAdiField, adField, soyadField, emailField, ogrenciPaneli, ogretimUyesiPaneli, personelPaneli);

		borderPane.setCenter(modalLayout);
		borderPane.setBottom(bottomLayout);

		Scene modalScene = new Scene(borderPane, 300, 400);
		modalStage.setScene(modalScene);
		modalStage.showAndWait();
	}

	private void kullaniciTipiDegistir() {
		String secilenTip = kullaniciTipiComboBox.getValue();
		personelPaneli.setVisible(secilenTip.equals("Personel"));
		personelPaneli.setManaged(secilenTip.equals("Personel"));
		ogrenciPaneli.setVisible(secilenTip.equals("Öğrenci"));
		ogrenciPaneli.setManaged(secilenTip.equals("Öğrenci"));
		ogretimUyesiPaneli.setVisible(secilenTip.equals("Öğretim Üyesi"));
		ogretimUyesiPaneli.setManaged(secilenTip.equals("Öğretim Üyesi"));
	}

	private VBox olusturOgrenciPaneli() {
		VBox panel = new VBox(10);
		panel.setVisible(false);
		ogrenciKayitYiliTextField = new TextField();
		ogrenciKayitYiliTextField.setPromptText("Kayıt Yılı");
		ogrenciBolumTextField = new TextField();
		ogrenciBolumTextField.setPromptText("Bölüm");
		panel.getChildren().addAll(ogrenciKayitYiliTextField, ogrenciBolumTextField);
		return panel;
	}

	private VBox olusturOgretimUyesiPaneli() {
		VBox panel = new VBox(10);
		panel.setVisible(false);
		ogretimUyesiTextField = new TextField();
		ogretimUyesiTextField.setPromptText("Bölüm");
		panel.getChildren().addAll(ogretimUyesiTextField);
		return panel;
	}

	private VBox olusturPersonelPaneli() {
		VBox panel = new VBox(10);
		panel.setVisible(false);
		personelPozisyonTextField = new TextField();
		personelPozisyonTextField.setPromptText("Pozisyon");
		panel.getChildren().addAll(personelPozisyonTextField);
		return panel;
	}

	private void kullaniciSil() {
		Kullanici secilenKullanici = kullaniciListesi.getSelectionModel().getSelectedItem();
		if (secilenKullanici != null) {
			try {
				commonFactory.businessKullaniciOlustur().kullaniciSil(secilenKullanici);
				kullaniciListeGoster(kullaniciListesi);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void kullaniciDuzenleModalGoster() {
		Stage modalStage = new Stage();
		modalStage.initModality(Modality.APPLICATION_MODAL);

		BorderPane borderPane = new BorderPane();

		VBox modalLayout = new VBox(10);
		modalLayout.setPadding(new Insets(10));
		TextField kullaniciAdiField = new TextField();
		kullaniciAdiField.setPromptText("Kullanıcı Adı");
		TextField adField = new TextField();
		adField.setPromptText("Ad");
		TextField soyadField = new TextField();
		soyadField.setPromptText("Soyad");
		TextField emailField = new TextField();
		emailField.setPromptText("Email");

		Kullanici seciliKullanici = kullaniciListesi.getSelectionModel().getSelectedItem();

		if (seciliKullanici == null) {
			return;
		}

		kullaniciTipiComboBox = new ComboBox<>();
		kullaniciTipiComboBox.setItems(FXCollections.observableArrayList("Personel", "Öğrenci", "Öğretim Üyesi"));

		if (seciliKullanici.getTur() == 1) {
			kullaniciTipiComboBox.setValue("Öğrenci");
		} else if (seciliKullanici.getTur() == 2) {
			kullaniciTipiComboBox.setValue("Personel");
		} else if (seciliKullanici.getTur() == 3) {
			kullaniciTipiComboBox.setValue("Öğretim Üyesi");
		}

		kullaniciTipiComboBox.setOnAction(e -> kullaniciTipiDegistir());

		Button guncelleButon = new Button("Güncelle");
		String kullaniciTipi = kullaniciTipiComboBox.getValue();
		guncelleButon.setOnAction(e -> {
			String kullaniciTipiLocal = kullaniciTipiComboBox.getValue();
			Kullanici yeniKullanici = new Kullanici();
			yeniKullanici.setKullaniciID(kullaniciListesi.getSelectionModel().getSelectedItem().getKullaniciID());
			yeniKullanici.setKullaniciAdi(kullaniciAdiField.getText());
			yeniKullanici.setAd(adField.getText());
			yeniKullanici.setSoyad(soyadField.getText());
			yeniKullanici.setEmail(emailField.getText());
			yeniKullanici.setParola(adField.getText() + soyadField.getText());
			switch (kullaniciTipiLocal) {
				case "Personel" -> {
					yeniKullanici.setTur(2);
					Personel personel = new Personel();
					personel.setPozisyon(personelPozisyonTextField.getText());
					personel.setKullanici(yeniKullanici);
					commonFactory.businessPersonelOlustur().personelGuncelle(personel, yeniKullanici);
				}
				case "Öğrenci" -> {
					yeniKullanici.setTur(1);
					Ogrenci ogrenci = new Ogrenci();
					ogrenci.setBolum(ogrenciBolumTextField.getText());
					ogrenci.setKayitYili(Integer.parseInt(ogrenciKayitYiliTextField.getText()));
					ogrenci.setKullanici(yeniKullanici);
					commonFactory.businessOgrenciOlustur().ogrenciGuncelle(ogrenci, yeniKullanici);
				}
				case "Öğretim Üyesi" -> {
					yeniKullanici.setTur(3);
					OgretimUyesi ogretimUyesi = new OgretimUyesi();
					ogretimUyesi.setBolum(ogretimUyesiTextField.getText());
					ogretimUyesi.setKullanici(yeniKullanici);
					commonFactory.businessOgretimUyesiOlustur().ogretimUyesiGuncelle(ogretimUyesi, yeniKullanici);
				}
			}
			kullaniciListeGoster(kullaniciListesi);
			modalStage.close();
		});

		HBox bottomLayout = new HBox(50);
		bottomLayout.setAlignment(Pos.CENTER);
		bottomLayout.getChildren().addAll(kullaniciTipiComboBox, guncelleButon);
		bottomLayout.setPadding(new Insets(10, 10, 20, 10));

		ogrenciPaneli = olusturOgrenciPaneli();
		ogretimUyesiPaneli = olusturOgretimUyesiPaneli();
		personelPaneli = olusturPersonelPaneli();

		kullaniciTipiDegistir();
		modalLayout.getChildren().addAll(new Label("Kullanıcı Ekle"), kullaniciAdiField, adField, soyadField, emailField, ogrenciPaneli, ogretimUyesiPaneli, personelPaneli);

		borderPane.setCenter(modalLayout);
		borderPane.setBottom(bottomLayout);

		kullaniciAdiField.setText(kullaniciListesi.getSelectionModel().getSelectedItem().getKullaniciAdi());
		adField.setText(kullaniciListesi.getSelectionModel().getSelectedItem().getAd());
		soyadField.setText(kullaniciListesi.getSelectionModel().getSelectedItem().getSoyad());
		emailField.setText(kullaniciListesi.getSelectionModel().getSelectedItem().getEmail());

		switch (kullaniciTipi) {
			case "Personel" -> {
				personelPozisyonTextField.setText(commonFactory.businessPersonelOlustur().personelGetir(kullaniciListesi.getSelectionModel().getSelectedItem().getKullaniciID()).getPozisyon());
			}
			case "Öğrenci" -> {
				Ogrenci ogrenci = commonFactory.businessOgrenciOlustur().ogrenciGetir(kullaniciListesi.getSelectionModel().getSelectedItem().getKullaniciID());
				ogrenciKayitYiliTextField.setText(String.valueOf(ogrenci.getKayitYili()));
				ogrenciBolumTextField.setText(ogrenci.getBolum());
			}
			case "Öğretim Üyesi" -> {
				ogretimUyesiTextField.setText(commonFactory.businessOgretimUyesiOlustur().ogretimUyesiGetir(kullaniciListesi.getSelectionModel().getSelectedItem().getKullaniciID()).getBolum());
			}
		}

		Scene modalScene = new Scene(borderPane, 300, 400);
		modalStage.setScene(modalScene);
		modalStage.showAndWait();
	}

	private void kullaniciListeGuncelle(List<Kullanici> kullanicilar, TableView<Kullanici> tableView) {
		ObservableList<Kullanici> data = FXCollections.observableArrayList(
				kullanicilar
		);
		tableView.setItems(data);
	}

	private void kullaniciListeGoster(TableView<Kullanici> kullaniciListesi) {
		List<Kullanici> kullanicilar = commonFactory.businessKullaniciOlustur().kullaniciListele();
		kullaniciListeGuncelle(kullanicilar, kullaniciListesi);
	}

	public VBox getPanel() {
		return panel;
	}
}

