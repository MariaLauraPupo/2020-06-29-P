package it.polito.tdp.PremierLeague.model;

import java.time.Month;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.PremierLeague.db.PremierLeagueDAO;

public class Model {
	private SimpleWeightedGraph<Match, DefaultWeightedEdge> grafo;
	private PremierLeagueDAO dao;
	private Map<Integer,Match> idMap;
	
	public Model() {
		dao = new PremierLeagueDAO();
		idMap = new HashMap<Integer,Match>();
		dao.listAllMatches(idMap);
	}
	
	public void creaGrafo(int mese, int min) {
		grafo = new SimpleWeightedGraph<Match, DefaultWeightedEdge>(DefaultWeightedEdge.class);
		//vertici
		Graphs.addAllVertices(grafo, dao.getMatchByMese(mese, idMap));
		//archi
		for(Arco a : dao.getArchi(min, idMap)) {
			if(grafo.containsVertex(a.getM1()) && grafo.containsVertex(a.getM2()) ) {
				DefaultWeightedEdge e = this.grafo.getEdge(a.getM1(), a.getM2());
				if(e == null) {
					if(a.getPeso() < 0) {
						Graphs.addEdgeWithVertices(grafo, a.getM1(), a.getM2(), ((double) -1)*a.getPeso());
					}else if(a.getPeso() > 0) {
						Graphs.addEdgeWithVertices(grafo, a.getM1(), a.getM2(), (a.getPeso()));

					}
				}
			}
		}
		System.out.println("vertici "+ grafo.vertexSet().size());
		System.out.println("archi "+ grafo.edgeSet().size());

	}
	public String getConnessioneMax(int min){
//		List<Match> result = new LinkedList<Match>();
		String result = "";
		double max = 0.0;
		for(Match m : this.grafo.vertexSet()) {
			for(DefaultWeightedEdge e : this.grafo.outgoingEdgesOf(m)) {
				if(this.grafo.getEdgeWeight(e) > max) {
					max = this.grafo.getEdgeWeight(e);
	//				result.add(m);
	//				result.add(grafo.getEdgeTarget(e));
					result += this.grafo.getEdgeSource(e).toString() + "-" + this.grafo.getEdgeTarget(e).toString() + "\n";
				}
			}
		}
		
		return result;
//		System.out.println(result);
	}
	
}
