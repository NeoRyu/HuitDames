// SHELL : CLASSE PROTEGEANT L'APPLICATION ET/OU PERMETTANT D'EFFECTUER DES PROCESS RECURENTS
//import java.io.IOException;
import java.util.Date;
import java.util.Calendar;
import java.text.DateFormat;
import java.text.SimpleDateFormat;


public class Shell {
	public static void CLS() {  
		for(int clear = 0; clear < 100000; clear++)
		{
		     System.out.println("\r\n") ;
		}
	}  
	
	public static String Sysdate() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		return dateFormat.format(date);
	}
	
	public static boolean ConvertStringToBool(String s) {
		// FONCTION DE CONVERSION D'UN STRING EN BOOLEAN (avec gestion de mauvaises données)
		s = s.toUpperCase();
		if (s.equals("TRUE") || s.equals("OUI") || s.equals("YES") || s.equals("1")) {
			return true;			  
		}
		else return false;
	}
}
