package fleet.logisure.chhavi.tflapp;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by chhavi on 28/8/15.
 */
public class AppPreferences {


    public static void setBasicProfile(Context mContext, String name, String photo) {
        if(name == null || photo == null) {
            return;
        }
        SharedPreferences prefs = mContext.getSharedPreferences("basicProfile", 0);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("name", name);
        editor.putString("photo", photo);
        editor.commit();
    }
    public static void setLoggedInAsTrue(Context mContext) {
        SharedPreferences prefs = mContext.getSharedPreferences("basicProfile", 0);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("loggedin", true);
        editor.commit();
    }
    public static void setLoggedInAsFalse(Context mContext) {
        SharedPreferences prefs = mContext.getSharedPreferences("basicProfile", 0);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("loggedin", false);
        editor.commit();
    }

    public static boolean isLoggedIn(Context mContext) {
        SharedPreferences prefs = mContext.getSharedPreferences("basicProfile", 0);
        return prefs.getBoolean("loggedin", false);
    }
}
