package it.polito.tdp.food.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.food.model.Adiacenti;
import it.polito.tdp.food.model.Condiment;
import it.polito.tdp.food.model.Food;

public class FoodDao {

	public List<Food> listAllFood() {
		
		String sql = "SELECT * FROM food";
		
		List<Food> list = new ArrayList<>();
		
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					list.add(new Food(res.getInt("food_id"),
							res.getInt("food_code"),
							res.getString("display_name"), 
							res.getInt("portion_default"), 
							res.getDouble("portion_amount"),
							res.getString("portion_display_name"),
							res.getDouble("calories")
							));
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
			
			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return list;
	}
	
	
	public List<Condiment> listCondiment(double calorieMax, Map<Integer, Condiment> idMap) {
		
		String sql = "SELECT * FROM condiment WHERE condiment_calories < ?";
		
		List<Condiment> list = new ArrayList<>();
		
		try {
			Connection conn = DBConnect.getConnection() ;
			PreparedStatement st = conn.prepareStatement(sql) ;
			st.setDouble(1, calorieMax);
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				Condiment c = new Condiment(res.getInt("condiment_id"), res.getInt("food_code"), res.getString("display_name"), 
						                     res.getString("condiment_portion_size"), res.getDouble("condiment_calories") );
				
				if ( !idMap.containsKey(c.getFood_code()) )
					idMap.put(c.getFood_code(), c);
				
				list.add(c);
				
			}
			
			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return list ;
	}
	
	
	public List<Adiacenti> listAdiacenti() {
		
		String sql = "SELECT f1.condiment_food_code AS ing1, f2.condiment_food_code AS ing2, COUNT(*) AS cont " + 
			         "FROM food_condiment AS f1, food_condiment AS f2 " + 
			         "WHERE f1.food_code = f2.food_code AND f1.condiment_food_code > f2.condiment_food_code " + 
			         "GROUP BY f1.condiment_food_code, f2.condiment_food_code";
		
		List<Adiacenti> list = new ArrayList<>() ;
		
		try {
			Connection conn = DBConnect.getConnection() ;
			PreparedStatement st = conn.prepareStatement(sql) ;
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				if ( res.getInt("cont") > 0 ) 
					list.add( new Adiacenti(res.getInt("ing1"), res.getInt("ing2"), res.getInt("cont")) );
			}
			
			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return list ;
	}
}