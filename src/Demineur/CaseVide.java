package Demineur;

public class CaseVide extends Case{

	public CaseVide(int lig, int col) {
		super(lig,col);		
	}
		
	public String toString() {		
		return isFlaged() ? "|  ⚑  |" : (isVisible() == true && getNbBomb() == 0) ? "|     |" 
				: getNbBomb()>0 ? "|  "+getNbBomb()+"  |" : "| "+this.getPosLigne()+";"+this.getPosCol()+" |";
		
	}
}
