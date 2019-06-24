package it.polito.tdp.food;

import java.net.URL;
import java.util.ResourceBundle;

import it.polito.tdp.food.model.Condiment;
import it.polito.tdp.food.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FoodController {
	
	private Model model;
	
	public void setModel(Model model) {
    	this.model = model;
    }

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="txtCalorie"
    private TextField txtCalorie; // Value injected by FXMLLoader

    @FXML // fx:id="btnCreaGrafo"
    private Button btnCreaGrafo; // Value injected by FXMLLoader

    @FXML // fx:id="boxIngrediente"
    private ComboBox<Condiment> boxIngrediente; // Value injected by FXMLLoader

    @FXML // fx:id="btnDietaEquilibrata"
    private Button btnDietaEquilibrata; // Value injected by FXMLLoader
    
    @FXML
    private TextArea txtResult;
    
    @FXML
    void doCreaGrafo(ActionEvent event) {
    	double calorie;
    	
    	try {
    	      calorie = Double.parseDouble(txtCalorie.getText().trim());
    	} catch(NumberFormatException nfe) {
    	      txtResult.setText("Devi inserire un numero reale!");
    	      return ;
    	}

    	model.creaGrafo(calorie);
    	txtResult.setText(model.visualizza());
    	boxIngrediente.getItems().addAll(model.getIngredienti());
    }

    @FXML
    void doCalcolaDieta(ActionEvent event) {
    	Condiment scelto;
    	
    	if ( boxIngrediente.getValue() == null ) {
    		txtResult.setText("Devi selezionare un Ingrediente!");
    		return ;
    	} else 
    		scelto = boxIngrediente.getValue();
    	
    	txtResult.setText("Lista di Ingredienti:\n");
    	for ( Condiment c: model.calcolaDieta(scelto) )
    		txtResult.appendText(c+"\n");
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert txtCalorie != null : "fx:id=\"txtCalorie\" was not injected: check your FXML file 'Food.fxml'.";
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'Food.fxml'.";
        assert boxIngrediente != null : "fx:id=\"boxIngrediente\" was not injected: check your FXML file 'Food.fxml'.";
        assert btnDietaEquilibrata != null : "fx:id=\"btnDietaEquilibrata\" was not injected: check your FXML file 'Food.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Food.fxml'.";
    }
    
}