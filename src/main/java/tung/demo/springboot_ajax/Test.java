package tung.demo.springboot_ajax;

import java.net.MalformedURLException;
import java.net.URL;

public class Test {

    public static void main(String[] args) throws MalformedURLException {
        String url = "http://download022.fshare.vn/dl/k7E4+vPyVmtn5T6s4hNrpH4yN+ek30KDgWopqp1qyA+fFKqHPLlk9IOflQTmvS3Y8ejlTX0HEsL1tmVc/ipz00508hhb.wmv";

        URL myUrl = new URL(url);
        String subPrefixServer = myUrl.getHost().replace(".fshare.vn", "");
        String reMakeUrl = String.format("http://%s%s", subPrefixServer + ".tungexplorer.me", myUrl.getPath());
        System.out.println(new URL(reMakeUrl));

    }
}
