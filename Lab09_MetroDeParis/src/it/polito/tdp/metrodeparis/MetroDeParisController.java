package it.polito.tdp.metrodeparis;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.DirectedWeightedMultigraph;

import it.polito.tdp.metrodeparis.model.Fermata;
import it.polito.tdp.metrodeparis.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;

public class MetroDeParisController {
	
	Model model;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<Fermata> cmbBoxPartenza;

    @FXML
    private ComboBox<Fermata> cmbBoxArrivo;

    @FXML
    private Button btnCalcolaPercorso;

    @FXML
    private TextArea txtResult;

    @FXML
    void doCalcolaPercorso(ActionEvent event) {
    	
    	DirectedWeightedMultigraph<Fermata, DefaultWeightedEdge> graph = model.getGrafo();
    	List<DefaultWeightedEdge> percorsoMinimo = model.getPercorsoMinimo(cmbBoxPartenza.getValue(), cmbBoxArrivo.getValue());
    	String risultato = "";
		for(DefaultWeightedEdge dwe : percorsoMinimo) {
			if(graph.getEdgeSource(dwe).getIdFermata()==(graph.getEdgeTarget(dwe)).getIdFermata()) {
				risultato += "Cambio su linea: " + graph.getEdgeTarget(dwe).getLinea() + "\n" + 
			                graph.getEdgeSource(dwe).getNome() + ", linea " + graph.getEdgeSource(dwe).getLinea() + " -> " +
			                graph.getEdgeTarget(dwe).getNome() + ",linea " + graph.getEdgeTarget(dwe).getLinea() + "\n\n";
			}
			
			else
				risultato += graph.getEdgeSource(dwe).getNome() + ", linea " + graph.getEdgeSource(dwe).getLinea() + "\n\n";
		}
		risultato += cmbBoxArrivo.getValue().getNome() + ", linea " + cmbBoxArrivo.getValue().getLinea();
		txtResult.appendText(risultato);	
    }

    @FXML
    void initialize() {
        assert cmbBoxPartenza != null : "fx:id=\"cmbBoxPartenza\" was not injected: check your FXML file 'MetroDeParis.fxml'.";
        assert cmbBoxArrivo != null : "fx:id=\"cmbBoxArrivo\" was not injected: check your FXML file 'MetroDeParis.fxml'.";
        assert btnCalcolaPercorso != null : "fx:id=\"btnCalcolaPercorso\" was not injected: check your FXML file 'MetroDeParis.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'MetroDeParis.fxml'.";

    }

	public void setModel(Model model) {
		this.model = model;
		
		List<Fermata> fermate = model.getFermate();
		cmbBoxPartenza.getItems().addAll(fermate);
		cmbBoxArrivo.getItems().addAll(fermate);
		if (cmbBoxPartenza.getItems().size() > 0)
			cmbBoxPartenza.setValue(model.getFermate().get(0));
		if (cmbBoxArrivo.getItems().size() > 0)
			cmbBoxArrivo.setValue(model.getFermate().get(0));
	}
}
