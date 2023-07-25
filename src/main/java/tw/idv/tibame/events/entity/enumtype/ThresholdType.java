package tw.idv.tibame.events.entity.enumtype;

public enum ThresholdType {
	FULL_PURCHASE("1", "滿額"), QUANTITY_PURCHASE("2", "滿件"), BOTH("3", "滿件且滿額");

	private final String value;
	private final String description;

	ThresholdType(String value, String description) {
		this.value = value;
		this.description = description;
	}

	public String getValue() {
		return value;
	}

	public String getDescription() {
		return description;
	}
}
