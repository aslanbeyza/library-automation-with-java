package org.library.model;

public class OgretimUyesi {
	private int ogretimUyesiID;
	private Kullanici kullanici;
	private String bolum;
	private String unvan;

	public OgretimUyesi() {
	}

	public OgretimUyesi(Kullanici kullanici, int ogretimUyesiID, String bolum, String unvan) {
		this.kullanici = kullanici;
		this.ogretimUyesiID = ogretimUyesiID;
		this.bolum = bolum;
		this.unvan = unvan;
	}

	public Kullanici getKullanici() {
		return kullanici;
	}

	public void setKullanici(Kullanici kullanici) {
		this.kullanici = kullanici;
	}

	public int getOgretimUyesiID() {
		return ogretimUyesiID;
	}

	public void setOgretimUyesiID(int ogretimUyesiID) {
		this.ogretimUyesiID = ogretimUyesiID;
	}

	public String getBolum() {
		return bolum;
	}

	public void setBolum(String bolum) {
		this.bolum = bolum;
	}

	public String getUnvan() {
		return unvan;
	}

	public void setUnvan(String unvan) {
		this.unvan = unvan;
	}
}