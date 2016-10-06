//GET (Accesseurs) & SET (Mutateurs)
public class Materia {
	
	// Quitter l'application XReines
	private static boolean exit_XReines = false;
	public boolean  getexit_XReines()	throws IllegalStateException {	
		return exit_XReines;	
	}
	public void setexit_XReines(boolean restart)	{
		int i = 1;
		Materia.exit_XReines = restart;	
	}
	
	// Taille du damier : Minimum 4 (avec 9 cases impossible de mettre 3 Dames)
	private static int damier = 1000000;	
	public int  getDamier()	throws IllegalStateException {	
		return damier;	
	}
	public void setDamier(int nbrDames)	{
		Materia.damier = nbrDames;	
	}
	
	// Affiche les solution sous forme de grille en ligne de commande
	private static boolean view_grille = false;		
	public boolean  getview_grille()	throws IllegalStateException {	
		return view_grille;	
	}
	public void setview_grille(boolean binaire)	{
		Materia.view_grille = binaire;	
	}
	
	// Affiche les solutions sous forme textuelle en ligne de commande
	private static boolean view_data = false;		
	public boolean  getview_data()	throws IllegalStateException {	
		return view_data;	
	}
	public void setview_data(boolean binaire)	{
		Materia.view_data = binaire;	
	}
	
	// Permet de stocker les solution dans un ArrayList
	private static boolean stock_data = false;		
	public boolean  getstock_data()	throws IllegalStateException {	
		return stock_data;	
	}
	public void setstock_data(boolean binaire)	{
		Materia.stock_data = binaire;	
	}

}
