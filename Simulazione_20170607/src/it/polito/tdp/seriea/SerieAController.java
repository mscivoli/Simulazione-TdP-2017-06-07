/**
 * Sample Skeleton for 'SerieA.fxml' Controller Class
 */

package it.polito.tdp.seriea;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import org.jgrapht.DirectedGraph;
import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;

import it.polito.tdp.seriea.db.SerieADAO;
import it.polito.tdp.seriea.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;

public class SerieAController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="boxSeason"
    private ChoiceBox<Integer> boxSeason; // Value injected by FXMLLoader

    @FXML // fx:id="boxTeam"
    private ChoiceBox<String> boxTeam; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

	private Model model;

    @FXML
    void handleCarica(ActionEvent event) {
    	
    	if(boxSeason.getValue()!=null) {
    		int anno = boxSeason.getValue();
    		model.creaGrafo(anno);
    		
    		txtResult.appendText("GRAFO CREATO\n\n");
    		txtResult.appendText(model.getGrafo().toString());
    		
    		DirectedGraph<String, DefaultWeightedEdge> grafo = model.getGrafo();
    		
    		for(String s : grafo.vertexSet()) {
    			
    			Set<DefaultWeightedEdge> archi = grafo.edgesOf(s);
    			for(DefaultWeightedEdge e : archi) {
    				txtResult.appendText(grafo.getEdgeTarget(e)+" -> "+grafo.getEdgeSource(e)+" : "+grafo.getEdgeWeight(e)+"\n\n");
    			}
    			
    			Set<DefaultWeightedEdge> uscenti = grafo.outgoingEdgesOf(s);
    			//Set<DefaultWeightedEdge> entranti = grafo.incomingEdgesOf(s);
    			int peso = 0;
    			for(DefaultWeightedEdge e : uscenti) {
    				if(grafo.getEdgeWeight(e)>0) {
    					peso+=3;
    				}
    				if(grafo.getEdgeWeight(e)<0) {
    					peso--;
    				}
    			}
//    			for(DefaultWeightedEdge e: entranti) {
//    				peso-=1;
//    			}
    			
    			txtResult.appendText(s+" CON "+peso+"\n\n\n");
    			boxTeam.getItems().clear();
    			boxTeam.getItems().addAll(grafo.vertexSet());
    		}
    	}

    }

    @FXML
    void handleDomino(ActionEvent event) {
    	txtResult.clear();
    	String inizio = boxTeam.getValue();
    	List<String> soluzione = model.soluzioneMigliore(inizio);
    	for(String s : soluzione) {
    		txtResult.appendText(s);
    	}

    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert boxSeason != null : "fx:id=\"boxSeason\" was not injected: check your FXML file 'SerieA.fxml'.";
        assert boxTeam != null : "fx:id=\"boxTeam\" was not injected: check your FXML file 'SerieA.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'SerieA.fxml'.";
    }

	public void setModel(Model model) {
		this.model = model;
		
		SerieADAO dao = new SerieADAO();
		
		boxSeason.getItems().clear();
		boxSeason.getItems().addAll(dao.listSeasons());
		
	}
}
