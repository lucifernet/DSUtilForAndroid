package ischool.dsa.internal;

import java.util.HashMap;

import javax.xml.XMLConstants;
import javax.xml.transform.dom.DOMSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.w3c.dom.Element;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class ValidProvider {

	// private static SchemaFactory factory;

	// private static Hashtable<String, Validator> validators;

	private static HashMap<String, Schema> schemas;

	static {
		SchemaFactory factory = SchemaFactory
				.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		// validators = new Hashtable<String, Validator>();
		schemas = new HashMap<String, Schema>();

		try {
			Schema s = factory.newSchema(ValidProvider.class
					.getResource("Envelope.xsd"));
			schemas.put("Envelope.xsd", s);

			s = factory.newSchema(ValidProvider.class
					.getResource("RequestHeader.xsd"));
			schemas.put("RequestHeader.xsd", s);

			s = factory.newSchema(ValidProvider.class
					.getResource("ResponseHeader.xsd"));
			schemas.put("ResponseHeader.xsd", s);

			s = factory.newSchema(ValidProvider.class.getResource("Fault.xsd"));
			schemas.put("Fault.xsd", s);
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 驗證 DSA 3.0 的 Envelope 基本規格。
	 * 
	 * @param content
	 *            包含 Header、Body 的 Xml 內容。
	 * @return 驗證結果，如果回傳 Null 代表驗證成績，非 Null 代表有錯誤發生。
	 */
	public static SAXParseException validateEnvelope(Element content) {
		return validate(content, "Envelope.xsd", true);
	}

	public static SAXParseException validateRequestHeader(Element content) {
		return validate(content, "RequestHeader.xsd", true);
	}

	public static SAXParseException validateResponseHeader(Element content) {
		return validate(content, "ResponseHeader.xsd", true);
	}

	public static SAXParseException validateFault(Element content) {
		return validate(content, "Fault.xsd", true);
	}

	/**
	 * 用指定的 Schema 驗證 Element。
	 * 
	 * @param content
	 *            要驗證的 Element。
	 * @param schemaName
	 *            要使用的 Schema 名稱。
	 * @return 驗證結果，如果回傳 Null 代表驗證成績，非 Null 代表有錯誤發生。
	 */
	private static SAXParseException validate(Element content,
			String schemaName, boolean reparse) {
		try {

			Validator vator = getValidator(schemaName);

			ValidErrorHandler objErrorHandler = new ValidErrorHandler();
			vator.setErrorHandler(objErrorHandler);

			Element input = content;
			if (reparse) {
				// 進行 Reparse 動作，如果 Dom 在載入記憶體後，有用 API 改過內容，不會反應到
				// Validator中，不知道為什麼...
				// input =
				// XmlHelper.parseXml(XmlHelper.convertToString(content));
				input = (Element) content.getOwnerDocument()
						.getDocumentElement().cloneNode(true);
			}

			vator.validate(new DOMSource(input));

			if (objErrorHandler.getException() != null)
				return objErrorHandler.getException();
			else
				return null;

		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	/**
	 * 管理所有用到的 Validator ，如果 Validator 不存在，會自動建立。
	 * 
	 * @param name
	 *            Validator 名稱，必須使用檔案當作名稱。
	 * @return Validator 實體。
	 * @throws Exception
	 *             如果載入 Schema 錯誤，則會產生。
	 */
	private static synchronized Validator getValidator(String name)
			throws Exception {
		Schema s = schemas.get(name);
		return s.newValidator();

		// if (validators.containsKey(name))
		// return validators.get(name);
		//
		// else {
		//
		// Schema sch = factory.newSchema(ValidProvider.class
		// .getResource(name));
		//
		// Validator vator = sch.newValidator();
		// validators.put(name, vator);
		//
		// return vator;
		// }
	}

	// public static void main(String[] args) throws SAXException {
	// long t1 = System.currentTimeMillis();
	//
	// SchemaFactory f = SchemaFactory
	// .newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
	//
	// long t2 = System.currentTimeMillis();
	// System.out.println(t2 - t1);
	//
	// Schema sch = f.newSchema(ValidProvider.class
	// .getResource("Envelope.xsd"));
	//
	// long t3 = System.currentTimeMillis();
	// System.out.println(t3 - t2);
	// Validator v = sch.newValidator();
	//
	// long t4 = System.currentTimeMillis();
	// System.out.println(t4 - t3);
	//
	// Element input = XmlUtil.createElement("Envelope");
	//
	// t4 = System.currentTimeMillis();
	// try {
	// v.validate(new DOMSource(input));
	// } catch (Exception e) {
	// // TODO Auto-generated catch block
	// // e.printStackTrace();
	// }
	//
	// long t5 = System.currentTimeMillis();
	// System.out.println(t5 - t4);
	// }
}
