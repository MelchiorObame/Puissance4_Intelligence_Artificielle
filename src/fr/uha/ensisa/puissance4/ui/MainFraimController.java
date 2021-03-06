package fr.uha.ensisa.puissance4.ui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import fr.uha.ensisa.puissance4.data.Humain;
import fr.uha.ensisa.puissance4.data.IA;
import fr.uha.ensisa.puissance4.data.Joueur;
import fr.uha.ensisa.puissance4.jeu.JeuGraphique;
import fr.uha.ensisa.puissance4.util.Constantes;
import javafx.animation.PauseTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;

public class MainFraimController implements Initializable {

	ObservableList<String> joueurChoix = FXCollections.observableArrayList("Humain", "Intelligence Artificielle");
	ObservableList<String> iaChoice = FXCollections.observableArrayList("MinMax", "Alpha-beta");
	
	@FXML
    private ChoiceBox<String> joueur1;

    @FXML
    private ChoiceBox<String> joueur2;

    @FXML
    private ChoiceBox<String> ia1;

    @FXML
    private ChoiceBox<String> ia2;

    @FXML
    private TextField name1;

    @FXML
    private TextField name2;

    @FXML
    private TextField level1;

    @FXML
    private TextField level2;

    @FXML
    private Button lancer;
    
    @FXML
    private ImageView image;
   
    private int typeJoueur1;
    private int typeJoueur2;
    
    private static MainFraimController instance;
    Joueur player1, player2; 
    JeuGraphique jeu;
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		image.setVisible(false);
		name1.setDisable(true);
		name2.setDisable(true);
		ia1.setDisable(true);
		ia2.setDisable(true);
		level1.setDisable(true);
		level2.setDisable(true); 
		joueur1.setItems(joueurChoix);
		joueur2.setItems(joueurChoix);
		ia1.setItems(iaChoice);
		ia2.setItems(iaChoice);
		joueur1.getSelectionModel().selectedItemProperty().addListener(
				(observable, oldValue, newValue) -> ToggleSelection(newValue,1));
		joueur2.getSelectionModel().selectedItemProperty().addListener(
				(observable, oldValue, newValue) -> ToggleSelection(newValue,2));

	}
	
	public  MainFraimController() {
		instance = this;
	}
	
	/**
	 * instance de la classe MainControler contenant les donn�es
	 * de saisi pour le jeux
	 * @return
	 */
	public static MainFraimController getInstance() {
		return instance;
	}
	
	public JeuGraphique getJeu() {
		return jeu;
	}

	/**
	 * renvoie le type de Joueur 1 :IA ou Humain
	 * @return
	 */
	public String getJoueur1() {
		return joueur1.getValue();
	}
	
	/**
	 * renvoie le type de Joueur 1 :IA ou Humain
	 * @return
	 */
	public String getJoueur2() {
		return joueur2.getValue();
	}
	
	/**
	 * nom du joueur 1 au cas ou c'est un Humain
	 * @return
	 */
	public String getNom1() {
		if (joueur1.getValue()=="Humain") {
			return name1.getText();
		}
		return "IA_1";
	}
	
	/**
	 * nom du joueur 2 au cas ou c'est un Humain
	 * @return
	 */
	public String getNom2() {
		if (joueur2.getValue()=="Humain") {
			return name2.getText();
		}
		return "IA_2";
	}
	
	/**
	 * renvoie le type d'IA du joueur 1 : MinMax ou AlphaBeta
	 * @return
	 */
	public String getIA1() {
		return ia1.getValue();
	}
	
	/**
	 * renvoie le type d'IA du joueur 2 : MinMax ou AlphaBeta
	 * @return
	 */
	public String getIA2() {
		return ia2.getValue();
	}
	
	/**
	 * G�re la desactivation des touches et champs selon ce qui est choisi (IA ou humain)
	 * @param newValue
	 * @param numeroJoueur
	 * @return
	 */
	private Object ToggleSelection(String newValue,int numeroJoueur) {
		if(newValue=="Intelligence Artificielle") {
			if (numeroJoueur==1) {
				name1.setDisable(true);
				ia1.setDisable(false);
				level1.setDisable(false);
				typeJoueur1=Constantes.JOUEUR_IA;
			}
			if(numeroJoueur==2) {
				name2.setDisable(true);
				ia2.setDisable(false);
				level2.setDisable(false);
				typeJoueur2=Constantes.JOUEUR_IA;
			}
		}else {
			if (numeroJoueur==1) {
				name1.setDisable(false);
				level1.setDisable(true);
				ia1.setDisable(true);
				typeJoueur1=Constantes.JOUEUR_HUMAN;
			}
			if (numeroJoueur==2) {
				name2.setDisable(false);
				level2.setDisable(true);
				ia2.setDisable(true);
				typeJoueur2=Constantes.JOUEUR_HUMAN;
			}	
		}
		return null;
	}

	/**
	 * lance la grille lors de l'appuie sur le bouton "lancer la partie", lance le programe et
	 * affiche la grille
	 * @param event
	 * @throws IOException
	 */
	@FXML
	void lancement(ActionEvent event) throws IOException {
			image.setVisible(true);
			switch (typeJoueur1) {
				case Constantes.JOUEUR_HUMAN:
					player1 = (Joueur)new Humain(this.getNom1(),Constantes.JOUEUR_1);
					break;
				case Constantes.JOUEUR_IA:
					int ia;
					if (getIA1()=="MinMax") {
						ia=Constantes.IA_MINIMAX;
					}else {
						ia=Constantes.IA_ALPHABETA;
					}
					String iaName=Constantes.IA_NAMES[(int)Math.floor(Math.random()*Constantes.IA_NAMES.length)];
					player1 = (Joueur)new IA(iaName,Constantes.JOUEUR_1,ia,Integer.parseInt(this.level1.getText()));
					break;
				default:
					//cree un joueur Humain par defaut
					player1 = (Joueur)new Humain("Default Player 1",Constantes.JOUEUR_1);
					break;
			}
			switch (typeJoueur2) {
				case Constantes.JOUEUR_HUMAN:
					player2 = (Joueur)new Humain(this.getNom2(),Constantes.JOUEUR_2);
					break;
				case Constantes.JOUEUR_IA:
					int ia;
					if (getIA1()=="MinMax") {
						ia=Constantes.IA_MINIMAX;
					}else {
						ia=Constantes.IA_ALPHABETA;
					}
					String iaName=Constantes.IA_NAMES[(int)Math.floor(Math.random()*Constantes.IA_NAMES.length)];
					player2 = (Joueur)new IA(iaName,Constantes.JOUEUR_2,ia,Integer.parseInt(this.level2.getText()));
					break;
				default:
					//cree un joueur Humain par defaut
					player2 = (Joueur)new Humain("Default Player 2",Constantes.JOUEUR_2);
					break;
			}	
			this.jeu = new JeuGraphique(player1, player2,new Console());
			
			//petit temps de pause mis pour des raison esthetiques.
			PauseTransition pt = new PauseTransition();
			pt.setDuration(Duration.millis(700));
			pt.setOnFinished(ev -> {
				lancer.getScene().getWindow().hide();
				Stage grille = new Stage();
				Parent root;
				try {
					root = FXMLLoader.load(getClass().getResource("MainGrid.fxml"));
					Scene scene = new Scene(root);
					grille.setScene(scene);
					grille.show();
					grille.setResizable(false);
				} catch (IOException e) {
					e.printStackTrace();
				}
				jeu.run();
			});
			pt.play();
	 }

}
