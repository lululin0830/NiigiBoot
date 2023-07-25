package tw.idv.tibame.events.entity.enumtype;

public enum EventType {
    PRODUCT_COUPON("1", "商品折價券"),
    SITEWIDE_COUPON("2", "全站折價券"),
    PRODUCT_DISCOUNT("3", "商品折扣活動"),
    PRODUCT_GIFT("4", "商品贈品活動");

    private final String value;
    private final String description;

    EventType(String value, String description) {
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

