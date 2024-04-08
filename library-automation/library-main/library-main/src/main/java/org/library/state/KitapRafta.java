package org.library.state;

import org.library.model.Kitap;

public class KitapRafta implements KitapDurum {
	@Override
	public void islemYap(Kitap kitap) {
		System.out.println("Kitap rafta ve ödünç alınabilir.");
		kitap.setDurum("Rafta");
	}
}
