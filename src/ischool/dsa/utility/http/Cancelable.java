package ischool.dsa.utility.http;

public class Cancelable {

	private boolean _cancel = false;
	
	public boolean isCanceled(){
		return _cancel;
	}
	
	public void setCancel(boolean cancel){
		_cancel = cancel;
	}
}
