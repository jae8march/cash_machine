package com.company.app.util.constant;

/**
 * Column names from tables.
 */
public final class Column {
    private  Column() { }

    /** Table 'user' */
    public static final String USER_ID = "user_id";
    public static final String USER_NAME = "user_name";
    public static final String USER_SURNAME = "user_surname";
    public static final String USER_LOGIN = "user_login";
    public static final String USER_PASSWORD = "user_password";
    public static final String USER_ROLE = "role";

    /** Table 'product' */
    public static final String PRODUCT_CODE = "product_code";
    public static final String PRODUCT_NAME = "product_name";
    public static final String PRODUCT_PRICE = "product_price";
    public static final String PRODUCT_QUANTITY = "product_quantity";
    public static final String PRODUCT_WEIGHT = "product_weight";
    public static final String PRODUCT_HOW_SOLD = "product_weightSold";

    /** Table 'checks' */
    public static final String CHECK_ID = "checks_id";
    public static final String CHECK_DATA = "checks_date";
    public static final String CHECK_STATUS = "checks_status";
    public static final String CHECK_LOGIN = "user_login";
    public static final String CHECK_PRICE = "checks_price";

    /** Table 'product_check' */
    public static final String CHECK_PRODUCT_QUANTITY = "product_check_quantity";
    public static final String CHECK_PRODUCT_WEIGHT = "product_check_weight";
    public static final String CHECK_PRODUCT_PRICE = "check_product_price";

    /** Table 'report' */
    public static final String REPORT_ID = "report_id";
    public static final String REPORT_TYPE = "report_type";
    public static final String REPORT_DATA = "report_date";
    public static final String REPORT_BEFORE = "report_cash_before";
    public static final String REPORT_AFTER = "report_cash_after";
    public static final String REPORT_TOTAL = "report_total";
}