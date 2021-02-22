package com.company.app.util.filter;
//TODO WORK
import com.company.app.dao.entity.User;
import com.company.app.dao.entity.enumeration.UserRole;
import com.company.app.util.constant.Path;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AuthenticationFilter implements Filter {
    public static final Logger LOG= Logger.getLogger(AuthenticationFilter.class);
    private static final Map<UserRole, List<String>> mapConfig = new HashMap<>();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        List<String> urlManager = new ArrayList<>();
        urlManager.add(Path.MANAGER);
        urlManager.add(Path.NEW_PRODUCT);
        urlManager.add(Path.ALL_PRODUCT);
        mapConfig.put(UserRole.MANAGER, urlManager);

        List<String> urlCashier = new ArrayList<>();
        urlCashier.add(Path.ALL_CHECKS);
        urlCashier.add(Path.CASHIER);
        urlCashier.add(Path.DETAILS_CHECK);
        mapConfig.put(UserRole.CASHIER, urlCashier);

        List<String> urlChiefCashier = new ArrayList<>();
        urlCashier.add(Path.ALL_CHECKS);
        urlCashier.add(Path.CHIEF_CASHIER);
        urlCashier.add(Path.DETAILS_CHECK);
        urlCashier.add(Path.REPORT);
        mapConfig.put(UserRole.CHIEF_CASHIER, urlChiefCashier);

    }

//    public static Set<String> getAllAppRoles() {
//        return mapConfig.keySet();
//    }
//
    public static List<String> getUrlPatternsForRole(UserRole role) {
        return mapConfig.get(role);
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
//        User user = (User) ((HttpServletRequest) req).getSession().getAttribute("user");
//        HttpServletRequest request = (HttpServletRequest) req;
//        HttpServletResponse response = (HttpServletResponse) resp;
//        String errorUserUrl = request.getContextPath() + Path.ERROR;
//
//        String reUrl = request.getRequestURL().toString();
//
//        Pattern patterncLogin = Pattern.compile("(login)");
//        Matcher matcherLogin = patterncLogin.matcher(reUrl);
//
//        Pattern patternReg = Pattern.compile("(register)");
//        Matcher matcherReg = patternReg.matcher(reUrl);
//
//        if (matcherLogin.find()||matcherReg.find()) {
//            chain.doFilter(request, response);
//            return;
//        }
//
//        String url =reUrl.replaceAll("/", "-");
//
//        //(chiefCashier|cashier|check|manager|jsp/product)
/////jsp/product/
//        //(chiefCashier|cashier|check|manager|jsp-product)
//
//
//        String role = String.valueOf(user.getUserRole());
////
//        Pattern patterncChiefCashier = Pattern.compile("(chiefCashier)");
//        Matcher matcherChiefCashier = patterncChiefCashier.matcher(url);
//
//        Pattern patternCashier = Pattern.compile("(cashier)");
//        Matcher matcherCashier = patternCashier.matcher(url);
//
//        Pattern patternCheck = Pattern.compile("(check)");
//        Matcher matcherCheck = patternCheck.matcher(url);
//
//        Pattern patternManager = Pattern.compile("(manager)");
//        Matcher matcherManager = patternManager.matcher(url);
//
//        Pattern patternProduct = Pattern.compile("(jsp-product)");
//        Matcher matcherProduct = patternProduct.matcher(url);
//
//        if((matcherChiefCashier.find() || matcherCheck.find()) && role.equalsIgnoreCase("chiefCashier")){
//            chain.doFilter(req, resp);
//        } else if ((matcherCashier.find() ||matcherCheck.find()) && role.equalsIgnoreCase("cashier")){
//            chain.doFilter(req, resp);
//        } else if ((matcherManager.find() || matcherProduct.find()) && role.equalsIgnoreCase("manager")){
//            chain.doFilter(req, resp);
//        } else {
//            response.sendRedirect(errorUserUrl);
//            chain.doFilter(req, resp);
//        }
//
//
//
//
////
////        String loginURI = request.getContextPath() + Path.INDEX;
////        String path = request.getRequestURI();
////        String roleRequired = "";
////
////        if (path.contains("manager")) roleRequired = "manager";
////        else if (path.contains("cashier")) roleRequired = "cashier";
////        else if (path.contains("chiefCashier")) roleRequired = "chiefCashier";
////
//////        if (null==user || null==user.getUserRole()){
//////            response.sendRedirect(loginURI);
//////        }
//////
//////        List<String> list = getUrlPatternsForRole(user.getUserRole());//
//////        if(!list.contains(path)){
//////            response.sendRedirect(loginURI);
//////        }
////
////
////        switch (roleRequired) {
////            case "manager":
////                if (user != null && (user.getUserRole() == UserRole.MANAGER)) {
////                    chain.doFilter(req, resp);
////
////                } else {
////                    response.sendRedirect(loginURI);
////                }
////                break;
////            case "chiefCashier":
////                if (user != null && user.getUserRole() == UserRole.CHIEF_CASHIER) {
////                    chain.doFilter(req, resp);
////                } else {
////                    response.sendRedirect(loginURI);
////                }
////                break;
////            case "cashier":
////                if (user != null && (user.getUserRole() == UserRole.CASHIER)) {
////                    chain.doFilter(req, resp);
////                } else {
////                    response.sendRedirect(loginURI);
////                }
////                break;
////            default:
////                chain.doFilter(req, resp);
////        }
    }

    @Override
    public void destroy() {
    }
}
