package org.library.dataaccesslayer.impl;

import org.library.dataaccesslayer.api.ARepository;
import org.library.model.Kullanici;

import java.util.List;

public class KullaniciRepository extends ARepository<Kullanici> {

	@Override
	public List<Kullanici> hepsiniGetir() {
		// SQL sorgusunu kullanarak tüm kullanıcıları getir
		return dbopHelper.listeSorgusuCalistir("kullaniciListele.sql", rs -> {
			int id = rs.getInt("KullaniciID");
			String kullaniciAdi = rs.getString("KullaniciAdi");
			String parola = rs.getString("Parola");
			String ad = rs.getString("Ad");
			String soyad = rs.getString("Soyad");
			String email = rs.getString("Email");
			int tur = rs.getInt("Tur");
			return new Kullanici(id, kullaniciAdi, parola, ad, soyad, email, tur);
		});
	}

	@Override
	public Kullanici getir(int id) {
		// SQL sorgusunu kullanarak id'ye göre kullanıcı getir
		return dbopHelper.listeSorgusuCalistir("kullaniciBul.sql", rs -> new Kullanici(rs.getInt("KullaniciID"), rs.getString("KullaniciAdi"), rs.getString("Parola"), rs.getString("Ad"), rs.getString("Soyad"), rs.getString("Email"), rs.getInt("Tur")), id).stream().findFirst().orElse(null);
	}

	@Override
	public void ekle(Kullanici kullanici) {
		// SQL sorgusunu kullanarak kullanıcı kaydet
		dbopHelper.dosyadanSorguCalistir("kullaniciEkle.sql", kullanici.getKullaniciAdi(), kullanici.getParola(), kullanici.getAd(), kullanici.getSoyad(), kullanici.getEmail(), kullanici.getEmail(), kullanici.getTur());
	}

	public int ekleReturn(Kullanici kullanici) {
		// SQL sorgusunu kullanarak kullanıcı kaydet
		return dbopHelper.listeSorgusuCalistir("kullaniciEkle.sql", rs -> Integer.parseInt(String.valueOf(rs.getInt("KullaniciID"))), kullanici.getKullaniciAdi(), kullanici.getParola(), kullanici.getAd(), kullanici.getSoyad(), kullanici.getEmail(), kullanici.getTur()).stream().findFirst().orElse(null);
	}

	@Override
	public void guncelle(Kullanici kullanici) {
		// SQL sorgusunu kullanarak kullanıcı güncelle
		try {
			dbopHelper.dosyadanSorguCalistir("kullaniciGuncelle.sql", kullanici.getKullaniciAdi(), kullanici.getParola(), kullanici.getAd(), kullanici.getSoyad(), kullanici.getEmail(), kullanici.getTur(), kullanici.getKullaniciID());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	@Override
	public void sil(int id) {
		// SQL sorgusunu kullanarak kullanıcı sil
		dbopHelper.dosyadanSorguCalistir("kullaniciSil.sql", id);
	}

	public Kullanici girisYap(String kullaniciAdi, String parola) {
		// SQL sorgusunu kullanarak kullanıcı girişi
		return dbopHelper.listeSorgusuCalistir("kullaniciGiris.sql", rs -> new Kullanici(rs.getInt("KullaniciID"), rs.getString("KullaniciAdi"), rs.getString("Parola"), rs.getString("Ad"), rs.getString("Soyad"), rs.getString("Email"), rs.getInt("Tur")), kullaniciAdi, parola).stream().findFirst().orElse(null);
	}
}
