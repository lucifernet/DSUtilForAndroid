package ischool.dsa.client;

import ischool.dsa.utility.Converter;
import ischool.dsa.utility.XmlHelper;
import ischool.dsa.utility.XmlUtil;

import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;

import org.w3c.dom.Element;

public class UserAgentProvider {
	public static Element getClientInfoToken() {
		Element e = XmlUtil.createElement("UserAgent");

		String username = System.getProperty("user.name");
		XmlUtil.addElement(e, "User", username);

		try {
			XmlUtil.addElement(e, "LocalMachineTime",
					Converter.toDateString(Calendar.getInstance().getTime()));
			java.net.InetAddress localMachine = java.net.InetAddress
					.getLocalHost();
			XmlUtil.addElement(e, "LocalMachineName",
					localMachine.getCanonicalHostName());
			XmlUtil.addElement(e, "LocalMachineAddress",
					localMachine.getHostAddress());
			XmlUtil.addElement(e, "MacAddress", getMacAddress());
		} catch (UnknownHostException e1) {

		} catch (SocketException e1) {

		}

		try {
			Element n = printIpAddressAndSubnettest();
			String nwString = XmlHelper.convertToString(n);
			XmlUtil.addCDATASection(e, "NetWorkInterface", nwString);
		} catch (Exception ex) {
		}
		return e;
	}

	private static String getMacAddress() throws UnknownHostException,
			SocketException {
		InetAddress addr = InetAddress.getLocalHost();

		NetworkInterface ni = NetworkInterface.getByInetAddress(addr);
		byte[] maca = ni.getHardwareAddress();

		StringBuilder sb = new StringBuilder();
		for (int k = 0; k < maca.length; k++) {
			String s = String.format("%02X%s", maca[k],
					(k < maca.length - 1) ? "-" : "");
			sb.append(s);
			// System.out.format("%02X%s", maca[k], (k < maca.length - 1) ? "-"
			// : "");
		}

		return sb.toString();
	}

	public static void main(String[] args) {
		Element e = getClientInfoToken();
		System.out.println(XmlHelper.convertToString(e, true));	
	}

	@SuppressWarnings("rawtypes")
	public static Element printIpAddressAndSubnettest() {
		Element nwElement = XmlUtil.createElement("NetworkInterfaceList");

		try {
			Enumeration eni = NetworkInterface.getNetworkInterfaces();

			while (eni.hasMoreElements()) {
				Element e = XmlUtil.createElement("NetworkInterface");

				NetworkInterface networkCard = (NetworkInterface) eni
						.nextElement();
				List ncAddrList = networkCard.getInterfaceAddresses();
				Iterator ncAddrIterator = ncAddrList.iterator();
				while (ncAddrIterator.hasNext()) {
					InterfaceAddress networkCardAddress = (InterfaceAddress) ncAddrIterator
							.next();
					InetAddress address = networkCardAddress.getAddress();
					if (!address.isLoopbackAddress()) {
						String hostAddress = address.getHostAddress();

						if (hostAddress.indexOf(":") > 0) {
							// case : ipv6
							continue;
						} else {
							// case : ipv4
							// System.out.println("address = " + hostAddress);
							XmlUtil.addElement(e, "Address", hostAddress);

							String maskAddress = calcMaskByPrefixLength(networkCardAddress
									.getNetworkPrefixLength());

							XmlUtil.addElement(e, "SubNetMask", maskAddress);

							String subnetAddress = calcSubnetAddress(
									hostAddress, maskAddress);

							XmlUtil.addElement(e, "SubNet", subnetAddress);

							String broadcastAddress = networkCardAddress
									.getBroadcast().toString();
							XmlUtil.addElement(e, "Broadcast", broadcastAddress);

							// System.out.println("subnetmask = " +
							// maskAddress);
							// System.out.println("subnet = " + subnetAddress);
							// System.out.println("broadcast = "
							// + broadcastAddress + "\n");
						}
					} else {
						String loopback = networkCardAddress.getAddress()
								.getHostAddress();
						// System.out
						// .println("loopback addr = " + loopback + "\n");
						XmlUtil.addElement(e, "LoopbackAddress", loopback);
					}

					if (XmlUtil.selectFirstElement(e) != null)
						XmlUtil.appendElement(nwElement, e);
				}
				// System.out.println("----- NetworkInterface Separator ----\n\n");

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return nwElement;
	}

	public static String calcMaskByPrefixLength(int length) {
		// System.out.println("Test>" + length);
		int mask = -1 << (32 - length);
		int partsNum = 4;
		int bitsOfPart = 8;
		int maskParts[] = new int[partsNum];
		int selector = 0x000000ff;

		for (int i = 0; i < maskParts.length; i++) {
			int pos = maskParts.length - 1 - i;
			maskParts[pos] = (mask >> (i * bitsOfPart)) & selector;
		}

		String result = "";
		result = result + maskParts[0];
		for (int i = 1; i < maskParts.length; i++) {
			result = result + "." + maskParts[i];
		}
		return result;
	}

	public static String calcSubnetAddress(String ip, String mask) {
		String result = "";
		try {
			// calc sub-net IP
			InetAddress ipAddress = InetAddress.getByName(ip);
			InetAddress maskAddress = InetAddress.getByName(mask);

			byte[] ipRaw = ipAddress.getAddress();
			byte[] maskRaw = maskAddress.getAddress();

			int unsignedByteFilter = 0x000000ff;
			int[] resultRaw = new int[ipRaw.length];
			for (int i = 0; i < resultRaw.length; i++) {
				resultRaw[i] = (ipRaw[i] & maskRaw[i] & unsignedByteFilter);
			}

			// make result string
			result = result + resultRaw[0];
			for (int i = 1; i < resultRaw.length; i++) {
				result = result + "." + resultRaw[i];
			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}

		return result;
	}

}
