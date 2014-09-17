package ischool.dsa.utility.dad;


public class DADName {
	private String _fullname;
	private String _appName;
	private String _contractName;

	public DADName(String fullname) {
		_fullname = fullname;

		String lowName = _fullname.toLowerCase();
				
		if (lowName.startsWith("http://") || lowName.startsWith("https://")) {
			while(fullname.endsWith("/"))
			{				
				fullname = fullname.substring(0,fullname.length()-1);				
			}
			_contractName = fullname.substring(fullname.lastIndexOf("/")+1,fullname.length());
			_appName = fullname.substring(0, fullname.lastIndexOf("/")+1);
		} else {
			String[] arr = fullname.split("/");
			_appName = arr[0];
			_contractName = "";

			if (arr.length > 1) {
				_contractName = arr[arr.length - 1];
			}
		}
	}

	public String getApplicationName() {
		return _appName;
	}

	public String getContractName() {
		return _contractName;
	}

	public String getFullName() {
		return _fullname;
	}

	public static String getApplicationName(String fullname) {
		DADName name = new DADName(fullname);
		return name.getApplicationName();
	}

	public static String getContractName(String fullname) {
		DADName name = new DADName(fullname);
		return name.getContractName();
	}
	
	public static void main(String[] args){
		DADName dad = new DADName("http://web.ischool.com.tw/service/shared/user");
		System.out.println("application : " + dad.getApplicationName());
		System.out.println("contract : " + dad.getContractName());
	}
}
