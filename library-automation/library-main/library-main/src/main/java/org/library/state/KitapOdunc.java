package org.library.state;

import org.library.model.Kitap;

public class KitapOdunc implements KitapDurum {
	@Override
	public void islemYap(Kitap kitap) {
		System.out.println("Kitap ödünç alındı.");
		kitap.setDurum("Ödünç Verildi");
	}
}
