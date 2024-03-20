package Demineur;

public abstract class Case {
		
	private int posLigne;
	private int posCol;
    private boolean visible;
    private int nbBomb;
    private boolean flaged;
    
    public int getNbBomb() {return this.nbBomb;}		
    public boolean isFlaged() {return this.flaged;}	
	public void setFlaged(boolean flag) {this.flaged = flag;}   
	public void setNbBomb(int pNbBomb) {this.nbBomb = pNbBomb;} 
    public boolean isVisible() {return this.visible;} 
    public void setVisible(boolean visible) {this.visible = visible;}	   
	public int getPosLigne() {return posLigne;}
	public void setPosLigne(int posLigne) {this.posLigne = posLigne;}
	public int getPosCol() {return posCol;}
	public void setPosCol(int posCol) {this.posCol = posCol;}
			
	public Case(int pPosLig,int pPosCol) {
		this.posLigne = pPosLig;
		this.posCol = pPosCol;
		this.visible = false;
		this.flaged = false;
	}
	
	public abstract String toString();
	
}
