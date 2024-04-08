module org.library {
	requires javafx.controls;
	requires javafx.fxml;

	requires net.synedra.validatorfx;
	requires org.kordamp.bootstrapfx.core;
	requires java.sql;

	opens org.library.dataaccesslayer.impl;
	opens org.library.dataaccesslayer.api;
	opens org.library.model;
	opens org.library.view;
	opens org.library.helper;
	opens sqlSorgulari;

	exports org.library;
}