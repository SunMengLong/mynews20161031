package xn.bwie.com.sunmenglongnews.base;

import android.text.TextUtils;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import xn.bwie.com.sunmenglongnews.application.MyApplication;
import xn.bwie.com.sunmenglongnews.utils.MD5Encoder;
import xn.bwie.com.sunmenglongnews.utils.NetUtils;
import xn.bwie.com.sunmenglongnews.utils.commonUtils;

/**
 * Created by Pooh on 2016/10/31.
 */
public abstract class BaseData {

    /**
     * 网络出错
     */
    public static  final  int Error_Net=100;
    /**
     * 请求出错
     */
    public static  final  int Error_Request=200;

    //先看本地是否有数据
    public void getData(String path,long time){
        //先看本地
       String data = getDataFromLocal(path, time);
        if(TextUtils.isEmpty(data)){
            //在看网络
            getDataFromNet(path, time);
        }else{
            setResultData(data);
        }
    }
    //数据共享//抽象方法/实例化本类对象时，必须调用此方法
    public abstract void setResultData(String data);

    //从本地获取数据
    private String getDataFromLocal(final String path, final long time) {
        File caChe= MyApplication.getContext().getCacheDir();
        File file=null;
        try {
            file=new File(caChe, MD5Encoder.encode(path));

            if(!file.exists()){
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            BufferedReader reader=null;
            reader=new BufferedReader(new FileReader(file.getAbsoluteFile()));

            long t=Long.parseLong(reader.readLine());

            if(System.currentTimeMillis()-t>time){
                StringBuilder builder=new StringBuilder();
                String len=null;
                while((len=reader.readLine())!=null){
                    builder.append(len);
                }
                return builder.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    //从网络获取数据
    private void getDataFromNet(final String path, final long time) {
        //判断网络状态
        int type= NetUtils.getNetWorkType(MyApplication.getContext());
        if(type!=NetUtils.NETWORKTYPE_INVALID){
            //开始请求网络
            RequestParams params=new RequestParams(path);

            x.http().get(params,new Callback.CommonCallback<String>(){
                @Override
                public void onSuccess(final String s) {
                    setResultData(s);
                    commonUtils.executeRunnable(new Runnable() {
                        @Override
                        public void run() {
                            writeDataLocal(s,path,time);
                        }
                    });
                }

                @Override
                public void onError(Throwable throwable, boolean b) {
                    setFailResult(Error_Request);
                }

                @Override
                public void onCancelled(CancelledException e) {

                }

                @Override
                public void onFinished() {

                }


            });
        }else{
            setFailResult(Error_Net);
        }
    }

    //请求状态的抽象方法
    protected abstract void setFailResult(int error_Net);


    private void writeDataLocal(String s, String path, long time) {
        //获取本地
        File caChe=MyApplication.getContext().getCacheDir();
        File file=null;
        try {
            file=new File(caChe, MD5Encoder.encode(path));
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        //写入数据
        BufferedWriter bw=null;
        try {
            bw=new BufferedWriter(new FileWriter(file.getAbsoluteFile()));
            bw.write(System.currentTimeMillis()+"/r/n");
            bw.write(s);
            bw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(bw!=null){
                try {
                    bw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

