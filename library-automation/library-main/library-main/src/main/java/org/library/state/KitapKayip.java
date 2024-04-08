package org.library.state;

import org.library.model.Kitap;

public class KitapKayip implements KitapDurum {
	@Override
	public void islemYap(Kitap kitap) {
		System.out.println("Kitap kayıp.");
		kitap.setDurum("Kayıp");
	}
}
