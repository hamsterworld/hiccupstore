package hiccup.hiccupstore.commonutil;

public enum ProductImageType {
    // MySQL에서의 Enum은 다른 DBMS와의 호환이 되지 않기 때문에
    // Column Type은 varchar(10)으로 설정되어 있음을 주의하세요.
    PRODUCT, DETAIL
}
