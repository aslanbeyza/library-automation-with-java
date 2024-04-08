package org.library.model;

import org.library.observer.KitapObserver;
import org.library.observer.KitapSubject;

import java.util.ArrayList;
import java.util.List;

public class KitapYonetimi implements KitapSubject {
	private List<KitapObserver> observers = new ArrayList<>();

	public void kitapListesiGuncelle(boolean kutuphaneMi) {
		notifyObservers(kutuphaneMi);
	}

	@Override
	public void addObserver(KitapObserver o) {
		observers.add(o);
	}

	@Override
	public void removeObserver(KitapObserver o) {
		observers.remove(o);
	}

	@Override
	public void notifyObservers(boolean kutuphaneMi) {
		for (KitapObserver observer : observers) {
			observer.kutuphaneKitapListesiGuncellendi(kutuphaneMi);
		}
	}
}
