package it.polito.tdp.seriea.model;

import java.util.*;

import org.jgrapht.DirectedGraph;
import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import it.polito.tdp.seriea.db.SerieADAO;

public class Model {
	
	private DirectedGraph<String, DefaultWeightedEdge> grafo;
	private List<Partita> partite;
	SerieADAO dao = new SerieADAO();
	List<String> best;

	public void creaGrafo(int anno) {
		this.grafo = new SimpleDirectedWeightedGraph<>(DefaultWeightedEdge.class);
		
		this.partite = dao.getPartite(anno);
		
		for(Partita p : partite) {
			if(p.getVincitrice().equals("H")) {
				Graphs.addEdgeWithVertices(grafo, p.getSquadraHome(), p.getSquadraFuori(), 1);
				Graphs.addEdgeWithVertices(grafo, p.getSquadraFuori(), p.getSquadraHome(), -1);
			}
			if(p.getVincitrice().equals("A")) {
				Graphs.addEdgeWithVertices(grafo, p.getSquadraHome(), p.getSquadraFuori(), -1);
				Graphs.addEdgeWithVertices(grafo, p.getSquadraFuori(), p.getSquadraHome(), 1);
			}
			
			if(p.getVincitrice().equals("D")) {
				Graphs.addEdgeWithVertices(grafo, p.getSquadraHome(), p.getSquadraFuori(), 0);
				Graphs.addEdgeWithVertices(grafo, p.getSquadraFuori(), p.getSquadraHome(), 0);
			}
			
		}
	}
	
	
	public List<String> soluzioneMigliore(String string){
		this.best = new LinkedList<String>();
		List<String> parziale = new LinkedList<String>();
		parziale.add(string);
		ricorsione(parziale);
		return best;
	}
	
	public void ricorsione(List<String> parziale) {
		
		System.out.println(parziale.toString());
		
		if(parziale.size()>best.size()) {
			best=new LinkedList<String>(parziale);
		}
		
		for(String s : grafo.vertexSet()) {
				List<String> successori = Graphs.successorListOf(grafo, s);
				for(String successore : successori) {
					if(!parziale.contains(successore) && grafo.getEdgeWeight(grafo.getEdge(s, successore))==1) {
						parziale.add(successore);
						ricorsione(parziale);
						parziale.remove(parziale.size()-1);
					}
				}
				
			}
		
		
		
	}

	public DirectedGraph<String, DefaultWeightedEdge> getGrafo() {
		// TODO Auto-generated method stub
		return this.grafo;
	}

}
