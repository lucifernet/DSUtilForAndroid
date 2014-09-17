package ischool.dsa.utility;

import java.util.List;

import org.w3c.dom.Element;

public class PropertyUtil {
	public static String toSimpleElementString(Element e) {
		StringBuilder sb = new StringBuilder();

		for (Element sub : XmlUtil.selectElements(e)) {
			if (sb.length() > 0)
				sb.append(';');

			List<Element> subs = XmlUtil.selectElements(sub);

			if (subs.size() == 0)
				sb.append(sub.getNodeName()).append(":").append(
						sub.getTextContent().trim());
			else {
				String str = toSimpleElementString(sub);
				sb.append(str);
			}
		}
		return sb.toString();
	}
}
