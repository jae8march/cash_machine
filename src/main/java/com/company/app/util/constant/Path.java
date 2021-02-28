package com.company.app.util.constant;

/**
 * Class with path.
 */
public final class Path {
    private Path() { }

    /** Jsp-files. */
    public static final String INDEX = "/index.jsp";
    public static final String ERROR = "/error.jsp";

    public static final String LOGIN = "/jsp/user/login.jsp";
    public static final String REGISTRATION = "/jsp/user/register.jsp";

    public static final String MANAGER = "/jsp/user/manager.jsp";
    public static final String CASHIER = "/jsp/user/cashier.jsp";
    public static final String CHIEF_CASHIER = "/jsp/user/chiefCashier.jsp";
    public static final String ADMIN = "/jsp/user/admin.jsp";

    public static final String ALL_PRODUCT = "/jsp/product/allProduct.jsp";
    public static final String NEW_PRODUCT = "/jsp/product/newProduct.jsp";

    public static final String ALL_CHECKS = "/jsp/check/allCheck.jsp";
    public static final String DETAILS_CHECK = "/jsp/check/detailsCheck.jsp";

    public static final String REPORT = "/jsp/report/report.jsp";

    public static final String ALL_USERS = "/jsp/admin/allUsers.jsp";

    /** Command */

    /** Manager commands*/
    public static final String C_MANAGER = "/api?action=manager";
    public static final String C_LIST_PRODUCT = "/api?action=listProduct";
    /** Chief Cashier commands*/
    public static final String C_CHIEF_CASHIER = "/api?action=chiefCashier";
    public static final String C_LIST_REPORT = "/api?action=listReport";
    /** Cashier commands*/
    public static final String C_CASHIER = "/api?action=cashier";
    /** Cashier and Chief Cashier commands*/
    public static final String C_DETAILS_CHECK = "/api?action=detailsCheck";
    public static final String C_LIST_CHECK = "/api?action=listCheck";
    /** Admin commands*/
    public static final String C_ADMIN = "/api?action=admin";
    public static final String C_LIST_USER = "/api?action=listUsers";
}
