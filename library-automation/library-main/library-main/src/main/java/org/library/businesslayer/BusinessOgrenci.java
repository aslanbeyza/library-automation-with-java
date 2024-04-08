package org.library.businesslayer;

import org.library.helper.CommonFactory;
import org.library.model.Kullanici;
import org.library.model.Ogrenci;

import java.util.List;

public class BusinessOgrenci {

	CommonFactory commmonFactory = CommonFactory.getInstance();

	public Ogrenci ogrenciEkle(Ogrenci ogrenci, Kullanici kullanici) {
		int kullanici1 = commmonFactory.kullaniciRepositoryOlustur().ekleReturn(kullanici);
		kullanici.setKullaniciID(kullanici1);
		ogrenci.setKullanici(kullanici);
		commmonFactory.ogrenciRepositoryOlustur().ekle(ogrenci);

		return ogrenci;
	}

	public Ogrenci ogrenciSil(Ogrenci ogrenci, Kullanici kullanici) {
		commmonFactory.ogrenciRepositoryOlustur().sil(ogrenci.getOgrenciID());
		commmonFactory.kullaniciRepositoryOlustur().sil(kullanici.getKullaniciID());

		return ogrenci;
	}

	public Ogrenci ogrenciGuncelle(Ogrenci ogrenci, Kullanici kullanici) {
		commmonFactory.ogrenciRepositoryOlustur().guncelle(ogrenci);
		commmonFactory.kullaniciRepositoryOlustur().guncelle(kullanici);

		return ogrenci;
	}

	public Ogrenci ogrenciGetir(int id) {
		return commmonFactory.ogrenciRepositoryOlustur().getir(id);
	}

	public List<Ogrenci> ogrenciListele() {
		return commmonFactory.ogrenciRepositoryOlustur().hepsiniGetir();
	}

}
