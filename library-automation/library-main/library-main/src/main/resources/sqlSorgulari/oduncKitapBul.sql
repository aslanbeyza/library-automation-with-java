SELECT Kitap.KitapID, Kitap.Baslik, Kitap.Yazar, Kitap.Tur, Kitap.Konu, Kitap.Durum, Kitap.Puan,
       OduncKitap.OduncTarihi, OduncKitap.IadeTarihi, OduncKitap.Durum AS OduncDurum
FROM Kitap
         INNER JOIN OduncKitap ON Kitap.KitapID = OduncKitap.KitapID
WHERE OduncKitap.KullaniciID = ?;