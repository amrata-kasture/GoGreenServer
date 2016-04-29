package exception;

import java.util.Scanner;

public class FixGreenEntry {
	
ErrorTracker et;
	
	//This method fixes missing BasePrice through User Input
	public String fixValueMissing(int en){
		et = ErrorTracker.VALUE_MISSING;
		System.out.println(et.getDescription());
		System.out.println("Provide the missing value:");
		Scanner sc = new Scanner(System.in);
		return sc.next();
	}
	
	//This method fixes missing Option Values through User Input
	public String  fixDBException(int en){
		et = ErrorTracker.DB_EXCEPTION;
		System.out.println(et.getDescription());
		System.out.println("Test DB Connection with server and try again");
		//Scanner sc = new Scanner(System.in);
		//return sc.nextLine();
		return "DB Fixed";
	}
	
	
}