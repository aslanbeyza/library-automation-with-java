package org.library.state;

import org.library.model.Kitap;

public class KitapContext {
	private Kitap kitap;
	private KitapDurum durum;

	public KitapContext(Kitap kitap) {
		this.kitap = kitap;
		durum = null;
	}

	public void setDurum(KitapDurum durum) {
		this.durum = durum;
	}

	public KitapDurum getDurum() {
		return durum;
	}

	public void islemYap() {
		durum.islemYap(kitap);
	}
}