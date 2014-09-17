package ischool.dsa.utility.version;

import java.util.ArrayList;
import java.util.List;

public class Version {
	private String _verString;
	private ArrayList<VersionPeriod> _periods;

	public Version(String version) {
		_verString = version;
		_periods = new ArrayList<VersionPeriod>();

		String[] ps = _verString.split("\\.");
		for (String p : ps) {
			VersionPeriod vp = new VersionPeriod(p);
			_periods.add(vp);
		}
	}

	public List<VersionPeriod> getPeriods() {
		return _periods;
	}

	public int compareTo(String version) {
		Version another = new Version(version);
		return this.compareTo(another);
	}

	public int compareTo(Version another) {
		List<VersionPeriod> anotherPeriods = another.getPeriods();

		int p1size = _periods.size();
		int p2size = anotherPeriods.size();
		int compSize = p1size;
		if (p1size > p2size)
			compSize = p2size;

		for (int i = 0; i < compSize; i++) {
			VersionPeriod period1 = _periods.get(i);
			VersionPeriod period2 = anotherPeriods.get(i);

			int result = period1.compareTo(period2);
			if (result != 0)
				return result;
		}

		if (p1size > p2size)
			return 1;

		if (p2size > p1size)
			return -1;

		return 0;
	}
	
	@Override
	public String toString(){
		return _verString;
	}
}
