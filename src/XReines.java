//region IMPORT
	import java.util.Scanner;				// Pour permettre la récupération de la saisie au clavier (Scanner(System.in))
	import java.util.concurrent.TimeUnit;	// Pour convertir des nanosecondes (benchmark) en microsecondes
	import java.util.ArrayList;	// ArrayList pour permettre le stockage des solutions
	import java.util.Arrays;
	//import java.util.regex.*;	// API permettant la gestion des Regex
	
	// Sauvegarde dans un fichier :
	import java.io.File;					
	import java.io.FileWriter;				
	import java.io.PrintWriter;				
	import java.io.BufferedWriter;			
	import java.io.IOException;				// Gestion des exceptions lors de la lecture/ecriture d'un fichier
//endregion

	
public class XReines {
	/* Le but du problème des X dames est de placer X dames d'un jeu d'échecs sur un damier de X × X cases sans que les dames ne 
	 * puissent se menacer mutuellement, conformément aux règles du jeu d'échecs (la couleur des pièces étant ignorée). 
	 * Par conséquent, deux dames ne devraient jamais partager la même rangée, colonne, ou diagonale.	*/
	
  //region INSTANCIATIONS	
	public static ArrayList<String> Solutions = new ArrayList<String>();
	public static Materia Dames = new Materia();
	//public static Shell   Wall = new Shell();		
	public static Scanner saisie = new Scanner(System.in);
	public static File fichier = new File ("XReines_Benchmark.txt");
	//public static PrintWriter pw = new PrintWriter (new BufferedWriter (new FileWriter (fichier)));
  //endregion
	
	
  //region VARIABLES	
	// Calculs
	private static int[] valeurX = new int[Dames.getDamier()];	/* tableau dont l'index Y stocke la valeur X */
	private static int numSol = 0;								/* Nombre de solutions trouvée */
	// Benchmark
	private static long chronoinit;				/* Pour permettre une soustraction au chrono BENCHMARK du temps d'execution */
	private static long chronostart;			/* Pour permettre le temps de calcul de chaque solution */
	private static long dureeEnNs;				/* Pour stocker la conversion des nanosecondes en microsecondes */
	// Exceptions
	public static int valMax = Dames.getDamier();
	public static boolean valBig = false;
	public static boolean valAlpha = false;
  //endregion	 	

	
  //region FONCTIONS  
	/* Methode principale permettant le calcul de la position X,Y des Dames et l'appel des fonctions */
	public static void main(String[] args) {
	  try {
		 Dames.setexit_XReines(false);
		 while (Dames.getexit_XReines() != true) {
			// INTRODUCTION
			System.out.print("     PROBLEME DES X REINES     \n");
			System.out.println("\nLe but du problème des X reines est de placer X reines d'un "
			 		        + "\njeu d'échecs sur un damier de X × X cases sans que les dames "
			 		        + "\nne puissent se menacer mutuellement, conformément aux règles "
			 		        + "\ndu jeu d'échecs (la couleur des pièces étant ignorée). "
			 		        + "\nPar conséquent, deux reines ne devraient JAMAIS partager "
			 		        + "\nla même rangée, colonne, ou diagonale."
			 		        + "\n_____________________________________________________________");
			 
			// VARIABLES LOCALES
			int count = 0;					// Compteur des possibilités explorées
			 
			// SAISIES AU CLAVIER		 
			Saisie_Config();				// Gestion de l'affichage/stockage des solutions trouvées
			 
			// ARBRE DES POSSIBILITES		 
			count = arbre_calcul(count); 	// Calcul par recursivité heuristique sur un arbre des possibilités
			 
			// AFFICHAGE RESULTATS
			Benchmark_Final(count);
			 
			if (Dames.getstock_data() == true) {
				File_Write(count);
			}
			
		    
		    // EXIT
		    System.out.println("_____________________________________________________________\n");
		    System.out.println("Souhaitez-vous quitter XReines ?");
		    String exit = saisie.nextLine();
			Dames.setexit_XReines(Shell.ConvertStringToBool(exit));			 
			if (Dames.getexit_XReines()) {				 
				 System.out.println("Good bye ! =)");
			}
			Shell.CLS();		    
		 }
		 saisie.close();
	  }
	  catch (Exception e)
	  {
		  // GESTION DES ERREURS
		  System.out.print("\n/!\\ ERREUR DÉTECTÉE /!\\ \n");
		  if (valAlpha == true) {			  
			  System.out.print("Veuillez entrer un caractère numérique (entier)\n"
			  				 + "Valeur maximum autorisée :"+valMax+"\n"); 
		  }
		  if (valBig == true) {			  
			  System.out.print("Ne peut depasser la valeur statique fixée par défaut.\n"
			  				 + "La limite a été fixée par rapport à la capacité de \n"
			  				 + "prise en charge maximale de la console...\n"
			  				 + "Valeur maximum autorisée :"+valMax+"\n"); 
		  }
		  System.out.print(e +"\n"); 
		  //e.printStackTrace(); 	/*Affiche l'erreur complète*/
		  
	  }
	}	
	
