package xn.bwie.com.sunmenglongnews.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Process;
import android.view.View;

import xn.bwie.com.sunmenglongnews.application.MyApplication;

/**
 * Created by Pooh on 2016/10/31.
 */
public class commonUtils {
    private static final String TAG="TOPNEWS";
    private static SharedPreferences.Editor editor;
    private static SharedPreferences sh;

    //打气，获取View视图
    public static View getInflate(int id){
        View vv=View.inflate(MyApplication.getContext(),id,null);
        return vv;
    }
    //dip转成px
    public static float dip2px(int dip){
        //获取像素密度
        int density=(int)MyApplication.getContext().getResources().getDisplayMetrics().density;
        int px=(int)(dip*density+0.5f);
        return px;
    }
    //px转成dip
    public static float px2dip(int px){
        //获取像素密度
        int density=(int)MyApplication.getContext().getResources().getDisplayMetrics().density;
        int dip=(int)(px/density+0.5f);
        return dip;
    }
    //根据id查找字符串
    public static String getMyString(int id){
        return  MyApplication.getContext().getResources().getString(id);
    }
    //根据id查找图片资源的方法
    public static Drawable getDrawable(int id){
        return  MyApplication.getContext().getResources().getDrawable(id);
    }
    //文件存储
    public static void saveSp(String tag,String str){
        if(sh==null) {
            sh = MyApplication.getContext().getSharedPreferences(TAG, Context.MODE_PRIVATE);
        }
        //获取写入对象
        editor = sh.edit();
        //存储
        editor.putString(tag,str);
        //提交
        editor.commit();
    }
    //获取存储的文件
    public static String getSp(String tag){
        if(sh==null){
            sh=MyApplication.getContext().getSharedPreferences(TAG,Context.MODE_PRIVATE);
        }
        return  sh.getString(tag,"");
    }
    //运行子线程
    public static void runOnUIThread(Runnable runnable){
        //先判断当前线程是否为主线程
        if(Process.myTid()==MyApplication.getMainId()){
            runnable.run();
        }else{
            //子线程
            MyApplication.getHandler().post(runnable);
        }
    }
    //使用线程池执行Runnable
    public static void executeRunnable(Runnable runnable){
        MyApplication.getExecutorService().execute(runnable);
    }
}
