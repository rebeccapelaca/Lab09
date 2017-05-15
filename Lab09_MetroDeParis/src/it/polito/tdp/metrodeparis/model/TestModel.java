package it.polito.tdp.metrodeparis.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.DirectedWeightedMultigraph;

public class TestModel {

	public static void main(String[] args) {
		
		Model model = new Model();
		System.out.println("TODO: write a Model class and test it!");
		Set<Fermata> fermate = model.creaGrafo();
		List<Fermata> listaFermate = new ArrayList<Fermata>();
		System.out.println("STAMPO TUTTI I VERTICI DEL GRAFO : \n");
		for(Fermata f : fermate) {
			listaFermate.add(f);
			System.out.println(f + ", linea " + f.getLinea() + "\n");
		}
		DirectedWeightedMultigraph<Fermata, DefaultWeightedEdge> graph = model.getGrafo();
		List<DefaultWeightedEdge> percorsoMinimo = model.getPercorsoMinimo(listaFermate.get(13), listaFermate.get(19));
		String risultato = "";
		System.out.println("///////////////////////////////////////////////////////////////////////////////// \n");
		System.out.println("STAMPO IL PERCORSO MINIMO : " + listaFermate.get(13) + " --> " + listaFermate.get(19) + "\n") ;
		for(DefaultWeightedEdge dwe : percorsoMinimo) {
			if(graph.getEdgeSource(dwe).getIdFermata()==(graph.getEdgeTarget(dwe)).getIdFermata()) {
				risultato += "Cambio su linea: " + graph.getEdgeTarget(dwe).getLinea() + "\n" + 
			                graph.getEdgeSource(dwe) + ", linea " + graph.getEdgeSource(dwe).getLinea() + " -> " +
			                graph.getEdgeTarget(dwe) + ",linea " + graph.getEdgeTarget(dwe).getLinea() + "\n\n";
			}
			
			else
				risultato += graph.getEdgeSource(dwe) + ", linea " + graph.getEdgeSource(dwe).getLinea() + "\n\n";
		}
		risultato += listaFermate.get(19) + ", linea " + listaFermate.get(19).getLinea();
		System.out.println(risultato);	
	}
}
