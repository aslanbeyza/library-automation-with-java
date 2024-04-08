package org.library.model;

public class Kullanici {
	private int kullaniciID;
	private String kullaniciAdi;
	private String parola;
	private String ad;
	private String soyad;
	private String email;
	private int tur;

	public Kullanici() {
	}

	public Kullanici(int kullaniciID, String kullaniciAdi, String parola, String ad, String soyad, String email, int tur) {
		this.kullaniciID = kullaniciID;
		this.kullaniciAdi = kullaniciAdi;
		this.parola = parola;
		this.ad = ad;
		this.soyad = soyad;
		this.email = email;
		this.tur = tur;
	}

	public Kullanici(int kullaniciID, String kullaniciAdi,  String ad, String soyad) {
		this.kullaniciID = kullaniciID;
		this.kullaniciAdi = kullaniciAdi;
		this.ad = ad;
		this.soyad = soyad;
	}

	public int getKullaniciID() {
		return kullaniciID;
	}

	public void setKullaniciID(int kullaniciID) {
		this.kullaniciID = kullaniciID;
	}

	public String getKullaniciAdi() {
		return kullaniciAdi;
	}

	public void setKullaniciAdi(String kullaniciAdi) {
		this.kullaniciAdi = kullaniciAdi;
	}

	public String getParola() {
		return parola;
	}

	public void setParola(String parola) {
		this.parola = parola;
	}

	public String getAd() {
		return ad;
	}

	public void setAd(String ad) {
		this.ad = ad;
	}

	public String getSoyad() {
		return soyad;
	}

	public void setSoyad(String soyad) {
		this.soyad = soyad;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getTur() {
		return tur;
	}

	public void setTur(int tur) {
		this.tur = tur;
	}
}
