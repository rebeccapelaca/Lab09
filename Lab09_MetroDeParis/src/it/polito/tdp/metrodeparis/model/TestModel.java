package it.polito.tdp.metrodeparis.model;

import java.util.List;

import it.polito.tdp.metrodeparis.dao.MetroDAO;

public class TestModel {

	public static void main(String[] args) {
		
		Model model = new Model();
		MetroDAO metroDAO = new MetroDAO();
		System.out.println("TODO: write a Model class and test it!");
		List<Fermata> fermate = metroDAO.getVertici();
		for(Fermata f : fermate)
			System.out.println(f);
		model.creaGrafo();		
		System.out.println(model.getPercorsoMinimo(fermate.get(13), fermate.get(18)));
	}
}
