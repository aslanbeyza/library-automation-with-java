package org.library.model;

import org.library.state.KitapContext;
import org.library.state.KitapDurum;
import org.library.state.KitapRafta;

import java.util.Objects;

public class Kitap {

	private int kitapID;
	private String baslik;
	private String yazar;
	private String tur;
	private String konu;
	private boolean oduncVerildi;
	private Double puan;

	private String durum;
	private KitapContext durumContext;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Kitap kitap = (Kitap) o;
		return kitapID == kitap.kitapID && oduncVerildi == kitap.oduncVerildi && baslik.equals(kitap.baslik) && yazar.equals(kitap.yazar) && tur.equals(kitap.tur) && konu.equals(kitap.konu) && puan.equals(kitap.puan);
	}

	@Override
	public int hashCode() {
		return Objects.hash(kitapID, baslik, yazar, tur, konu, oduncVerildi, puan);
	}

	public Kitap(int kitapID, String baslik, String yazar, String tur, String konu, boolean oduncVerildi, Double puan, String durum) {
		this.kitapID = kitapID;
		this.baslik = baslik;
		this.yazar = yazar;
		this.tur = tur;
		this.konu = konu;
		this.oduncVerildi = oduncVerildi;
		this.puan = puan;
		this.durum = durum;
		this.durumContext = new KitapContext(this);
		this.durumContext.setDurum(new KitapRafta());
	}

	public int getKitapID() {
		return kitapID;
	}

	public void setKitapID(int kitapID) {
		this.kitapID = kitapID;
	}

	public String getBaslik() {
		return baslik;
	}

	public void setBaslik(String baslik) {
		this.baslik = baslik;
	}

	public String getYazar() {
		return yazar;
	}

	public void setYazar(String yazar) {
		this.yazar = yazar;
	}

	public String getTur() {
		return tur;
	}

	public void setTur(String tur) {
		this.tur = tur;
	}

	public String getKonu() {
		return konu;
	}

	public void setKonu(String konu) {
		this.konu = konu;
	}

	public boolean isOduncVerildi() {
		return oduncVerildi;
	}

	public void setOduncVerildi(boolean oduncVerildi) {
		this.oduncVerildi = oduncVerildi;
	}

	public Double getPuan() {
		return puan;
	}

	public void setPuan(Double puan) {
		this.puan = puan;
	}

	public String getDurum() {
		return durum;
	}

	public void setDurum(String durum) {
		this.durum = durum;
	}

	public void setDurum(KitapDurum durum) {
		durumContext.setDurum(durum);
	}

	public void durumDegistir() {
		durumContext.islemYap();
	}
}
