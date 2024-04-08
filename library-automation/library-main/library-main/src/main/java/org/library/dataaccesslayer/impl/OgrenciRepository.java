package org.library.dataaccesslayer.impl;

import org.library.dataaccesslayer.api.ARepository;
import org.library.model.Kullanici;
import org.library.model.Ogrenci;

import java.util.List;

public class OgrenciRepository extends ARepository<Ogrenci> {
	@Override
	public List<Ogrenci> hepsiniGetir() {

		return dbopHelper.listeSorgusuCalistir("ogrenciListele.sql", rs ->{
				Kullanici kullanici = new Kullanici(rs.getInt("KullaniciID"), rs.getString("KullaniciAdi"), rs.getString("Ad"), rs.getString("Soyad"));
				Ogrenci ogrenci = new Ogrenci(kullanici, rs.getInt("OgrenciID"), rs.getInt("KayitYili"), rs.getString("Bolum"));

				return ogrenci;
		});
	}

	@Override
	public Ogrenci getir(int id) {
		return dbopHelper.listeSorgusuCalistir("ogrenciBul.sql", rs ->{
			Kullanici kullanici = new Kullanici();
			kullanici.setKullaniciID(rs.getInt("KullaniciID"));
			kullanici.setKullaniciAdi(rs.getString("KullaniciAdi"));
			kullanici.setAd(rs.getString("Ad"));
			kullanici.setSoyad(rs.getString("Soyad"));
			Ogrenci ogrenci = new Ogrenci();
			ogrenci.setKullanici(kullanici);
			ogrenci.setKayitYili(rs.getInt("KayitYili"));
			ogrenci.setBolum(rs.getString("Bolum"));
			return ogrenci;
		}, id).stream().findFirst().orElse(null);
	}

	@Override
	public void ekle(Ogrenci ogrenci) {
		dbopHelper.dosyadanSorguCalistir("ogrenciEkle.sql", ogrenci.getKullanici().getKullaniciID(), ogrenci.getKayitYili(), ogrenci.getBolum());
	}

	@Override
	public void guncelle(Ogrenci ogrenci) {
		dbopHelper.dosyadanSorguCalistir("ogrenciGuncelle.sql", ogrenci.getKayitYili(), ogrenci.getBolum(), ogrenci.getOgrenciID());
	}

	@Override
	public void sil(int id) {
		dbopHelper.dosyadanSorguCalistir("ogrenciSil.sql", id);
	}
}
