package com.example.hook.hooktest;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.security.cert.X509Certificate;
import java.util.HashMap;

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

    @Override
    public void handleLoadPackage(final XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {
        hook58Pay(lpparam);
    }

    private void hook58Pay(final XC_LoadPackage.LoadPackageParam lpparam) {
        if (lpparam.packageName.equals("com.anjuke.android.newbroker")) {
            XposedBridge.log("Hook测试程序" + lpparam.packageName);
            hook58PayClient(lpparam);
            hookPaySuccessResult(lpparam);
            hookPayError(lpparam);
        }
    }

    private void hook58PayClient(final XC_LoadPackage.LoadPackageParam lpparam) {
        try {
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
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            XposedBridge.log("hoock58PayClient出错");
        }

    }

    private void hookPayError(final XC_LoadPackage.LoadPackageParam lpparam) {
        try {
            XposedHelpers.findAndHookMethod("com.pay58.sdk.b.f", lpparam.classLoader, "a", Object.class, lpparam.classLoader.loadClass("com.pay58.sdk.b.a.b"), String.class, String.class, String.class, HashMap.class, new XC_MethodHook() {
                /**
                 * onCreate之前回调
                 * @param param  onCreate方法的信息，可以修改
                 * @throws
                 */
                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    XposedBridge.log("Hook 58pay 错误返回前code:" + param.args[3]);
                    XposedBridge.log("Hook 58pay 错误返回前message:" + param.args[4]);
                }

                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {

                }
            });
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void hookPaySuccessResult(final XC_LoadPackage.LoadPackageParam lpparam) {
        try {
            XposedHelpers.findAndHookMethod("com.pay58.sdk.b.f", lpparam.classLoader, "a", Object.class, lpparam.classLoader.loadClass("com.pay58.sdk.b.a.b"), String.class, Object.class, HashMap.class, new XC_MethodHook() {
                /**
                 * onCreate之前回调
                 * @param param  onCreate方法的信息，可以修改
                 * @throws
                 */
                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    XposedBridge.log("Hook 58pay 成功返回body:" + param.args[3]);
                }

                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {

                }
            });
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
