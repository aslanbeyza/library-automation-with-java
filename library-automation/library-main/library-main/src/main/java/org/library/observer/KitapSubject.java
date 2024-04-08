package org.library.observer;

public interface KitapSubject {
	void addObserver(KitapObserver o);
	void removeObserver(KitapObserver o);
	void notifyObservers(boolean kutuphaneMi);
}
