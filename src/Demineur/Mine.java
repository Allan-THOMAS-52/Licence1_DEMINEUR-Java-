package Demineur;

public class Mine extends Case{
		
	public Mine(int lig, int col) {
		super(lig,col);			
	}
		
	public String toString() {		
		return isFlaged() ? "|  ⚑  |" : isVisible() ? "|  Ⳝ  |" : "| "+this.getPosLigne()+";"+this.getPosCol()+" |";	
	}
	
}
