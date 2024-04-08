package org.library.businesslayer;

import org.library.helper.CommonFactory;
import org.library.model.Kullanici;
import org.library.model.Personel;

import java.util.List;

public class BusinessPersonel {
	CommonFactory commmonFactory = CommonFactory.getInstance();

	public Personel personelEkle(Personel personel, Kullanici kullanici) {

		int kullanici1 = commmonFactory.kullaniciRepositoryOlustur().ekleReturn(kullanici);
		kullanici.setKullaniciID(kullanici1);
		personel.setKullanici(kullanici);
		commmonFactory.personelRepositoryOlustur().ekle(personel);


		return personel;
	}

	public Personel personelSil(Personel personel, Kullanici kullanici) {

		commmonFactory.personelRepositoryOlustur().sil(personel.getPersonelID());
		commmonFactory.kullaniciRepositoryOlustur().sil(kullanici.getKullaniciID());

		return personel;
	}

	public Personel personelGuncelle(Personel personel, Kullanici kullanici) {

		commmonFactory.personelRepositoryOlustur().guncelle(personel);
		commmonFactory.kullaniciRepositoryOlustur().guncelle(kullanici);

		return personel;
	}

	public Personel personelGetir(int id) {
		return commmonFactory.personelRepositoryOlustur().getir(id);
	}

	public List<Personel> personelListele() {
		return commmonFactory.personelRepositoryOlustur().hepsiniGetir();
	}
}
