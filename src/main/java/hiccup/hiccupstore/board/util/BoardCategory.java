package hiccup.hiccupstore.board.util;

public enum BoardCategory {
    USER("회원/정보관리",1),
    ORDER("주문/결제",2),
    DELIVERY("배송",3),
    PRODUCT("반품/환불/교환/AS",4),
    ETC("기타",5);
    private final String valueString;
    private final int valueNum;

    BoardCategory(String valueString, int valueNum) {
        this.valueString = valueString;
        this.valueNum = valueNum;
    }
    public String getValueString() {
        return this.valueString;
    }
    public int getValueNum() {
        return this.valueNum;
    }
}
