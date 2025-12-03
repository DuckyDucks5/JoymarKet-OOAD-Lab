package controller;

import java.util.ArrayList;

import model.Promo;
import repository.PromoDA;

public class PromoHandler {

	private PromoDA promoDA = new PromoDA();
	
	public ArrayList<Promo> getAllPromos(){
		return promoDA.getAllPromos();
	}
	
	public Promo getPromo(String code) {
		return promoDA.getPromo(code);
	}
}
