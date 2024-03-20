package Demineur;

import java.util.Scanner;
import java.util.*;

public class Jeu {

	public Jeu() {
		start();
	}
	
	private boolean isANumber(String input) {
		if (input == null) {
	        return false;
	    }
	    try {
	        double d = Double.parseDouble(input);
	    } catch (NumberFormatException nfe) {
	        return false;
	    }
	    return true;
	}
	
	
	private void start() {
			
		System.out.println("                            Jeu du démineur par Allan THOMAS");
		System.out.println("========================================================================================\n");
		
		Scanner sc = new Scanner(System.in);
		Scanner sc2 = new Scanner(System.in);
		Scanner sc3 = new Scanner(System.in);
		boolean entry = false;
		String input = "";
		
		
		
		do {
			System.out.println("Jouer ? ( y/n )");
			
			input = sc.nextLine();
			input.toLowerCase();
			if(input.equals("y") || input.equals("n"))
				entry = true;
			
		}while(entry!=true);
		
		
		if(input.equals("y")) {
		    boolean entry2;
		    int x=0,y=0;
			do {
				entry2 = false;
				System.out.println(" Entrez le nombre de lignes (10 max):");			
				input = sc.nextLine();				
				if(isANumber(input) && !input.equals("0")) {
					entry2 = true;
					x = Integer.parseInt(input);
				}
			}while(entry2!=true);
			
			do {
				entry2 = false;
				System.out.println(" Entrez le nombre de colonnes (10 max):");					
				input = sc.nextLine();				
				if(isANumber(input) && !input.equals("0")) {
					entry2 = true;
					y = Integer.parseInt(input);
				}
			}while(entry2!=true);
																
			Plateau p = new Plateau(x,y);
			
			do {
				entry2 = false;
				System.out.println(" Difficulté ? ( max = 9 min = 1 )");				
				input = sc.nextLine();				
				if(isANumber(input) && !input.equals("0")) {
					entry2 = true;
					p.setDiff(Integer.parseInt(input));
				}
			}while(entry2!=true);
			
						
			p.initNbMines();
			p.initCases();
			
			while(true) {
				
				System.out.println("\n"+p.getNbFlagRemaining()+" drapeaux restants");
				System.out.print("\n"+p.toString());
				entry = false;
				String input2 = "";
				do {
					System.out.println(" Prêt ? ( y/n ) || flag || unflag ");	
					input2 = sc2.nextLine();
					input2.toLowerCase();
					if(input2.equals("y") || input2.equals("n") || input2.equals("win") || input2.equals("lose") 
							|| input2.equals("flag") || input2.equals("unflag"))
						entry = true;
				}while(entry!=true);
				
				
				if(input2.equals("y")) {
					
					int lig=0,col=0;
					do {	
						entry = false;
						System.out.println(" Saisissez la ligne :");
						input2 = sc.nextLine();
						if(isANumber(input2) && (Integer.parseInt(input2) < p.getNbLigne()) ) {
							lig = Integer.parseInt(input2);
							entry = true;
						}					
					}while(entry!=true);
					 					  
					do {	
						entry = false;
						System.out.println(" Saisissez la colonne :");
						input2 = sc.nextLine();
						if(isANumber(input2) && (Integer.parseInt(input2) < p.getNbCol())) {	
							col = Integer.parseInt(input2);
							entry = true;
						}					
					}while(entry!=true);
										
					p.decouvrir(lig,col);
					
					if(p.getEnd() == true) {
						System.out.println(" Rejouer ? ( y/n )");					
						String input3 = sc3.nextLine();
						if(input3.equals("n")) 
							break;
						start();				
					}
				}
				else
					if(input2.equals("win")) {
						p.Gagner();
						System.out.println(" Rejouer ? ( y/n )");					
						String input3 = sc3.nextLine();
						if(input3.equals("n")) 
							break;
						start();
					}
				else
					if(input2.equals("lose")) {
						p.Perdu();
						p.toString();
						System.out.println(" Rejouer ? ( y/n )");					
						String input3 = sc3.nextLine();
						if(input3.equals("n")) 
							break;
						start();
					}
				else
					if(input2.equals("flag")) {
						if(p.getNbFlagRemaining()==0)
							System.out.println("\nVous n'avez plus de drapeaux !");
						else {
							int lig=0,col=0;	
							do {	
								entry = false;
								System.out.println(" Saisissez la ligne :");
								input2 = sc.nextLine();
								if(isANumber(input2)) {							
									lig = Integer.parseInt(input2);
									entry = true;
								}					
							}while(entry!=true);
							
							do {	
								entry = false;
								System.out.println(" Saisissez la colonne :");
								input2 = sc.nextLine();
								if(isANumber(input2)) {							
									col = Integer.parseInt(input2);
									entry = true;
								}					
							}while(entry!=true);
							
							if(p.getCase(lig, col).isFlaged())
								System.out.println("Il y a déjà un drapeau ici !");
							else {
								p.getCase(lig, col).setFlaged(true);
								p.subNbFlag();
							}
						}						
					}
				else
					if(input2.equals("unflag")) {
						int lig=0,col=0;	
						do {	
							entry = false;
							System.out.println(" Saisissez la ligne :");
							input2 = sc.nextLine();
							if(isANumber(input2)) {							
								lig = Integer.parseInt(input2);
								entry = true;
							}					
						}while(entry!=true);
						
						do {	
							entry = false;
							System.out.println(" Saisissez la colonne :");
							input2 = sc.nextLine();
							if(isANumber(input2)) {							
								col = Integer.parseInt(input2);
								entry = true;
							}					
						}while(entry!=true);
						
						if(!p.getCase(lig, col).isFlaged())
							System.out.println("Il n'y a pas de drapeau ici !");
						else {
							p.addNbFlag();
							p.getCase(lig, col).setFlaged(false);
						}
					}
				else
					if(input2.equals("n"))
						System.exit(0);			
			}	    
		}
		else
			System.exit(0);
	}
}
