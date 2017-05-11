package it.polito.tdp.metrodeparis.model;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import org.jgrapht.Graphs;
import org.jgrapht.alg.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.DirectedWeightedMultigraph;


import it.polito.tdp.metrodeparis.dao.MetroDAO;

public class Model {

	private MetroDAO metroDAO;
	private DirectedWeightedMultigraph<Fermata, DefaultWeightedEdge> graph;
	private List<Fermata> fermate;
	List<Fermata> camminoMin;

	public Model() {

		this.metroDAO = new MetroDAO();
		this.camminoMin = new ArrayList<Fermata>();
	}

	public List<Fermata> getFermate() {
		if (this.fermate == null) {
			this.fermate = metroDAO.getVertici();
		}
		return this.fermate;
	}
	
	public List<Fermata> getAllFermate() {
		
		List<Fermata> allFermate = metroDAO.getAllFermate();
		return allFermate;
	}

	public void creaGrafo() {
		
		List<CoppiaFermate> archiDaVisitare= metroDAO.listaCoppieFermateCollegate();
		List<CoppiaFermate> archiVisitati = new ArrayList<CoppiaFermate>();
		DefaultWeightedEdge e1;
		DefaultWeightedEdge e2;
		DefaultWeightedEdge e3;
		DefaultWeightedEdge e4;
		
		this.graph = new DirectedWeightedMultigraph<>(DefaultWeightedEdge.class);

		Graphs.addAllVertices(graph, this.getFermate());

		for (CoppiaFermate cf1 : archiDaVisitare) {
			for(CoppiaFermate cf2 : archiDaVisitare) {
				if(cf1.equals(cf2)) {
					
					/*
					 *  cf1 : Px   ---> Dx  LINEA X
					 *        | | 
					 * in giù | | in sù
					 *        | |     
					 *  cf2 : Py   ---> Dy  LINEA Y
					 */
					
					// aggiungo l'arco da Px (LINEA X) a Py (LINEA Y), con peso uguale all'intervallo della LINEA Y
					e1 = graph.addEdge(cf1.getFermata1(), cf2.getFermata1());
					graph.setEdgeWeight(e1, metroDAO.getPeso(cf2.getId_linea()));
					
					// aggiungo l'arco da Py (LINEA Y) a Px (LINEA X), con peso uguale all'intervallo della LINEA X
					e2 = graph.addEdge(cf2.getFermata1(), cf1.getFermata1());
					graph.setEdgeWeight(e2, metroDAO.getPeso(cf1.getId_linea()));
					
					// aggiungo l'arco Px (LINEA X) a Dx (LINEA X), con peso uguale al tempo di percorrenza LINEA X
					e3 = graph.addEdge(cf1.getFermata1(), cf1.getFermata2());
					graph.setEdgeWeight(e3, metroDAO.getTempo(cf1.getFermata1(),cf1.getFermata2(), cf1.getId_linea()));
					
					// aggiungo l'arco da Py (LINEA Y) a Dy (LINEA Y), con peso uguale al tempo di percorrenza LINEA Y
					e4 = graph.addEdge(cf2.getFermata1(), cf2.getFermata2());
					graph.setEdgeWeight(e4, metroDAO.getTempo(cf2.getFermata1(),cf2.getFermata2(), cf2.getId_linea()));	
					archiVisitati.add(cf2);
					archiDaVisitare.remove(cf2);	
				}
			}
			archiVisitati.add(cf1);
			archiDaVisitare.remove(cf1);
		}
	}
	
	public List<Fermata> getPercorsoMinimo(Fermata partenza, Fermata arrivo) {
		
		DijkstraShortestPath<Fermata, DefaultWeightedEdge> camminoMinimo = new DijkstraShortestPath<Fermata, DefaultWeightedEdge>(graph, partenza, arrivo);
		
		for(DefaultWeightedEdge d : camminoMinimo.getPath().getEdgeList()) {
			if(!camminoMin.contains(graph.getEdgeSource(d)))
				camminoMin.add(graph.getEdgeSource(d));
			if(!camminoMin.contains(graph.getEdgeTarget(d)))
				camminoMin.add(graph.getEdgeTarget(d));
		}

		return camminoMin;
			
	}
	
	public Time calcolaTempoTotale(Fermata partenza, Fermata arrivo) { 
		
		Long tempoTotale;
		
		DijkstraShortestPath<Fermata, DefaultWeightedEdge> camminoMinimo = new DijkstraShortestPath<Fermata, DefaultWeightedEdge>(graph, partenza, arrivo);
		
		tempoTotale = (long) ((camminoMinimo.getPathLength()/3600)*1000) + (camminoMin.size()*30*1000);
		Time result = new Time(tempoTotale);
		
		return result;	
	}
}
