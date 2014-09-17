package ischool.dsa.utility.version;

public class VersionPeriod {
	private String _period;
	private VersionPeriodType _type;
	private int _number;

	public VersionPeriod(String period) {
		_period = period;

		try {
			_number = Integer.parseInt(period);
			_type = VersionPeriodType.NUMBER;
		} catch (Exception ex) {
			_type = VersionPeriodType.STRING;
		}
	}

	public VersionPeriodType getType() {
		return _type;
	}

	public int getNumber() {
		return _number;
	}

	public String getPeriod() {
		return _period;
	}

	public int compareTo(VersionPeriod another) {
		if (_type == VersionPeriodType.NUMBER
				&& another.getType() == VersionPeriodType.NUMBER) {
			if (_number > another.getNumber())
				return 1;
			if (_number < another.getNumber())
				return -1;
			return 0;
		}

		if (_type == VersionPeriodType.STRING
				&& another.getType() == VersionPeriodType.STRING) {
			return _period.compareTo(another.getPeriod());
		}

		if (_type == VersionPeriodType.NUMBER
				&& another.getType() == VersionPeriodType.STRING) {
			return -1;
		}

		if (_type == VersionPeriodType.STRING
				&& another.getType() == VersionPeriodType.NUMBER) {
			return 1;
		}

		return 0;
	}
}
