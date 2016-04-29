package exception;

/**
 * Enumeration for storing Error Numbers and Error Messages
 * @author Amrata Kasture
 *
 */

public enum ErrorTracker {
	WRONG_FILE_PATH(1,"The given File location is not found."),
	IO_ISSUE(2,"Input/Output Operations are disabled or Interrupted on the Input File."),
	INCOMPATIBLE_FILE_FORMAT(3,"Input File format is incompatible."),
	FILE_ACCESS_DENIED(4,"Access is denied on this File."),
	VALUE_MISSING(5,"Value missing for valid Green Entry."),
	DB_EXCEPTION(6,"DB Connectivity issue occured.");
	
	  private final int code;
	  private final String description;

	  private ErrorTracker(int code, String description) {
	    this.code = code;
	    this.description = description;
	  }

	  public String getDescription() {
	     return description;
	  }

	  public int getCode() {
	     return code;
	  }

	  @Override
	  public String toString() {
	    return code + ": " + description;
	  }
}
