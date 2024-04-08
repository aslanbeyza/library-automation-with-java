package org.library.dataaccesslayer.api;

import java.util.List;

public interface IRepository<T> {

	List<T> hepsiniGetir();

	T getir(int id);

	void ekle(T entity);

	void guncelle(T entity);

	void sil(int id);
}