	private static void Saisie_Config() {
		 System.out.print("CONFIGURATION\n\n");		  
		 		 
		// SAISIE DU NOMBRE DE DAMES (et donc de la taille du damier)
		 System.out.println("1 > Veuillez saisir le nombre de Reines :");
		 String str = saisie.nextLine();
		 
		 if(Integer.valueOf(str).intValue() > valMax) { 
			 valBig = true;
		 }	//Valeur trop grande
		 
		 /* TODO 
		  // GESTION DES CARACTERES ALPHANUMERIQUE :
		   saisie.findInLine("\\s*[a-z]\\s*");
		   MatchResult result = saisie.match();
		   if (result.groupCount() > 1)
		  	 valAlpha = true;	     
		  */   
		 
		// SAISIE DES PARAMETRES
		 System.out.println("\n2 > Souhaitez vous stocker les solutions dans un ArrayList ?");
		 String stockage = saisie.nextLine();
		 Dames.setstock_data(Shell.ConvertStringToBool(stockage));
		 
		 System.out.println("\n3 > Souhaitez vous afficher les données sous forme de grille ?");
		 String grille = saisie.nextLine();
		 Dames.setview_grille(Shell.ConvertStringToBool(grille));
		 
		 if (Dames.getview_grille() == false) {
			 System.out.println("\n4 > Souhaitez vous alors afficher les données sous forme textuelle ?");
			 String texte = saisie.nextLine();
			 Dames.setview_data(Shell.ConvertStringToBool(texte));
		 }
		 
		 // FEATURES
		 Dames.setDamier(Integer.parseInt(str)); 		 
		 System.out.println("\nLa taille du damier sera donc de : " + str + " x " + str);
		 System.out.println("_____________________________________________________________");
		 System.out.println("RECHERCHE DES SOLUTIONS EN COURS..."); 
		 
		 chronoinit = System.nanoTime(); //On réinitialise de chrono pour lancer le test !
	}
	
