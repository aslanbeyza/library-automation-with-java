package org.library.model;

import java.util.Date;

public class SistemLog {
	private int logID;
	private Personel personel;
	private Kitap kitap;
	private String islemTipi;
	private Date islemTarihi;

	public SistemLog(int logID, Personel personel, Kitap kitap, String islemTipi, Date islemTarihi) {
		this.logID = logID;
		this.personel = personel;
		this.kitap = kitap;
		this.islemTipi = islemTipi;
		this.islemTarihi = islemTarihi;
	}

	public int getLogID() {
		return logID;
	}

	public void setLogID(int logID) {
		this.logID = logID;
	}

	public Personel getPersonel() {
		return personel;
	}

	public void setPersonel(Personel personel) {
		this.personel = personel;
	}

	public Kitap getKitap() {
		return kitap;
	}

	public void setKitap(Kitap kitap) {
		this.kitap = kitap;
	}

	public String getIslemTipi() {
		return islemTipi;
	}

	public void setIslemTipi(String islemTipi) {
		this.islemTipi = islemTipi;
	}

	public Date getIslemTarihi() {
		return islemTarihi;
	}

	public void setIslemTarihi(Date islemTarihi) {
		this.islemTarihi = islemTarihi;
	}
}
