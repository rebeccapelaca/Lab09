package it.polito.tdp.metrodeparis.model;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.jgrapht.Graphs;
import org.jgrapht.alg.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.DirectedWeightedMultigraph;

import com.javadocmd.simplelatlng.LatLngTool;
import com.javadocmd.simplelatlng.util.LengthUnit;

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
	
	public DirectedWeightedMultigraph<Fermata, DefaultWeightedEdge> getGrafo() {
		if(this.graph==null) {
			this.creaGrafo();
		}
		return this.graph ;
	}

	public Set<Fermata> creaGrafo() {
		
		this.graph = new DirectedWeightedMultigraph<>(DefaultWeightedEdge.class);

		Graphs.addAllVertices(graph, this.getFermate());
		
		for(Fermata f1 : graph.vertexSet())
			for(Fermata f2 : graph.vertexSet())
				if(f1.getIdFermata()==f2.getIdFermata() && f1.getLinea()!= f2.getLinea()) {
					DefaultWeightedEdge e = graph.addEdge(f1, f2);
					graph.setEdgeWeight(e, f2.getLinea().getIntervallo());
				}
		
		for(CoppiaFermate cf : metroDAO.listaConnessioni()) {
			DefaultWeightedEdge e = graph.addEdge(cf.getFermata1(), cf.getFermata2());
			graph.setEdgeWeight(e, this.getTempo(cf.getFermata1(), cf.getFermata2(), cf.getLinea()));
		}
		return graph.vertexSet();
	}
	
	public double getTempo(Fermata f1, Fermata f2, Linea linea) {
		
		int velocita = linea.getVelocita(); 
		double tempo ;
		double spazio;
		
		spazio = LatLngTool.distance(f1.getCoords(), f2.getCoords(), LengthUnit.KILOMETER);
		
		tempo = spazio/velocita;
		
		return tempo;
	}
	
	public List<DefaultWeightedEdge> getPercorsoMinimo(Fermata partenza, Fermata arrivo) {
		
		DijkstraShortestPath<Fermata, DefaultWeightedEdge> camminoMinimo = new DijkstraShortestPath<Fermata, DefaultWeightedEdge>(this.getGrafo(), partenza, arrivo);
		
		List<DefaultWeightedEdge> percorsoMinimo = camminoMinimo.getPath().getEdgeList();
	
		return percorsoMinimo;
			
	}
	
	public Time calcolaTempoTotale(Fermata partenza, Fermata arrivo) { 
		
		Long tempoTotale;
		
		DijkstraShortestPath<Fermata, DefaultWeightedEdge> camminoMinimo = new DijkstraShortestPath<Fermata, DefaultWeightedEdge>(this.getGrafo(), partenza, arrivo);
		
		tempoTotale = (long) ((camminoMinimo.getPathLength()/3600)*1000) + (camminoMin.size()*30*1000);
		Time result = new Time(tempoTotale);
		
		return result;	
	}
}
