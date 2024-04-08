package org.library.dataaccesslayer.impl;

import org.library.dataaccesslayer.api.ARepository;
import org.library.model.Kitap;
import org.library.model.Kullanici;
import org.library.model.OduncKitap;

import java.util.List;

public class OduncKitapRepository extends ARepository<OduncKitap> {
	public OduncKitap getir(int kullaniciID) {
		return (OduncKitap) dbopHelper.listeSorgusuCalistir("oduncKitapListeleID.sql", rs -> {

			Kullanici kullanici = new Kullanici(rs.getInt("kullaniciid"), rs.getString("KullaniciAdi"), rs.getString("Ad")
					, rs.getString("Soyad"));

			Kitap kitap = new Kitap(rs.getInt("KitapID"),
					rs.getString("Baslik"), rs.getString("Yazar"),
					rs.getString("Tur"), rs.getString("Konu"),
					rs.getBoolean("OduncVerildi"), rs.getDouble("Puan"),rs.getString("Durum"));

			return new OduncKitap(rs.getInt("OduncID"),
					kullanici, kitap,
					rs.getDate("OduncTarihi").toLocalDate(),
					rs.getDate("IadeTarihi") != null ?
							rs.getDate("IadeTarihi").toLocalDate() : null,
					rs.getString("Durum"));
		}, kullaniciID);
	}

	public List<OduncKitap> hepsiniGetir(Kullanici kullanici) {
		return dbopHelper.listeSorgusuCalistir("oduncKitapListeleID.sql", rs -> {

			Kitap kitap = new Kitap(rs.getInt("KitapID"),
					rs.getString("Baslik"), rs.getString("Yazar"),
					rs.getString("Tur"), rs.getString("Konu"),
					rs.getBoolean("OduncVerildi"), rs.getDouble("Puan"), rs.getString("Durum"));

			return new OduncKitap(rs.getInt("OduncID"),
					kullanici, kitap,
					rs.getDate("OduncTarihi").toLocalDate(),
					rs.getDate("IadeTarihi") != null ?
							rs.getDate("IadeTarihi").toLocalDate() : null,
					rs.getString("Durum"));
		}, kullanici.getKullaniciID());
	}

	@Override
	public List<OduncKitap> hepsiniGetir() {
		return dbopHelper.listeSorgusuCalistir("oduncKitapListele.sql", rs -> {
			Kullanici kullanici = new Kullanici(rs.getInt("kullaniciid"), rs.getString("KullaniciAdi"), rs.getString("Ad")
					, rs.getString("Soyad"));

			Kitap kitap = new Kitap(rs.getInt("KitapID"),
					rs.getString("Baslik"), rs.getString("Yazar"),
					rs.getString("Tur"), rs.getString("Konu"),
					rs.getBoolean("OduncVerildi"), rs.getDouble("Puan"), rs.getString("Durum"));

			return new OduncKitap(rs.getInt("OduncID"),
					kullanici, kitap,
					rs.getDate("OduncTarihi").toLocalDate(),
					rs.getDate("IadeTarihi") != null ?
							rs.getDate("IadeTarihi").toLocalDate() : null,
					rs.getString("Durum"));
		});
	}

	@Override
	public void ekle(OduncKitap oduncKitap) {
		// SQL sorgusunu kullanarak kullanıcı kaydet
		dbopHelper.dosyadanSorguCalistir("oduncKitapEkle.sql", oduncKitap.getKullanici().getKullaniciID(), oduncKitap.getKitap().getKitapID(), oduncKitap.getOduncTarihi(), oduncKitap.getIadeTarihi(), oduncKitap.getOduncVerildi());
	}

	@Override
	public void guncelle(OduncKitap oduncKitap) {
		// SQL sorgusunu kullanarak kullanıcı güncelle
		dbopHelper.dosyadanSorguCalistir("oduncKitapGuncelle.sql", oduncKitap.getKullanici().getKullaniciID(), oduncKitap.getKitap().getKitapID(), oduncKitap.getOduncTarihi(), oduncKitap.getIadeTarihi(), oduncKitap.getOduncVerildi(), oduncKitap.getOduncID());
	}

	@Override
	public void sil(int id) {
		// SQL sorgusunu kullanarak kullanıcı sil
		dbopHelper.dosyadanSorguCalistir("oduncKitapSil.sql", id);
	}

}
