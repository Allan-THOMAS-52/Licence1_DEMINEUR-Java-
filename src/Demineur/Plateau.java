package Demineur;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

public class Plateau {

	private int nbCol;
	private int nbLigne;
	private int diff;
	private int nbMinesTot;
	private int nbFlagRemaining;
	private Case[][] plateau;
	private boolean end;
		
	public Plateau(int pNbLigne, int pNbCol) {
		this.plateau = new Case[pNbLigne][pNbCol];
		this.nbCol = pNbCol;
		this.nbLigne = pNbLigne;
		this.end = false;
		this.nbFlagRemaining = 0;
	}
		
	public int getNbCol() {return nbCol;}
	public int getNbLigne() {return nbLigne;}
	public int getDiff() {return diff;}
	public void setNbCol(int pNbCol) {this.nbCol = pNbCol;}	
	public void setNbLigne(int pNbLigne) {this.nbLigne = pNbLigne;}	
	public void setDiff(int pDiff) {this.diff = pDiff;}
	public boolean getEnd() { return this.end;}
	public void setEnd(boolean pEnd) { this.end = pEnd;}
	public Case getCase(int lig, int col) { return plateau[lig][col];}
	public int getNbFlagRemaining() {return this.nbFlagRemaining;}
	public void addNbFlag() {this.nbFlagRemaining++;}
	public void subNbFlag() {this.nbFlagRemaining--;}	
	
	//si les coords sont en dehors du plateau, on renvoie true, sinon on renvoie false
	public boolean isOut(int lig,int col) {
		if(lig >= 0 && lig < this.nbLigne && col >= 0 && col < this.nbCol)
			return false;
		else
			return true;
	}
	
	public int isBomb(int lig, int col) {
		if(lig >= 0 && lig < this.nbLigne && col >= 0 && col < this.nbCol &&
				plateau[lig][col].getClass().getSimpleName().equals("Mine"))
			return 1;
		else
			return 0;		
	}
	
	//calcul du nb total de mines 
	public void initNbMines() {
		this.nbMinesTot = (nbCol*nbLigne*diff)/10;
		this.nbFlagRemaining = this.nbMinesTot;
	}
	
	public void initCases() {
		//place le nb exact de mines de manière aléatoire
		int compteurMines = 0,lig ,col;
		while( compteurMines != nbMinesTot ) {			
			lig = (int)(Math.random()*nbLigne);
			col = (int)(Math.random()*nbCol);
			if(plateau[lig][col] == null) {
				plateau[lig][col] = new Mine(lig,col);
				compteurMines++;
			}
		}	
		//comble les cases sans mines par des cases vides		
		for( lig = 0; lig < nbLigne ;lig++ ) 
			for( col = 0; col < nbCol ;col++ )
				if( plateau[lig][col] == null )
					plateau[lig][col] = new CaseVide(lig,col);									
	}
	
	public String toString() {	
		String result = "";
		for(int lig=0 ; lig<nbLigne ; lig++ ) {			
			for(int col=0 ; col<nbCol ; col++ ) 				
				result += plateau[lig][col].toString()+"  "; 
			result += "\n \n";
		}
		return result;		
	}
	
	public void decouvrir(int pLig, int pCol) {
		if(plateau[pLig][pCol].isVisible()) { //si case deja revelee
			System.out.println("Case déjà découverte !");
			return;
		}
		if(plateau[pLig][pCol].isFlaged()) {//si il y a un drapeau sur la case
			System.out.println("On ne peut pas découvrir une case marquée !");
			return;		
		}
		else			
			if(plateau[pLig][pCol].getClass().getSimpleName().equals("Mine")) //si c'est une mine on perd
			Perdu();		
			else { //sinon on decouvre la case et on regarde si le jeu est gagnant
				vide();			
				reveal(pLig,pCol);	
				vide();			
			}
	}
	
	public void vide() {
		int compteur = 0;
		for( int lig = 0; lig < nbLigne ;lig++ ) 
			for( int col = 0; col < nbCol ;col++ )
				if(plateau[lig][col].isVisible())
					compteur++;
		if(this.nbLigne*this.nbCol-this.nbMinesTot == compteur)
			Gagner();
	}
	
	public void Perdu() {
		System.out.println(" Vous avez perdu !");
		for(int i=0;i<this.nbLigne;i++)
			for(int j=0;j<this.nbCol;j++)
				reveal(i,j);	
		System.out.println("\n"+this.toString());
		setEnd(true);
		music();
	}
	
	public void Gagner() {
		System.out.println(" Vous avez gagné la partie !");
		System.out.println("\n"+this.toString());
		setEnd(true);
	}
		
	public int calcNear(int pLig, int pCol) {
		int nbNear=0;
		
		nbNear+= isBomb(pLig-1,pCol-1);
		nbNear+= isBomb(pLig-1,pCol);
		nbNear+= isBomb(pLig-1,pCol+1);
		
		nbNear+= isBomb(pLig,pCol-1);
		nbNear+= isBomb(pLig,pCol+1);
		
		nbNear+= isBomb(pLig+1,pCol-1);
		nbNear+= isBomb(pLig+1,pCol);
		nbNear+= isBomb(pLig+1,pCol+1);
		
		return nbNear;
	}
	
	public void reveal(int lig, int col){
	  if(isOut(lig,col))
		  return;
	  if(plateau[lig][col].isFlaged())
		  return;
	  if(plateau[lig][col].isVisible())
		  return;
	  plateau[lig][col].setVisible(true);
	  
	  if(calcNear(lig,col)!=0) {
		  plateau[lig][col].setNbBomb(calcNear(lig,col));
		  return;
	  }
		 
	  reveal(lig-1,col-1);
	  reveal(lig-1,col+1);
	  reveal(lig+1,col-1);
	  reveal(lig+1,col+1);
	  reveal(lig-1,col);
	  reveal(lig+1,col);
	  reveal(lig,col-1);
	  reveal(lig,col+1);
	  
	}
	
	public void music() {
		try {
			FileInputStream fileInputStream = new FileInputStream
					("C:\\Users\\allan\\Desktop\\Workplace2\\Demineur\\src\\MP3\\bomb.mp3");
			Player player = new Player(fileInputStream);
			player.play();
		} catch (FileNotFoundException e) {			
			e.printStackTrace();
		} catch (JavaLayerException e) {
			e.printStackTrace();
		}
	}
	
}
