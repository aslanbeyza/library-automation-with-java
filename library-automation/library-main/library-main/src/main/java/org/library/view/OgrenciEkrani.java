package org.library.view;

// İçe aktarmalar (imports)
// ...

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import org.library.helper.CommonFactory;
import org.library.model.Kitap;
import org.library.model.KitapYonetimi;
import org.library.model.Kullanici;
import org.library.observer.KitapListesiGuncelleyici;

import java.util.List;
import java.util.Objects;

public class OgrenciEkrani {
	private VBox layout;
	private ComboBox<String> aramaKriteriComboBox;
	private TableView<Kitap> kitapListesi;
	private TextField aramaKutusu;
	private TextField puanlamaKutusu;
	private Button kitapAlButonu, kitapPuanlaButonu, kitapTeslimEt;
	private CommonFactory commonFactory = CommonFactory.getInstance();
	private boolean kutuphaneMi = true;

	private KitapYonetimi kitapYonetimi;

	Kullanici kullanici = commonFactory.getAktifKullanici();

	public OgrenciEkrani() {
		init();
	}

	private void init() {

		layout = new VBox(10);
		layout.setAlignment(Pos.CENTER);
		layout.setPadding(new Insets(15));

		Button cikisButonu = new Button("Çıkış");
		cikisButonu.setOnAction(e -> Platform.exit());

		// Kullanıcı adını gösteren etiket
		Label kullaniciAdiLabel = new Label("Sn. " + kullanici.getKullaniciAdi());
		kullaniciAdiLabel.setPadding(new Insets(10, 10, 10, 10));
		HBox headerBox = new HBox();
		headerBox.getChildren().addAll(kullaniciAdiLabel, cikisButonu);
		HBox.setHgrow(kullaniciAdiLabel, Priority.ALWAYS);

		headerBox.setAlignment(Pos.TOP_RIGHT);

		layout.getChildren().add(0, headerBox);

		ToggleButton kutuphaneButton = new ToggleButton("Tüm Kitaplar");
		ToggleButton kendiKitaplarimButton = new ToggleButton("Benim Kitaplarım");

		ToggleGroup toggleGroup = new ToggleGroup();

		kutuphaneButton.setToggleGroup(toggleGroup);
		kendiKitaplarimButton.setToggleGroup(toggleGroup);
		kendiKitaplarimButton.setSelected(true);
		toggleGroup.selectedToggleProperty().addListener((observable, oldToggle, newToggle) -> {
			ToggleButton selectedButton = (ToggleButton) newToggle;
			ToggleButton oldButton = (ToggleButton) oldToggle;
			if (selectedButton != null) {
				selectedButton.setStyle("-fx-background-color: darkgreen; -fx-text-fill: white;");
			}
			if (oldButton != null) {
				oldButton.setStyle("-fx-background-color: green; -fx-text-fill: white;");
			}
		});

		kendiKitaplarimButton.setStyle("-fx-background-color: darkgreen; -fx-text-fill: white;");
		kutuphaneButton.setStyle("-fx-background-color: green; -fx-text-fill: white;");

		kutuphaneButton.setOnAction(e -> {
			if (kutuphaneButton.isSelected()) {
				kutuphaneArayuzu();
			}
		});

		kendiKitaplarimButton.setOnAction(e -> {
			if (kendiKitaplarimButton.isSelected()) {
				kendiKitaplarimArayuzu();
			}
		});

		HBox toggleButtonBar = new HBox(10, kutuphaneButton, kendiKitaplarimButton);
		toggleButtonBar.setAlignment(Pos.CENTER);

		layout.getChildren().add(toggleButtonBar);

		kitapListesi = new TableView<>();
		kitapListesi.setPrefHeight(700);
		kitapListesi.setPrefWidth(700);

		KitapListesiGuncelleyici kitapListesiGuncelleyici = new KitapListesiGuncelleyici(kitapListesi);
		kitapYonetimi = new KitapYonetimi();
		kitapYonetimi.addObserver(kitapListesiGuncelleyici);

		puanlamaKutusu = new TextField();
		puanlamaKutusu.setPromptText("Puan");

		kitapAlButonu = new Button("Kitap Al");
		kitapPuanlaButonu = new Button("Puanla");
		kitapTeslimEt = new Button("Teslim Et");

		aramaKriteriComboBox = new ComboBox<>();
		aramaKriteriComboBox.setItems(FXCollections.observableArrayList("Yazar", "Başlık", "Tür", "Konu", "Puan"));
		aramaKriteriComboBox.setValue("Yazar");

		aramaKutusu = new TextField();
		aramaKutusu.setPromptText("Aranacak kelimeyi giriniz.");
		aramaKutusu.setOnKeyReleased(e -> kitaplariAra());

		HBox aramaVePuanlama = new HBox(20, aramaKriteriComboBox, aramaKutusu, puanlamaKutusu, kitapPuanlaButonu);
		aramaVePuanlama.setAlignment(Pos.CENTER);

		layout.getChildren().addAll(kitapListesi, aramaVePuanlama, kitapAlButonu, kitapTeslimEt);

		kitapTabloOlustur();
		kendiKitaplarimArayuzu();
		if(kitapListesi.getSelectionModel().getSelectedItem() != null) {
			kitapPuanlaButonu.setDisable(false);
		} else {
			kitapPuanlaButonu.setDisable(true);
		}


		kitapAlButonu.setOnAction(e -> kitabiAl());
		kitapTeslimEt.setOnAction(e -> kitabiTeslimEt());
		kitapPuanlaButonu.setOnAction(e -> kitabiPuanla());
	}

