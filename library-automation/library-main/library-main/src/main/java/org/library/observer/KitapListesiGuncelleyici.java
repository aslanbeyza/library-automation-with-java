package org.library.observer;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import org.library.helper.CommonFactory;
import org.library.model.Kitap;
import org.library.model.Kullanici;

import java.util.List;

public class KitapListesiGuncelleyici implements KitapObserver {
	CommonFactory commonFactory = CommonFactory.getInstance();
	private Kullanici kullanici = commonFactory.getAktifKullanici();
	private TableView<Kitap> kitapListesi;

	public KitapListesiGuncelleyici(TableView<Kitap> kitapListesi) {
		this.kitapListesi = kitapListesi;
	}

	@Override
	public void kutuphaneKitapListesiGuncellendi(boolean kutuphaneMi) {
		List<Kitap> kitaplar = null;
		if (kutuphaneMi) {
			kitaplar = commonFactory.businessKitapOlustur().kitapListele();
		} else {
			kitaplar = commonFactory.businessKitapOlustur().oduncKitapListele(kullanici);
		}
		ObservableList<Kitap> data = FXCollections.observableArrayList(
				kitaplar
		);
		kitapListesi.setItems(data);
	}
}
