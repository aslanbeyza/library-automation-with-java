package org.library.model;

import java.time.LocalDate;

public class OduncKitap {
	private int oduncID;
	private Kullanici kullanici;
	private Kitap kitap;
	private LocalDate oduncTarihi;
	private LocalDate iadeTarihi;
	private String durum;

	public OduncKitap() {
	}
	public OduncKitap(int oduncID, Kullanici kullanici, Kitap kitap, LocalDate oduncTarihi, LocalDate iadeTarihi, String durum) {
		this.oduncID = oduncID;
		this.kullanici = kullanici;
		this.kitap = kitap;
		this.oduncTarihi = oduncTarihi;
		this.iadeTarihi = iadeTarihi;
		this.durum = durum;
	}

	public int getOduncID() {
		return oduncID;
	}

	public void setOduncID(int oduncID) {
		this.oduncID = oduncID;
	}

	public Kullanici getKullanici() {
		return kullanici;
	}

	public void setKullanici(Kullanici kullanici) {
		this.kullanici = kullanici;
	}

	public Kitap getKitap() {
		return kitap;
	}

	public void setKitap(Kitap kitap) {
		this.kitap = kitap;
	}

	public LocalDate getOduncTarihi() {
		return oduncTarihi;
	}

	public void setOduncTarihi(LocalDate oduncTarihi) {
		this.oduncTarihi = oduncTarihi;
	}

	public LocalDate getIadeTarihi() {
		return iadeTarihi;
	}

	public void setIadeTarihi(LocalDate iadeTarihi) {
		this.iadeTarihi = iadeTarihi;
	}

	public String getOduncVerildi() {
		return durum;
	}

	public void setOduncVerildi(String oduncVerildi) {
		this.durum = oduncVerildi;
	}
}