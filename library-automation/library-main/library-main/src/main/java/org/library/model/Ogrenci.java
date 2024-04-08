package org.library.model;

public class Ogrenci {
	private int ogrenciID;
	private Kullanici kullanici;
	private int kayitYili;
	private String bolum;

	public Ogrenci() {
	}

	public Ogrenci(Kullanici kullanici, int ogrenciID, int kayitYili, String bolum) {
		this.ogrenciID = ogrenciID;
		this.kullanici = kullanici;
		this.kayitYili = kayitYili;
		this.bolum = bolum;
	}

	public Kullanici getKullanici() {
		return kullanici;
	}

	public void setKullanici(Kullanici kullanici) {
		this.kullanici = kullanici;
	}

	public int getOgrenciID() {
		return ogrenciID;
	}

	public void setOgrenciID(int ogrenciID) {
		this.ogrenciID = ogrenciID;
	}

	public int getKayitYili() {
		return kayitYili;
	}

	public void setKayitYili(int kayitYili) {
		this.kayitYili = kayitYili;
	}

	public String getBolum() {
		return bolum;
	}

	public void setBolum(String bolum) {
		this.bolum = bolum;
	}
}
