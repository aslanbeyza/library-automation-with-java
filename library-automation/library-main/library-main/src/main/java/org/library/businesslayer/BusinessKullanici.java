package org.library.businesslayer;

import org.library.helper.CommonFactory;
import org.library.model.Kullanici;

import java.util.List;

public class BusinessKullanici {

	CommonFactory commmonFactory = CommonFactory.getInstance();

	public void kullaniciEkle(Kullanici kullanici) {
		commmonFactory.kullaniciRepositoryOlustur().ekle(kullanici);
	}

	public void kullaniciSil(Kullanici kullanici) {
		commmonFactory.kullaniciRepositoryOlustur().sil(kullanici.getKullaniciID());
	}

	public void kullaniciGuncelle(Kullanici kullanici) {
		commmonFactory.kullaniciRepositoryOlustur().guncelle(kullanici);
	}

	public Kullanici kullaniciGetir(int kullaniciID) {
		return commmonFactory.kullaniciRepositoryOlustur().getir(kullaniciID);
	}

	public List<Kullanici> kullaniciListele() {
		return commmonFactory.kullaniciRepositoryOlustur().hepsiniGetir();
	}
}