	protected static int arbre_calcul(int count) {
		// VARIABLES LOCALES
		 valeurX[0] = -1;	// Utilisation d'un tableau pour stocker sur l'axe [Y] la position de l'axe =X  (position des Reines)
		 int posY = 0;		// Positionnement sur l'axe Y
		 
		 // ARBRE DES POSSIBILITES
		 while (posY >= 0) {					/* Tant que la position Y est superieur ou egale à 0 */
			  
			 /* Utilisation d'un Do...While pour permettre l'execution AU MINIMUM UNE FOIS (si un false sort...) 
			  -> Tant que X (stocké dans valeurX[posY]) ne depasse pas la taille du damier
			   	 ET tant que la fonction Verification renvoi une valeur true (position menacante) */
			  do {
				  count++;
				  valeurX[posY]++;				/* Alors on incremente immediatement la position de X */
			  } while ((valeurX[posY] < Dames.getDamier()) && Verification(posY));
			  if (valeurX[posY] < Dames.getDamier()) {		/* Si X est inferieur a la taille du damier */	    	  
				  if (posY < Dames.getDamier()-1 ) {		/* Si Y est inferieur a la taille du damier (-1 car Y debute 0) */
					  valeurX[++posY] = -1;		/* -> on continu de chercher une solution correcte (si il y a) */
				  } else { 
					  ++numSol; 					  
					  Affichage();					  			  
				  }		/* -> Sinon on affiche la solution trouvée */
			  } else {	posY--;	}				/* -> Sinon on decremente la position de Y pour reefectuer d'autres calculs,
												  	  mais si on arrive a Y = -1 (aucune autre solution trouvée, alors on quitte la boucle */
		 
		 }
		return count;
	}

	
	/* Fonction heuristique permettant de determiner si la position d'une Dame est possible ou non */
	static boolean Verification(int posY) {
		int posX = valeurX[posY];					/* On recupere l'emplacement X situé a l'index Y		
		/* On parcourt ensuite le tableau ( i permet de gerer aussi les diagonales ) */
		for (int i = 1; i <= posY; i++) {			/* On check colonne par colonne */		
			int posPrec = valeurX[posY - i];		/* et ligne par ligne ( i permet de gerer les diagonales ) */	      
			if (posPrec == posX	||	posPrec == posX - i	||	posPrec == posX + i) {	
				return true;	
			} /* Si la position est menacante */
		} 
		return false;
	}
	

	/* Methode permettant l'affichage (sous forme d'une grille) et/ou stockage d'une solution  */
	public static void Affichage() {
		char ASCII = '0';				// Permet de stocker les lettres de colonnes
		Solutions.add(""); 		// Création d'un élément vide dans l'ArrayList
		chronostart = System.nanoTime();
		
		if (Dames.getview_grille() == true || Dames.getstock_data() == true || Dames.getview_data() == true) { 			
			for (int posY = 0; posY < Dames.getDamier(); posY++) {
				if (Dames.getview_grille() == true) { 
					// Affichage du numéro de solution
					if (posY == 0)	{
						System.out.print("\nSolution n°"+numSol+" : \n");
					}
					// AFFICHAGE DES NUMEROS DE LIGNES
					if (Dames.getDamier() >= 10)
						if (posY+1 < 10)
							System.out.print("0"+posY+1);
					else
						System.out.print(posY+1);
				}
				for (int posX = 0; posX < Dames.getDamier(); posX++) {		
					// AFFICHAGE DES DAMES ET DE LA GRILLE
					if (Dames.getview_grille() == true) { 
						// Si la valeur X est identique on affiche |D sinon |_ 
						System.out.print((valeurX[posY] == posX) ? "|¤" : "|_"); 
					}
					
					// STOCKAGE DE LA SOLUTION
					if (Dames.getstock_data() == true || Dames.getview_data() == true) {
						if (valeurX[posY] == posX) {	// Si on trouve une Reine
							ASCII = (char)(posX+65);	// COLONNE

							//STOCKAGE
							//On modifie le dernier ajout, en y ajoutant le contenu precedent+la nouvelle coordonnée
							// Solutions.set((numSol-1),Solutions.get(numSol-1)+Character.toString(ASCII)+(posY+1));
							// Solutions.set((numSol-1),Character.toString(ASCII)+(posY+1));
							
							// AFFICHAGE TEXTUEL
							if (Dames.getview_data() == true) {
								// On modifie le dernier element pour remplacer par le derneir element
								Solutions.set((numSol-1),Character.toString(ASCII)+(posY+1));
								// Features
								if (posY == 0)	{
									System.out.print("Solution n°"+numSol+" : ");
								}
								if (posY > 0)	{
									System.out.print("-");
								}
								
								// Affichage par le biai de l'ArrayList
								System.out.print(Solutions.get(numSol-1));
								
								// Affichage par recupération de variables locales
								//System.out.print(Character.toString(ASCII)+(posY+1));								
							} else {
								// On modifie le dernier element pour ajouter le derneir element
								Solutions.set((numSol-1),Solutions.get(numSol-1)+Character.toString(ASCII)+(posY+1));
							}
							
						}
					}						
				}
				if (Dames.getview_grille() == true) { 
					System.out.println("|");	// < Affichage fin quadrillage et retour de chariot
				}					
			}//endfor
			
			if (Dames.getview_grille() == true) { 
				if (Dames.getDamier() >= 10)
					System.out.print("   ");
				else
					System.out.print("  ");
				for (int posX = 0; posX < Dames.getDamier(); posX++) {
					// AFFICHAGE DES LETTRES DE COLONNES
					ASCII = (char)(posX+65);
					System.out.print(ASCII+" ");
				}
				// AFFICHAGE BENCHMARK INTERMEDIAIRE
				dureeEnNs = (System.nanoTime()-chronostart);
				System.out.println("\nSolution élucidée en : "+ dureeEnNs +" nanosecondes "
								 + "("+ TimeUnit.MILLISECONDS.convert(dureeEnNs, TimeUnit.NANOSECONDS) +" ms)"); // AFFICHAGE BENCHMARK
			}
			
			if (Dames.getview_data() == true) { 
				System.out.println("");	// < Affichage fin quadrillage et retour de chariot
			} /*else {
				// TODO : Affichage uniquement pour phase de test :
				// ICI ON AFFICHE UNIQUEMENT LA LISTE QUAND ELLE EST FINIE
				System.out.print(Solutions.get(numSol-1)+"\n");
			}*/
		}//endif		
	}  	
	
