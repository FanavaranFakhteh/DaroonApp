package com.daroonapp.library;

public class Urls {
    public static String baseUrl ="https://daroonapp.com/api/v1/";
    public static String makePayment =  baseUrl + "payments/make";
    public static String zarinPayment = baseUrl + "payments/ipg/";
    public static String failTransaction = baseUrl + "payments/application/event/";
    public static String trackingCode = baseUrl + "payments/verify/";
    public static String allTransactions = baseUrl + "payments/user/get";
}