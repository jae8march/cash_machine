package com.company.app.util;

/**
 * Class for pagination, calculation of the next, previous page, offset.
 */
public class Pagination {
    public static final int ROWS = 7;

    private Pagination(){}

    public static long getPage(String pageString){
        long page;
        if (pageString == null || pageString.isEmpty()) {
            page = 1;
        } else {
            page = Long.parseLong(pageString);
        }
        return page;
    }

    public static long getNextPage(String nextPageString, long page){
        long nextPage;
        if ("previous".equals(nextPageString)) {
            nextPage = page - 1;
        } else if ("next".equals(nextPageString)) {
            nextPage = page + 1;
        } else {
            nextPage = page;
        }
        return nextPage;
    }

    public static long getOffset(long lastPage, long page){
        long offset;
        if (lastPage > page) {
            offset = (page - 1) * ROWS;
        } else {
            offset = (lastPage - 1) * ROWS;
        }
        return offset;
    }
}




















