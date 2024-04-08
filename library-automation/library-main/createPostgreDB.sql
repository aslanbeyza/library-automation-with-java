CREATE TABLE kitap (
	kitapid serial4 NOT NULL,
	baslik varchar(255) NULL,
	yazar varchar(255) NULL,
	tur varchar(255) NULL,
	konu varchar(255) NULL,
	durum varchar(50) NULL,
	puan numeric(3, 2) NULL,
	oduncverildi bool NULL,
	CONSTRAINT kitap_pkey PRIMARY KEY (kitapid),
	CONSTRAINT kitap_puan_check CHECK (((puan >= (0)::numeric) AND (puan <= (5)::numeric)))
);

CREATE TABLE kullanici (
	kullaniciid serial4 NOT NULL,
	kullaniciadi varchar(50) NOT NULL,
	parola varchar(255) NOT NULL,
	ad varchar(100) NULL,
	soyad varchar(100) NULL,
	email varchar(100) NULL,
	tur int4 NOT NULL,
	CONSTRAINT kullanici_email_key UNIQUE (email),
	CONSTRAINT kullanici_pkey PRIMARY KEY (kullaniciid)
);

CREATE TABLE odunckitap (
	oduncid serial4 NOT NULL,
	kullaniciid int4 NULL,
	kitapid int4 NULL,
	odunctarihi date NULL,
	iadetarihi date NULL,
	durum varchar(50) NULL,
	CONSTRAINT odunckitap_pkey PRIMARY KEY (oduncid),
	CONSTRAINT odunckitap_kitapid_fkey FOREIGN KEY (kitapid) REFERENCES kitap(kitapid) ON DELETE CASCADE,
	CONSTRAINT odunckitap_kullaniciid_fkey FOREIGN KEY (kullaniciid) REFERENCES kullanici(kullaniciid) ON DELETE CASCADE
);

CREATE TABLE ogrenci (
	ogrenciid serial4 NOT NULL,
	kullaniciid int NOT NULL,
	kayityili int4 NULL,
	bolum varchar(100) NULL,
	CONSTRAINT ogrenci_pkey PRIMARY KEY (ogrenciid),
	FOREIGN KEY (kullaniciid) REFERENCES kullanici(kullaniciid) ON DELETE CASCADE
);

CREATE TABLE ogretimuyesi (
	ogretimuyesiid serial4 NOT NULL,
	kullaniciid int NOT NULL,
	bolum varchar(100) NULL,
	unvan varchar(100) NULL,
	CONSTRAINT ogretimuyesi_pkey PRIMARY KEY (ogretimuyesiid),
	FOREIGN KEY (kullaniciid) REFERENCES kullanici(kullaniciid) ON DELETE CASCADE
);

CREATE TABLE personel (
	personelid serial4 NOT NULL,
	kullaniciid int NOT NULL,
	pozisyon varchar(100) NULL,
	CONSTRAINT personel_pkey PRIMARY KEY (personelid),
	FOREIGN KEY (kullaniciid) REFERENCES kullanici(kullaniciid) ON DELETE CASCADE
);