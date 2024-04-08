package org.library.dataaccesslayer.impl;

import org.library.dataaccesslayer.api.ARepository;
import org.library.model.Kitap;

import java.util.List;

public class KitapRepository extends ARepository<Kitap> {

	@Override
	public List<Kitap> hepsiniGetir() {
		return dbopHelper.listeSorgusuCalistir("kitapListele.sql", rs -> new Kitap(rs.getInt("KitapID"),
				rs.getString("Baslik"), rs.getString("Yazar"), rs.getString("Tur"),
				rs.getString("Konu"), rs.getBoolean("OduncVerildi"), rs.getDouble("Puan"), rs.getString("Durum")));
	}

	@Override
	public Kitap getir(int id) {
		return dbopHelper.listeSorgusuCalistir("kitapBul.sql", rs -> new Kitap(rs.getInt("KitapID"),
				rs.getString("Baslik"), rs.getString("Yazar"), rs.getString("Tur"),
				rs.getString("Konu"), rs.getBoolean("OduncVerildi"), rs.getDouble("Puan"), rs.getString("Durum")), id).stream().findFirst().orElse(null);
	}

	@Override
	public void ekle(Kitap kitap) {
		dbopHelper.dosyadanSorguCalistir("kitapEkle.sql", kitap.getBaslik(), kitap.getYazar(), kitap.getTur(), kitap.getKonu(), kitap.isOduncVerildi(), kitap.getPuan(), kitap.getDurum());
	}

	@Override
	public void guncelle(Kitap kitap) {
		dbopHelper.dosyadanSorguCalistir("kitapGuncelle.sql", kitap.getBaslik(), kitap.getYazar(), kitap.getTur(), kitap.getKonu(), kitap.isOduncVerildi(), kitap.getPuan(), kitap.getDurum(), kitap.getKitapID());
	}

	@Override
	public void sil(int id) {
		dbopHelper.dosyadanSorguCalistir("kitapSil.sql", id);
	}
}
