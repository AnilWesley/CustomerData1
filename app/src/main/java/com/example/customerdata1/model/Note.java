package com.example.customerdata1.model;



public class Note {
    public static final String TABLE_NAME = "product";
    public static final String TABLE_NAME1 = "details";

    public static final String PRODUCT_ID = "id";

    public static final String PRODUCT_NAME = "name";
    public static final String PRODUCT_QUANTITY = "quantity";
    public static final String PRODUCT_PRICE = "price";
    public static final String PRODUCT_TOTAL_PRICE = "total_price";
    public static final String SIZE = "size";
    public static final String GRAND_TOTAL_PRICE = "grand_total";

    private int product_id;
    private String product_name;
    private String product_quantity;
    private String product_price;
    private String product_total_price;
    private String size;
    private String grand_total;


    // Create table SQL query
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + PRODUCT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + PRODUCT_NAME + " TEXT,"
                    + PRODUCT_QUANTITY + " TEXT,"
                    + PRODUCT_PRICE + " TEXT,"
                    + PRODUCT_TOTAL_PRICE + " TEXT"
                    + ")";

    // Create table SQL query
    public static final String CREATE_TABLE1 =
            "CREATE TABLE " + TABLE_NAME1 + "("
                    + PRODUCT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + SIZE + " TEXT,"
                    + GRAND_TOTAL_PRICE + " TEXT"
                    + ")";

    public Note() {
    }


    public Note(int product_id, String size, String grand_total) {
        this.product_id = product_id;
        this.size = size;
        this.grand_total = grand_total;
    }

    public static String getTableName1() {
        return TABLE_NAME1;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getGrand_total() {
        return grand_total;
    }

    public void setGrand_total(String grand_total) {
        this.grand_total = grand_total;
    }

    public Note(int product_id, String product_name, String product_quantity, String product_price, String product_total_price) {

        this.product_id = product_id;
        this.product_name = product_name;
        this.product_quantity = product_quantity;
        this.product_price = product_price;
        this.product_total_price = product_total_price;
    }

    public static String getTableName() {
        return TABLE_NAME;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getProduct_quantity() {
        return product_quantity;
    }

    public void setProduct_quantity(String product_quantity) {
        this.product_quantity = product_quantity;
    }

    public String getProduct_price() {
        return product_price;
    }

    public void setProduct_price(String product_price) {
        this.product_price = product_price;
    }

    public String getProduct_total_price() {
        return product_total_price;
    }

    public void setProduct_total_price(String product_total_price) {
        this.product_total_price = product_total_price;
    }
}
