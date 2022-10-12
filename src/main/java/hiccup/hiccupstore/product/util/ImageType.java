package hiccup.hiccupstore.product.util;

public enum ImageType {
    PRODUCT("PRODUCT"),
    DETAIL("DETAIL");

    private final String valueString;

    ImageType(String valueString) {
        this.valueString = valueString;
    }
    public String getValue() {
        return this.valueString;
    }
}