	private static void Benchmark_Final(int possibilite) {
		// AFFICHAGE BENCHMARK FINAL		 
		 chronoinit = (System.nanoTime()-chronoinit);
		 
		 System.out.println("_____________________________________________________________");
		 System.out.println("BENCHMARK"); 
		 System.out.println("\n> Résolution complète en "+ chronoinit +" nanosecondes "
				 		  + "("+ TimeUnit.MILLISECONDS.convert(chronoinit, TimeUnit.NANOSECONDS) +" ms)");
		 System.out.println("> Pour un damier d'une taille de "+Dames.getDamier()+" x "+Dames.getDamier()+", \n"
		 				  + "  il existe "+ numSol +" solutions possibles de  \n"
		 				  + "  positionner correctement les "+Dames.getDamier()+" Reines !");
		 System.out.println("  Possibilités explorées : "+possibilite+" (Recherche en profondeur)");
		 
		 
		 long arrangements = ((Dames.getDamier()*Dames.getDamier())-Dames.getDamier()+1);						
		 for (long i = (((Dames.getDamier()*Dames.getDamier()) - Dames.getDamier())+2); i <= (Dames.getDamier()*Dames.getDamier()); i++)											
		 {
			 arrangements = arrangements * i;
		 }
		 System.out.println("  Arrangements possibles : "+arrangements+" (Algorithme recursif)");
		 System.out.println("  Placements possibles : "+Math.pow(Dames.getDamier()*Dames.getDamier(), Dames.getDamier())+" (Recherche exhaustive)");
	}
	
	protected static void File_Write(int count) throws IOException {
		// SAUVEGARDE DES RESULTATS
		try
		{		
			System.out.println("\nCopie en cours des données dans le fichier "+fichier+"..."); 
			FileWriter fw = new FileWriter (fichier);	
			fw.write ("__________"+Dames.getDamier()+" REINES__________\r\n");
			fw.write ("Damier de "+Integer.toString(Dames.getDamier())+"x"+Integer.toString(Dames.getDamier())
					+ " (pour "+Integer.toString(Dames.getDamier())+" Reines)\r\n"
					+ "> Résolution complète en "+ chronoinit +" nanosecondes "
					+ "("+ TimeUnit.MILLISECONDS.convert(chronoinit, TimeUnit.NANOSECONDS) +" ms)\r\n"					
					+ "> Il existe "+ numSol +" solutions possibles :\r\n"	);
			for (String liste : Solutions) {
			    fw.write (liste +"\r\n");
			}
			fw.write ("____________________________\r\n");
			fw.close();
			System.out.println("Fin de la copie des données !\n"); 
		}
		catch (IOException exception)
		{
		    System.out.println ("Erreur lors de la lecture : " + exception.getMessage());
		}
	}
  //endregion
}