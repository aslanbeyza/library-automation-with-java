package org.library.businesslayer;

import org.library.helper.CommonFactory;
import org.library.model.Kitap;
import org.library.model.Kullanici;
import org.library.model.OduncKitap;
import org.library.state.KitapOdunc;
import org.library.state.KitapRafta;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BusinessKitap {
	CommonFactory commmonFactory = CommonFactory.getInstance();

	public void kitapEkle(Kitap kitap) {
		commmonFactory.kitapRepositoryOlustur().ekle(kitap);
	}

	public void kitapSil(Kitap kitap) {
		commmonFactory.kitapRepositoryOlustur().sil(kitap.getKitapID());
	}

	public void kitapGuncelle(Kitap kitap) {
		commmonFactory.kitapRepositoryOlustur().guncelle(kitap);
	}

	public Kitap kitapGetir(Kitap kitap) {
		return commmonFactory.kitapRepositoryOlustur().getir(kitap.getKitapID());
	}

	public List<Kitap> kitapListele() {
		List<Kitap> kitaplar = commmonFactory.kitapRepositoryOlustur().hepsiniGetir();
		return kitaplar;
	}

	public List<Kitap> yazaraGoreKitapListele(String yazar) {
		List<Kitap> kitaplar = kitapListele();
		List<Kitap> yazarinKitaplari = new ArrayList<Kitap>();
		for (Kitap kitap : kitaplar) {
			if (kitap.getYazar().toLowerCase().contains(yazar)) {
				yazarinKitaplari.add(kitap);
			}
		}
		return yazarinKitaplari;
	}

	public List<Kitap> konuyaGoreKitapListele(String konu) {
		List<Kitap> kitaplar = kitapListele();
		List<Kitap> konuyaGoreKitaplar = new ArrayList<Kitap>();
		for (Kitap kitap : kitaplar) {
			if (kitap.getKonu().toLowerCase().contains(konu)) {
				konuyaGoreKitaplar.add(kitap);
			}
		}
		return konuyaGoreKitaplar;
	}

	public List<Kitap> puanaGoreKitapListele(String puan) {
		List<Kitap> kitaplar = kitapListele();
		List<Kitap> puanaGoreKitaplar = new ArrayList<Kitap>();
		for (Kitap kitap : kitaplar) {
			if (kitap.getPuan() == Integer.parseInt(puan)) {
				puanaGoreKitaplar.add(kitap);
			}
		}
		return puanaGoreKitaplar;
	}

	public List<Kitap> tureGoreKitapListele(String tur) {
		List<Kitap> kitaplar = kitapListele();
		List<Kitap> tureGoreKitaplar = new ArrayList<Kitap>();
		for (Kitap kitap : kitaplar) {
			if (kitap.getTur().toLowerCase().contains(tur)) {
				tureGoreKitaplar.add(kitap);
			}
		}
		return tureGoreKitaplar;
	}

	public List<Kitap> basligaGoreKitapListele(String baslik) {
		List<Kitap> kitaplar = kitapListele();
		List<Kitap> basligaGoreKitaplar = new ArrayList<Kitap>();
		for (Kitap kitap : kitaplar) {
			if (kitap.getBaslik().toLowerCase().contains(baslik)) {
				basligaGoreKitaplar.add(kitap);
			}
		}
		return basligaGoreKitaplar;
	}

	public Kitap kitapOduncAl(Kullanici kullanici, Kitap kitap) {
		if (kitap.isOduncVerildi()) {
			System.out.println("Bu kitap zaten ödünç verilmiş.");
			return null;
		} else if (!Objects.equals(kitap.getDurum(), "Rafta")) {
			System.out.println("Bu kitap rafta değil.");
			return null;
		}

		kitap.setOduncVerildi(true);
		kitap.setDurum("Ödünç");
		kitap.setDurum(new KitapOdunc());
		kitap.durumDegistir();

		commmonFactory.kitapRepositoryOlustur().guncelle(kitap);

		OduncKitap oduncKitap = new OduncKitap();
		oduncKitap.setKitap(kitap);
		oduncKitap.setKullanici(kullanici);
		oduncKitap.setOduncTarihi(java.time.LocalDate.now());
		commmonFactory.oduncKitapRepositoryOlustur().ekle(oduncKitap);

		return kitap;
	}

	public List<Kitap> ortakKitaplariBul(List<Kitap> yazaraGoreListe, List<Kitap> konuyaGoreListe) {
		List<Kitap> ortakKitaplar = new ArrayList<>(yazaraGoreListe);
		ortakKitaplar.retainAll(konuyaGoreListe);
		return ortakKitaplar;
	}

	public Kitap kitapTeslimEt(Kullanici kullanici, Kitap kitap) {
		if (!Objects.equals(kitap.getDurum(), "Ödünç Verildi")) {
			System.out.println("Bu kitap ödünç verilmiş değil.");
			return null;
		}

		kitap.setOduncVerildi(false);
		kitap.setDurum("Rafta");
		kitap.setDurum(new KitapRafta());
		kitap.durumDegistir();
		commmonFactory.kitapRepositoryOlustur().guncelle(kitap);
		commmonFactory.oduncKitapRepositoryOlustur().sil(kitap.getKitapID());

		return kitap;
	}

	public List<Kitap> oduncKitapListele(Kullanici kullanici) {
		List<OduncKitap> oduncKitaplar = commmonFactory.oduncKitapRepositoryOlustur().hepsiniGetir(kullanici);
		List<Kitap> kitaplar = new ArrayList<Kitap>();
		for (OduncKitap oduncKitap : oduncKitaplar) {
			kitaplar.add(oduncKitap.getKitap());
		}
		return kitaplar;
	}

	public void oduncKitapSil(Kitap kitap) {
		commmonFactory.oduncKitapRepositoryOlustur().sil(kitap.getKitapID());
	}

}
