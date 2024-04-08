package org.library.helper;

import org.library.businesslayer.*;
import org.library.dataaccesslayer.impl.*;
import org.library.model.Kullanici;
import org.library.view.OgrenciEkrani;
import org.library.view.PersonelEkrani;


public class CommonFactory {

	private static CommonFactory instance = null;

	private CommonFactory() {

	}

	private Kullanici aktifKullanici = new Kullanici();
	public static CommonFactory getInstance() {
		if (instance == null) {
			synchronized (CommonFactory.class) {
				instance = new CommonFactory();
			}
		}
		return instance;
	}

	public KullaniciRepository kullaniciRepositoryOlustur() {
		return (KullaniciRepository) RepositoryFactory.repositoryOlustur("Kullanici");
	}

	public KitapRepository kitapRepositoryOlustur() {
		return (KitapRepository) RepositoryFactory.repositoryOlustur("Kitap");
	}

	public OgrenciRepository ogrenciRepositoryOlustur() {
		return (OgrenciRepository) RepositoryFactory.repositoryOlustur("Ogrenci");
	}

	public OgretimUyesiRepository ogretimUyesiRepositoryOlustur() {
		return (OgretimUyesiRepository) RepositoryFactory.repositoryOlustur("OgretimUyesi");
	}

	public PersonelRepository personelRepositoryOlustur() {
		return (PersonelRepository) RepositoryFactory.repositoryOlustur("Personel");
	}

	public OduncKitapRepository oduncKitapRepositoryOlustur() {
		return new OduncKitapRepository();
	}

	public OgrenciEkrani ogrenciEkraniOlustur() {
		return new OgrenciEkrani();
	}

	public PersonelEkrani personelEkraniOlustur() {
		return new PersonelEkrani();
	}

	public BusinessOgrenci businessOgrenciOlustur() {
		return new BusinessOgrenci();
	}

	public BusinessKitap businessKitapOlustur() {
		return new BusinessKitap();
	}

	public BusinessKullanici businessKullaniciOlustur() {
		return new BusinessKullanici();
	}

	public BusinessPersonel businessPersonelOlustur() {
		return new BusinessPersonel();
	}

	public BusinessOgretimUyesi businessOgretimUyesiOlustur() {
		return new BusinessOgretimUyesi();
	}

	public Kullanici getAktifKullanici() {
		return aktifKullanici;
	}

	public void setAktifKullanici(Kullanici aktifKullanici) {
		this.aktifKullanici = aktifKullanici;
	}

}
