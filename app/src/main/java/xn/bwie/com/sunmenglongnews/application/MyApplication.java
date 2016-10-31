package xn.bwie.com.sunmenglongnews.application;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Process;

import org.xutils.x;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Pooh on 2016/10/31.
 */
public class MyApplication extends Application{

    private static Context context;
    private static int myMainThreadId;
    private static Handler handler;
    private static Thread thread;
    private static ExecutorService executorService;

    @Override
    public void onCreate() {
        super.onCreate();
        //获取上下文对象
        context = getApplicationContext();
        //创建整个项目当中要用到的Handler
        handler = new Handler();
        //获取主线程的id
        myMainThreadId = Process.myTid();
        //获取主线程
        thread = Thread.currentThread();
        //线程池
        executorService = Executors.newFixedThreadPool(5);
        //使用xutils获取网络数据是必须先注册
        x.Ext.init(this);
        x.Ext.setDebug(true);
    }
    //上下文对象的方法
    public static Context getContext(){
        return context;
    }
    //handler的方法
    public static Handler getHandler(){
        return handler;
    }
    //主线程id的方法
    public static int getMainId(){
        return myMainThreadId;
    }
    //主线程的方法
    public static Thread getMainThread(){
        return thread;
    }
    //线程池的方法
    public static ExecutorService getExecutorService(){
        return executorService;
    }
}
