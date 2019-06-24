package it.polito.tdp.food.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.food.db.FoodDao;

public class Model {
	
	private Map<Integer, Condiment> idMap;
	private FoodDao dao;
	private Graph<Condiment, DefaultWeightedEdge> grafo;
	private List<Condiment> best;
	
	public Model() {
		dao = new FoodDao();
		idMap = new HashMap<Integer, Condiment>();
	}

	public void creaGrafo(double calorie) {
		
		dao.listCondiment(calorie, idMap);
		
		grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		Graphs.addAllVertices(grafo, idMap.values());
		
		for ( Adiacenti a: dao.listAdiacenti() ) 
			if ( idMap.containsKey(a.getIng1()) && idMap.containsKey(a.getIng2()) )
					Graphs.addEdge(grafo, idMap.get(a.getIng1()), idMap.get(a.getIng2()), a.getPeso());
			
	}
	
	public String visualizza() {
		String result = "";
		
		List<Condiment> lista = new ArrayList<>(grafo.vertexSet());
		Collections.sort(lista);
		
		for (Condiment c: lista) {
			int somma = 0;
			for (Condiment con: Graphs.neighborListOf(grafo, c))
				somma += (int) (grafo.getEdgeWeight(grafo.getEdge(c, con))); 
			
			result += "Ingrediente: "+c.getDisplay_name()+"  Calorie: "+c.getCondiment_calories()+"  Cibi: "+somma+"\n";
		}
		
		return result;
	}
	
	public List<Condiment> getIngredienti() {
		return new LinkedList<>(grafo.vertexSet());
	}

	public List<Condiment> calcolaDieta(Condiment c) {
		best = new ArrayList<>();
		
		List<Condiment> parziale = new ArrayList<>();
		parziale.add(c);
		
		ricorsione(parziale);
		
		return best;
	}
	
	private void ricorsione(List<Condiment> parziale) {
		
		for ( Condiment condiment: grafo.vertexSet() ) 
			if ( !parziale.contains(condiment) && !grafo.containsEdge(condiment, parziale.get(parziale.size()-1)) ) {
				parziale.add(condiment);
					
				ricorsione(parziale);
					
				parziale.remove(condiment);
			}
		
		if ( sommaCal(parziale) > sommaCal(best) )
			best = new ArrayList<>(parziale);
		
	}
	
	private double sommaCal(List<Condiment> completa) {
		double somma = 0.0;
		
		for ( Condiment c: completa )
			somma += c.getCondiment_calories();
		
		return somma;
	}
	
}