package org.library.dataaccesslayer.impl;

import org.library.dataaccesslayer.api.ARepository;
import org.library.model.Kullanici;
import org.library.model.OgretimUyesi;

import java.util.List;

public class OgretimUyesiRepository extends ARepository<OgretimUyesi> {

	@Override
	public List<OgretimUyesi> hepsiniGetir() {
		return dbopHelper.listeSorgusuCalistir("ogretimUyesiListele.sql", rs -> {
			Kullanici kullanici = new Kullanici(rs.getInt("KullaniciID"), rs.getString("KullaniciAdi"), rs.getString("Ad"), rs.getString("Soyad"));
			OgretimUyesi ogretimUyesi = new OgretimUyesi(kullanici, rs.getInt("OgretimUyesiID"), rs.getString("Bolum"), rs.getString("Unvan"));
			return ogretimUyesi;
		});
	}

	@Override
	public OgretimUyesi getir(int id) {
		return dbopHelper.listeSorgusuCalistir("OgretimUyesiBul.sql", rs -> {
			Kullanici kullanici = new Kullanici();
			kullanici.setKullaniciID(rs.getInt("KullaniciID"));
			kullanici.setKullaniciAdi(rs.getString("KullaniciAdi"));
			kullanici.setAd(rs.getString("Ad"));
			kullanici.setSoyad(rs.getString("Soyad"));
			OgretimUyesi ogretimUyesi = new OgretimUyesi();
			ogretimUyesi.setKullanici(kullanici);
			ogretimUyesi.setBolum(rs.getString("Bolum"));
			ogretimUyesi.setUnvan(rs.getString("Unvan"));
			return ogretimUyesi;
		}, id).stream().findFirst().orElse(null);
	}

	@Override
	public void ekle(OgretimUyesi ogretimUyesi) {
		dbopHelper.dosyadanSorguCalistir("OgretimUyesiEkle.sql",
				ogretimUyesi.getKullanici().getKullaniciID(), ogretimUyesi.getBolum(), ogretimUyesi.getUnvan());
	}

	@Override
	public void guncelle(OgretimUyesi ogretimUyesi) {
		dbopHelper.dosyadanSorguCalistir("OgretimUyesiGuncelle.sql",
				ogretimUyesi.getBolum(), ogretimUyesi.getUnvan());
	}

	@Override
	public void sil(int id) {
		dbopHelper.dosyadanSorguCalistir("OgretimUyesiSil.sql", id);
	}
}
