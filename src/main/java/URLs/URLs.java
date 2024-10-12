package URLs;

import org.openqa.selenium.By;

public class URLs {

    public static String mainURL = "https://sutrastores.com/en/";
    public static String register = "https://sutrastores.com/en/account/register";
    public static String facebook = "https://www.facebook.com/SutraStores/";
    public static String instagram = "https://www.instagram.com/sutra_stores/";
    public static String tik_tok = "https://www.tiktok.com/@sutra_stores";
    public static String twitterX = "https://x.com/sutra_stores?mx=2";
    public static String twitterX_register = "https://x.com/i/flow/login?redirect_after_login=%2Fsutra_stores";
    public static String forgetPassword = "https://sutrastores.com/en/account/login#recover";
    public static String searchItem (String itemToSearch){
        return String.format("https://sutrastores.com/en/search?q=%s&options%%5Bprefix%%5D=last&type=product", itemToSearch) ;
    }

    public static String searchproduct (String product){
        return String.format("https://sutrastores.com/en/search?q=%s*&type=product", product);
    }






}
