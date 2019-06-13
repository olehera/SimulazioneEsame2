package it.polito.tdp.food.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.food.db.FoodDao;

public class Model {
	
	private Map<Integer, Condiment> idMap;
	private List<Food> food;
	private FoodDao dao;
	private Graph<Condiment, DefaultWeightedEdge> grafo;
	
	public Model() {
		dao = new FoodDao();
		idMap = new HashMap<Integer, Condiment>();
		food = new ArrayList<>(dao.listAllFood());
	}

	public void creaGrafo(double calorie) {
		
		dao.listCondiment(calorie, idMap);
		
		grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		Graphs.addAllVertices(grafo, idMap.values());
		
		for ( Adiacenti a: dao.listAdiacenti() )
			Graphs.addEdge(grafo, idMap.get(a.getIng1()), idMap.get(a.getIng2()), a.getPeso());
			
	}
	
	public String visualizza() {
		String result = "";
		
		List<Condiment> lista = new ArrayList<>(grafo.vertexSet());
		Collections.sort(lista);
		
		for (Condiment c: lista) {
			int somma = 0;
			for (Condiment con: Graphs.neighborListOf(grafo, c))
				somma = (int) (grafo.getEdgeWeight(grafo.getEdge(c, con))); 
			
			result += "Ingrediente: "+c.getDisplay_name()+"  Calorie: "+c.getCondiment_calories()+"  Cibi :"+somma+"\n";
		}
		
		return result;
	}

}