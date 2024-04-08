package org.library.model;


public class Personel {
	private int personelID;
	private Kullanici kullanici;
	private String pozisyon;

	public Personel() {
	}
	public Personel(Kullanici kullanici, int personelID, String pozisyon) {
		this.kullanici = kullanici;
		this.personelID = personelID;
		this.pozisyon = pozisyon;
	}

	public Kullanici getKullanici() {
		return kullanici;
	}

	public void setKullanici(Kullanici kullanici) {
		this.kullanici = kullanici;
	}

	public int getPersonelID() {
		return personelID;
	}

	public void setPersonelID(int personelID) {
		this.personelID = personelID;
	}

	public String getPozisyon() {
		return pozisyon;
	}

	public void setPozisyon(String pozisyon) {
		this.pozisyon = pozisyon;
	}
}
