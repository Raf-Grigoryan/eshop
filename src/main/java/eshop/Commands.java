package eshop;

public interface Commands {
    String EXIT = "0";
    String ADD_CATEGORY = "1";
    String REMOVE_CATEGORY = "2";
    String ADD_PRODUCT = "3";
    String EDIT_PRODUCT_BY_ID = "4";
    String DELETE_PRODUCT_BY_ID = "5";
    String PRINT_PRODUCT_COUNT = "6";
    String PRINT_PRODUCT_WITH_MIN_PRICE = "7";
    String PRINT_PRODUCT_WITH_MAX_PRICE = "8";
    String AVERAGE_PRICE = "9";
    String PRINT_ALL_PRODUCTS = "10";
}
