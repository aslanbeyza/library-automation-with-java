package org.library.businesslayer;

import org.library.helper.CommonFactory;
import org.library.model.Kullanici;
import org.library.model.OgretimUyesi;

import java.util.List;

public class BusinessOgretimUyesi {

	CommonFactory commmonFactory = CommonFactory.getInstance();

	public OgretimUyesi ogretimUyesiEkle(OgretimUyesi ogretimUyesi, Kullanici kullanici) {

		int kullanici1 = commmonFactory.kullaniciRepositoryOlustur().ekleReturn(kullanici);
		kullanici.setKullaniciID(kullanici1);
		ogretimUyesi.setKullanici(kullanici);
		commmonFactory.ogretimUyesiRepositoryOlustur().ekle(ogretimUyesi);

		return ogretimUyesi;
	}

	public OgretimUyesi ogretimUyesiSil(OgretimUyesi ogretimUyesi, Kullanici kullanici) {

		commmonFactory.ogretimUyesiRepositoryOlustur().sil(ogretimUyesi.getOgretimUyesiID());
		commmonFactory.kullaniciRepositoryOlustur().sil(kullanici.getKullaniciID());

		return ogretimUyesi;
	}

	public OgretimUyesi ogretimUyesiGuncelle(OgretimUyesi ogretimUyesi, Kullanici kullanici) {

		commmonFactory.ogretimUyesiRepositoryOlustur().guncelle(ogretimUyesi);
		commmonFactory.kullaniciRepositoryOlustur().guncelle(kullanici);

		return ogretimUyesi;
	}

	public OgretimUyesi ogretimUyesiGetir(int id) {
		return commmonFactory.ogretimUyesiRepositoryOlustur().getir(id);
	}

	public List<OgretimUyesi> ogretimUyesiListele() {
		return commmonFactory.ogretimUyesiRepositoryOlustur().hepsiniGetir();
	}

}
