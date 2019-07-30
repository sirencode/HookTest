package com.example.hook.hooktest;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.X509TrustManager;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

/**
 * create by shenyonghe at 2018/12/30
 */
public class SetTextHook implements IXposedHookLoadPackage {
    //    @Override
////    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {
////        if (!lpparam.packageName.equals("com.syh.producta"))
////            return;
////
////        XposedBridge.log("开始Hook测试程序");
////        XposedHelpers.findAndHookMethod("com.syh.framework.MainActivity", lpparam.classLoader, "setTextViewText", String.class, new XC_MethodHook() {
////            /**
////             * onCreate之前回调
////             * @param param  onCreate方法的信息，可以修改
////             * @throws
////             */
////            @Override
////            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
////                XposedBridge.log("处理setText方法前");
////                param.args[0] = "我是被Xposed修改的";
////            }
////
////            @Override
////            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
////                XposedBridge.log("处理setText方法后");
////            }
////        });
////    }
//    @Override
//    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {
//        if (!lpparam.packageName.equals("com.anjuke.android.newbroker"))
//            return;
//
//        XposedBridge.log("Hook测试程序" + lpparam.packageName);
//        XposedHelpers.findAndHookMethod("com.anjuke.android.newbroker.module.housing.newpublish.activity.HugEsfPublishActivity", lpparam.classLoader, "loadChangeInfo", new XC_MethodHook() {
//            /**
//             * onCreate之前回调
//             * @param param  onCreate方法的信息，可以修改
//             * @throws
//             */
//            @Override
//            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
//                XposedBridge.log("Hook loadChangeInfo方法前");
//            }
//
//            @Override
//            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
//                XposedBridge.log("Hook loadChangeInfo方法后");
//            }
//        });
//    }

    @Override
    public void handleLoadPackage(final XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {
        if (!lpparam.packageName.equals("com.anjuke.android.newbroker"))
            return;

        XposedBridge.log("Hook测试程序" + lpparam.packageName);
        XposedHelpers.findAndHookMethod("com.pay58.sdk.b.f", lpparam.classLoader, "a", lpparam.classLoader.loadClass("com.pay58.sdk.b.b"), new XC_MethodHook() {
            /**
             * onCreate之前回调
             * @param param  onCreate方法的信息，可以修改
             * @throws
             */
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                XposedBridge.log("Hook pay58方法前");
            }

            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                XposedBridge.log("Hook pay58方法后");
                Object result = param.getResult();
                // 获取okhttpclient
                Class okhttp = lpparam.classLoader.loadClass("okhttp3.OkHttpClient");
                Class build = lpparam.classLoader.loadClass("okhttp3.OkHttpClient$Builder");

                Method method = okhttp.getDeclaredMethod("connectTimeoutMillis");
                Constructor constructor = build.getDeclaredConstructor(okhttp);
                constructor.setAccessible(true);

                Method buildM = build.getDeclaredMethod("build");
                Object buildObj = constructor.newInstance(result);

//                Method connectTimeout = build.getDeclaredMethod("connectTimeout", long.class, lpparam.classLoader.loadClass("java.util.concurrent.TimeUnit"));
//                Class time = lpparam.classLoader.loadClass("java.util.concurrent.TimeUnit");
//                Object[] consts = time.getEnumConstants();
//                connectTimeout.invoke(buildObj, 10, consts[3]);

                final X509TrustManager e = new X509TrustManager() {
                    @Override
                    public void checkClientTrusted(final X509Certificate[] array, final String s) {
                    }

                    @Override
                    public void checkServerTrusted(final X509Certificate[] array, final String s) {
                    }

                    @Override
                    public X509Certificate[] getAcceptedIssuers() {
                        return new X509Certificate[0];
                    }
                };

                SSLContext sslContext = SSLContext.getInstance("SSL");
                sslContext.init(null, new X509TrustManager[]{e}, null);
                SSLSocketFactory c = sslContext.getSocketFactory();
                Method sslSocketFactory = build.getDeclaredMethod("sslSocketFactory", lpparam.classLoader.loadClass("javax.net.ssl.SSLSocketFactory"), lpparam.classLoader.loadClass("javax.net.ssl.X509TrustManager"));
                sslSocketFactory.invoke(buildObj, c, e);

                result = buildM.invoke(buildObj);
                param.setResult(result);
                XposedBridge.log("Hook pay58方法后" + method.invoke(param.getResult()));
            }
        });
    }
}
