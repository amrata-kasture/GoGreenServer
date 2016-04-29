package exception;

import java.util.Scanner;

/**This is Helper Class
 * @author Amrata Kasture
 *
 */
public class FixPlantFile {
	ErrorTracker et;
	
	//This method fixes missing OptionSetName through User Input
	public String fixFormat(int en){
		et = ErrorTracker.INCOMPATIBLE_FILE_FORMAT;
		System.out.println(et.getDescription());
		System.out.println("Recheck the File and enter the missing PropertyName below:");
		Scanner sc = new Scanner(System.in);
		return sc.next();
	}
	
	public String fixPath(int en){
		et = ErrorTracker.INCOMPATIBLE_FILE_FORMAT;
		System.out.println(et.getDescription());
		System.out.println("Recheck the File and enter the missing PropertyName below:");
		Scanner sc = new Scanner(System.in);
		return sc.next();
	}
	public String fixIO(int en){
		et = ErrorTracker.INCOMPATIBLE_FILE_FORMAT;
		System.out.println(et.getDescription());
		System.out.println("Recheck the File and enter the missing PropertyName below:");
		Scanner sc = new Scanner(System.in);
		return sc.next();
	}
	
	public String fixAccess(int en){
		et = ErrorTracker.INCOMPATIBLE_FILE_FORMAT;
		System.out.println(et.getDescription());
		System.out.println("Recheck the File and enter the missing PropertyName below:");
		Scanner sc = new Scanner(System.in);
		return sc.next();
	}
}
