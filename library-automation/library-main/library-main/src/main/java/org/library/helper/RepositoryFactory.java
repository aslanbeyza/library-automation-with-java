package org.library.helper;


import org.library.dataaccesslayer.api.ARepository;
import org.library.dataaccesslayer.impl.*;

public class RepositoryFactory {

	public static ARepository<?> repositoryOlustur(String type) {
		switch (type) {
			case "Kitap":
				return new KitapRepository();
			case "Kullanici":
				return new KullaniciRepository();
			case "Personel":
				return new PersonelRepository();
			case "Ogrenci":
				return new OgrenciRepository();
			case "OgretimUyesi":
				return new OgretimUyesiRepository();
			default:
				throw new IllegalArgumentException("Bilinmeyen tip");
		}
	}
}
