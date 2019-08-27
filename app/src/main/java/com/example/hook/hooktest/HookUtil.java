package com.example.hook.hooktest;

import android.content.Context;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class HookUtil implements IXposedHookLoadPackage {

    Context context;

    private String PACKAGENAME = "com.xxx.yyy";

    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {
        if (lpparam.packageName.equals(PACKAGENAME)) {
            getContext(lpparam.classLoader);
        }
    }

    //获取上下文
    private void getContext(final ClassLoader appClassLoader) {
        try {
            Class<?> ContextClass = XposedHelpers.findClass("android.content.ContextWrapper", appClassLoader);
            XposedHelpers.findAndHookMethod(ContextClass, "getApplicationContext", new XC_MethodHook() {
                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    super.afterHookedMethod(param);
                    if (context != null)
                        return;
                    context = (Context) param.getResult();

                }
            });
        } catch (Throwable t) {
            XposedBridge.log("获取上下文出错");
            context = null;
        }
    }
}
