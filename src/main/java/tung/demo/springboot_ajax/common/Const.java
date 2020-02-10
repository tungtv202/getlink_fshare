package tung.demo.springboot_ajax.common;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Const {
    public static String FSHARE_SESSION_ID;
    public static String FSHARE_TOKEN;

    public static final String FSHARE_ENDPOINT_LOGIN = "https://api.fshare.vn/api/user/login";
    public static final String FSHARE_ENDPOINT_GETLINK = "https://api.fshare.vn/api/session/download";

    public static String getDate(){
        String pattern = "yyyy-MM-dd mm:ss";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        return simpleDateFormat.format(new Date());
    }
}
