package it.polito.tdp.PremierLeague.model;

import java.time.Month;
import java.util.ArrayList;
import java.util.HashMap;
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
	
	public void creaGrafo(int mese) {
		grafo = new SimpleWeightedGraph<Match, DefaultWeightedEdge>(DefaultWeightedEdge.class);
		//vertici
		Graphs.addAllVertices(grafo, dao.getMatchByMese(mese, idMap));
		//archi
		System.out.println(grafo.vertexSet().size());
	}
	
}
