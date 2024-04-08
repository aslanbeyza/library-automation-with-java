package org.library.dataaccesslayer.api;

import org.library.helper.DBOperationHelper;

import java.util.List;

public abstract class ARepository<T> implements IRepository<T> {
	protected DBOperationHelper dbopHelper;

	public ARepository() {
		this.dbopHelper = DBOperationHelper.getInstance();
	}

	public abstract List<T> hepsiniGetir();

	public abstract T getir(int id);

	public abstract void ekle(T entity);

	public abstract void guncelle(T entity);

	public abstract void sil(int id);
}
