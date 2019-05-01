package fr.uha.ensisa.puissance4.ui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import fr.uha.ensisa.puissance4.data.Partie;
import fr.uha.ensisa.puissance4.util.Constantes;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class MainGridController implements Initializable{

    @FXML
    private MenuItem newGameButton;

    @FXML
    private MenuItem scoreResetButton;

    @FXML
    private MenuItem mainMenuButton;

    @FXML
    private MenuItem rulesButton;

    @FXML
    private MenuItem aboutButton;

    @FXML
    private Label gameState;

    @FXML
    private Label player1Score;

    @FXML
    private Label player2Score;

    @FXML
    private Label player1Pseudo;

    @FXML
    private Label player2Pseudo;

    @FXML
    private Circle colorPlayer1;

    @FXML
    private Circle colorPlayer2;

    @FXML
    private GridPane rectGrid;

    @FXML
    private GridPane buttonGrid;
    
    @FXML
    private ImageView imageLoadCenter;
    
    @FXML
    private Label info;

    private int coloneJouer;
    private int lineJouer;
	private int alternColor=1;
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		setPlayerPseudo1(MainFraimController.getInstance().player1.getNom());
		setPlayerPseudo2(MainFraimController.getInstance().player2.getNom());
		MainFraimController.getInstance().getJeu().setMainGridControler(this);
		imageLoadCenter.setVisible(true);
	}
    
    public void setColonneJouer(int col) {
    	coloneJouer=col;
    }
    
    public int getColonneJouer() {
    	return coloneJouer;
    }
    
    /**
     * Recupere la ligne du jeton à afficher
     * @return
     */
    public int getLineJouer() {
    	return lineJouer;
    }
    
    
    public void setLineJouer(int line) {
    	this.lineJouer=line;
    }

	public void afficherFinGame() {
		String msg;
		Partie partie = MainFraimController.getInstance().getJeu().getPartie();
		switch(partie.getEtatPartie())
		{
			case Constantes.VICTOIRE_JOUEUR_1 : 
				msg="   VICTOIRE DE: "+partie.getJoueur1().getNom();
				break;
			case Constantes.VICTOIRE_JOUEUR_2 : 
				msg="  VICTOIRE DE: "+partie.getJoueur2().getNom();
				break;
			default : 
				msg=" MATCH NUL";
				break;
		}
		imageLoadCenter.setVisible(false);
		this.gameState.setText(msg);
	}
	
	/**
	 * Ajoute le jeton dans la grille graphique
	 */
	public void updateGrille() {
		Circle circ2 ;
		if(alternColor%2==0) {
    		circ2 = new Circle(45,Color.YELLOW);
    		alternColor++;
    	}else {
    		circ2 = new Circle(45,Color.RED);
    		alternColor++;
    	}
    	rectGrid.add(circ2, coloneJouer, lineJouer-1);
	}
    
	
	
    public void setPlayerPseudo1(String user) {
    	this.player1Pseudo.setText(user);
    }
    
    public void setPlayerPseudo2(String user) {
    	this.player2Pseudo.setText(user);	
    }
    
    @FXML
    void aboutDialog(ActionEvent event) {
    	Alert about = new Alert(AlertType.INFORMATION);
    	about.setTitle("A propos");
    	about.setHeaderText("A propos");
    	Label lb = new Label("Jeu de puissance 4 développe en java, les sources sont disponibles à  l'adresse suivante,");
    	FlowPane fp = new FlowPane();
    	Hyperlink link = new Hyperlink("https://github.com/MelchiorObame/Puissance4_Intelligence_Artificielle"); 
    	fp.getChildren().addAll(lb,link);
    	about.getDialogPane().contentProperty().set(fp);
    	about.showAndWait();
    }

    @FXML
    void rulesDialog(ActionEvent event) {
    	Alert about = new Alert(AlertType.INFORMATION);
    	about.setTitle("Regles du jeu");
    	about.setHeaderText("Regles du jeu");
    	about.setContentText("Le but du jeu est d'aligner 4 pions de sa couleur soit horizontalement , verticalement ou diagonalement.\n");
    	about.showAndWait();
    }

    @FXML
    void loadMainMenu(ActionEvent event) throws IOException {
    	Stage stage = (Stage) buttonGrid.getScene().getWindow();
    	Parent menu = FXMLLoader.load(getClass().getResource("MainFraim.fxml"));
    	Scene menuScene = new Scene(menu);
    	stage.setScene(menuScene);
    	stage.show();
    }
    
    @FXML
    void scoreReset(ActionEvent event) {

    }
    

    @FXML
    void buttonHandler(ActionEvent event) {
    	
    }


    @FXML
    void newGame(ActionEvent event) {
    	
    }



}
