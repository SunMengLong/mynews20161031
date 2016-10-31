package xn.bwie.com.sunmenglongnews.utils;

import android.util.Log;

/**
 * Created by Pooh on 2016/11/1.
 */
public class logUtils {
    public static final  boolean isDebug=true;
    public static void i(String TAG,String info){
        if(isDebug){
            Log.i(TAG,info);
        }
    }
}