	private void kutuphaneArayuzu() {
		kutuphaneMi = true;
		kitapYonetimi.kitapListesiGuncelle(kutuphaneMi);
	}

	private void kendiKitaplarimArayuzu() {
		kutuphaneMi = false;
		kitapYonetimi.kitapListesiGuncelle(kutuphaneMi);
	}

	private void kitapTabloOlustur() {
		kitapListesi.setEditable(true);
		kitapListesi.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
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

		TableColumn<Kitap, Double> durumKolon = new TableColumn<>("Durum");
		puanKolon.setCellValueFactory(new PropertyValueFactory<>("durum"));

		kitapListesi.getColumns().clear();
		kitapListesi.getColumns().addAll(kitapIDKolon, baslikKolon, yazarKolon, turKolon, konuKolon, oduncKolon, durumKolon, puanKolon);
	}

	private void listeGuncelle(List<Kitap> kitaplar) {
		ObservableList<Kitap> data = FXCollections.observableArrayList(
				kitaplar
		);
		kitapListesi.setItems(data);
	}

	private void kutuphaneKitaplariListele() {
		List<Kitap> kitaplar = commonFactory.businessKitapOlustur().kitapListele();
		listeGuncelle(kitaplar);
	}

	private void kullaniciKitaplariListele() {
		List<Kitap> kitaplar = commonFactory.businessKitapOlustur().oduncKitapListele(kullanici);
		listeGuncelle(kitaplar);
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
				if (kutuphaneMi)
					listeGuncelle(kitaplar);
				else {
					List<Kitap> kitaplar2 = commonFactory.businessKitapOlustur().oduncKitapListele(kullanici);
					kitaplar.retainAll(kitaplar2);
					listeGuncelle(kitaplar);
				}
			}
		} else {
			if (kutuphaneMi)
				kutuphaneKitaplariListele();
			else
				kullaniciKitaplariListele();
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

	private void kitabiTeslimEt() {
		Kitap secilenKitap = kitapListesi.getSelectionModel().getSelectedItem();
		if (kutuphaneMi) {
			System.out.println("Lütfen kendi kitaplarınızı teslim ediniz.");
			return;
		}
		if (secilenKitap != null) {
			commonFactory.businessKitapOlustur().kitapTeslimEt(kullanici, secilenKitap);
		}
	}

	private void kitabiAl() {
		Kitap secilenKitap = kitapListesi.getSelectionModel().getSelectedItem();
		if (secilenKitap != null) {
			commonFactory.businessKitapOlustur().kitapOduncAl(kullanici, secilenKitap);
		}
	}

	private void kitabiPuanla() {
		if (kullanici.getTur() != 1 && kullanici.getTur() != 3) {
			System.out.println("Bu işlemi yapmaya yetkiniz yok.");
			return;
		}

		if (kitapListesi.getSelectionModel().getSelectedItem() == null) {
			System.out.println("Lütfen bir kitap seçiniz.");
			return;
		}

		if (kutuphaneMi) {
			System.out.println("Lütfen kendi kitaplarınızı puanlayınız.");
			return;
		}
		Kitap secilenKitap = kitapListesi.getSelectionModel().getSelectedItem();
		String puan = puanlamaKutusu.getText();
		if (secilenKitap != null && puan != null && !puan.isEmpty()) {
			try {
				double puanDegeri = Double.parseDouble(puan);
				if (puanDegeri < 0 || puanDegeri > 5) {
					System.out.println("Hatalı puan değeri");
					return;
				}
				double mevcutPuan = secilenKitap.getPuan();
				int puanlayanKisiSayisi = 50;
				double yeniPuan = (mevcutPuan * puanlayanKisiSayisi + puanDegeri) / (puanlayanKisiSayisi + 1);
				secilenKitap.setPuan(yeniPuan);
				commonFactory.businessKitapOlustur().kitapGuncelle(secilenKitap);
			} catch (NumberFormatException e) {
				System.out.println("Hatalı puan değeri");
			}
		}
	}

	public VBox getPanel() {
		return layout;
	}
}

