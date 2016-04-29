package exception;

public class GreenEntryException extends Exception {
	
	private static final long serialVersionUID = 1L;
	int errNo = 0;
	String fixMe="";
	
	FixGreenEntry fge = new FixGreenEntry();
    FixPlantFile fpf = new 	FixPlantFile();
	public GreenEntryException(int errNo){
		this.errNo = errNo;
	}
	
	public GreenEntryException(ErrorTracker et){
		this.errNo = et.getCode();
	}
	
	public String wrapFix(int errorNo){
		fix(errNo);
		return fixMe;
	}
	
	public void fix(int errNo){
		switch(errNo){	
		case 1: this.fixMe =  fpf.fixPath(errNo); break;
		case 2: this.fixMe =  fpf.fixIO(errNo);break;
		case 3: this.fixMe =  fpf.fixFormat(errNo); break;
		case 4: this.fixMe =  fpf.fixAccess(errNo); break;
		case 5: this.fixMe =  fge.fixValueMissing(errNo); break;
		case 6: this.fixMe =  fge.fixDBException(errNo); break;
		}
	}
	
	
}