package it.polito.tdp.spellchecker;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

public class FXMLController {

	List<String> dizionario;
	List<String> dictionary;
	
    public FXMLController() {
		this.dizionario = new ArrayList<>();
		this.dictionary = new ArrayList<>();
	}

	@FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<String> cmbLingua;

    @FXML
    private Label txtErrori;

    @FXML
    private TextArea txtParoleErrate;

    @FXML
    private TextArea txtTesto;
    
    @FXML
    private Label txtTempo;

    @FXML
    void doClearText(ActionEvent event) {
    	txtErrori.setText("");
    	txtParoleErrate.setText("");
    	txtTempo.setText("");
    	txtTesto.setText("");
    }

    @FXML
    void doSpellCheck(ActionEvent event) {

        try {
        	FileReader fr = new FileReader("src/main/resources/English.txt");
        	BufferedReader br = new BufferedReader(fr); 
        	String word;
        	while ((word = br.readLine()) != null) {
        		dictionary.add(word);
        	}
             br.close();
        } catch (IOException e){
        	System.out.println("Errore nella lettura del file English.txt");
        }
        

        try {
        	FileReader fr = new FileReader("src/main/resources/Italian.txt");
        	BufferedReader br = new BufferedReader(fr); 
        	String word;
        	while ((word = br.readLine()) != null) {
        		dizionario.add(word);
        	}
             br.close();
        } catch (IOException e){
        	System.out.println("Errore nella lettura del file Italian.txt");
        }
        
    	String testo=txtTesto.getText();
    	long start=System.currentTimeMillis();
    	int num=0;
    	testo=testo.toLowerCase();
    	testo=testo.replaceAll("[.,\\/#!$%\\^&\\*;:{}=\\-_?`~()\\[\\]\"]", "");
    	String[] parole=testo.split(" ");
    	if(cmbLingua.getValue()==null) {
    		txtErrori.setText("Inserire lingua");
   			return;
   		}
    	if(cmbLingua.getValue().equals("Italiano")) {
    		for(String s:parole)
    			if(!dizionario.contains(s)) {
    				txtParoleErrate.appendText(s+" \n");
    				num++;
    			}
    	} 
    	
    	if(cmbLingua.getValue().equals("English")) {
    		for(String s:parole)
    			if(!dictionary.contains(s)) {
    				txtParoleErrate.appendText(s+" \n");
    				num++;
    			}
    	}
    	long end=System.currentTimeMillis();
    	txtErrori.setText("The text contains "+num+" errors");
    	txtTempo.setText("Spell check complete in "+((end-start)/Math.pow(10, 9)));
    }

    @FXML
    void initialize() {
        assert cmbLingua != null : "fx:id=\"cmbLingua\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtErrori != null : "fx:id=\"txtErrori\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtParoleErrate != null : "fx:id=\"txtParoleErrate\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtTempo != null : "fx:id=\"txtTempo\" was not injected: check your FXML file 'Scene.fxml'.";

        
        cmbLingua.getItems().add("Italiano");
        cmbLingua.getItems().add("English");
        

        }
    

}
