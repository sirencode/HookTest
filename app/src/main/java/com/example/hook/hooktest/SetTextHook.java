package com.example.hook.hooktest;

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
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {
        if (!lpparam.packageName.equals("com.syh.producta"))
            return;

        XposedBridge.log("开始Hook测试程序");
        XposedHelpers.findAndHookMethod("android.widget.TextView", lpparam.classLoader, "setText", CharSequence.class, new XC_MethodHook() {
            /**
             * onCreate之前回调
             * @param param  onCreate方法的信息，可以修改
             * @throws Throwable
             */
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                XposedBridge.log("处理setText方法前");
                param.args[0] = "我是被Xposed修改的";
            }

            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                XposedBridge.log("处理setText方法后");
            }
        });
    }
}
