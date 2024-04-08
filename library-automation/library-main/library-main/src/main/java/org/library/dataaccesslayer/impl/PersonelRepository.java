package org.library.dataaccesslayer.impl;

import org.library.dataaccesslayer.api.ARepository;
import org.library.model.Kullanici;
import org.library.model.Personel;

import java.util.List;

public class PersonelRepository extends ARepository<Personel> {

	@Override
	public List<Personel> hepsiniGetir() {
		return dbopHelper.listeSorgusuCalistir("personelListele.sql", rs ->{
			Kullanici kullanici = new Kullanici(rs.getInt("KullaniciID"), rs.getString("KullaniciAdi"), rs.getString("Ad"), rs.getString("Soyad"));
			Personel personel = new Personel(kullanici, rs.getInt("PersonelID"), rs.getString("Pozisyon"));
			return personel;
		});
	}

	@Override
	public Personel getir(int id) {
		return dbopHelper.listeSorgusuCalistir("personelBul.sql", rs ->{
			Kullanici kullanici = new Kullanici();
			kullanici.setKullaniciID(rs.getInt("KullaniciID"));
			kullanici.setKullaniciAdi(rs.getString("KullaniciAdi"));
			kullanici.setAd(rs.getString("Ad"));
			kullanici.setSoyad(rs.getString("Soyad"));
			Personel personel = new Personel();
			personel.setKullanici(kullanici);
			personel.setPozisyon(rs.getString("Pozisyon"));
			return personel;
			}, id).stream().findFirst().orElse(null);
	}

	@Override
	public void ekle(Personel personel) {
		dbopHelper.dosyadanSorguCalistir("personelEkle.sql", personel.getKullanici().getKullaniciID(), personel.getPozisyon());
	}

	@Override
	public void guncelle(Personel personel) {
		dbopHelper.dosyadanSorguCalistir("personelGuncelle.sql", personel.getPozisyon(), personel.getPersonelID());
	}

	@Override
	public void sil(int id) {
		dbopHelper.dosyadanSorguCalistir("personelSil.sql", id);
	}
}

