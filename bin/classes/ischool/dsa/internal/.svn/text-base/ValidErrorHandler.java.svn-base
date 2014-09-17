package ischool.dsa.internal;

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class ValidErrorHandler implements ErrorHandler {

	private SAXParseException _exp = null;

	public void error(SAXParseException arg0) throws SAXException {
		if (_exp == null) // 此方法會被呼叫多次，此判斷式是為了只處理第一次。
			_exp = arg0;
	}

	public void fatalError(SAXParseException arg0) throws SAXException {
		if (_exp == null)
			_exp = arg0;
	}

	public void warning(SAXParseException arg0) throws SAXException {
		if (_exp == null)
			_exp = arg0;
	}

	public SAXParseException getException() {
		return _exp;
	}
}
