package controller;

import java.util.ArrayList;

import model.Promo;
import repository.PromoDA;

public class PromoHandler {

	// Data Access Object Untuk Mengambil Data Promo Dari Database
	private PromoDA promoDA = new PromoDA();
	
	// Mendapatkan Semua Promo Yang Tersedia
	public ArrayList<Promo> getAllPromos() {
		return promoDA.getAllPromos();
	}
	
	// Mendapatkan Satu Promo Berdasarkan Kode Promo
	public Promo getPromo(String code) {
		return promoDA.getPromo(code);
	}
}
