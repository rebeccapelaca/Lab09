package it.polito.tdp.metrodeparis.dao;

import java.util.List;

import it.polito.tdp.metrodeparis.model.CoppiaFermate;
import it.polito.tdp.metrodeparis.model.Fermata;

public class TestDAO {

	public static void main(String[] args) {
		
		MetroDAO metroDAO = new MetroDAO();

		List<Fermata> allFermate = metroDAO.getAllFermate();
		System.out.println("LISTA FERMATE DA INSERIRE NELLE COMBOBOX : \n");
		for(Fermata f : allFermate)
			System.out.println(f.getNome() + ", id " + f.getIdFermata() + "\n");
		System.out.println("///////////////////////////////////////////////////////////////////////////////////////// \n");
		System.out.println("LISTA VERTICI DEL GRAFO PESATO E ORIENTATO : \n ");
		List<Fermata> fermate = metroDAO.getVertici();
		for(Fermata f : fermate)
			System.out.println(f.getNome() + ", linea " + f.getLinea() + "\n");
		System.out.println("//////////////////////////////////////////////////////////////////////////////////////// \n");
		System.out.println("LISTA DI CONNESSIONI : \n");
		List<CoppiaFermate> connessioni = metroDAO.listaConnessioni();
		for(CoppiaFermate cf : connessioni)
			System.out.println(cf.getIdConnessione() + ", linea " + cf.getLinea() + ", " + cf.getFermata1() + " --> " + cf.getFermata2());
	}
}
