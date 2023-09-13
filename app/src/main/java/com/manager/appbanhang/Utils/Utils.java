package com.manager.appbanhang.Utils;

import com.manager.appbanhang.model.GioHang;
import com.manager.appbanhang.model.User;

import java.util.ArrayList;
import java.util.List;

public class Utils {
//    public static final String BASE_URL = "http://172.20.10.3/banhang/";
    public static final String BASE_URL = "http://192.168.1.7/banhang/";
    public static List<GioHang> gioHangList;
    public static List<GioHang> muaHangList = new ArrayList<>();


    public static User user = new User();
    public static String ID_RECEIVED;
    public static final String SENDID = "idsend";
    public static final String RECEIVEDID = "idreceived";
    public static final String MESS = "message";
    public static final String DATETIME = "datetime";
    public static final String PATH_CHAT = "chat";
}
