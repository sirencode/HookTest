package com.example.hook.hooktest;

import android.annotation.SuppressLint;
import android.util.Log;

import java.lang.reflect.Method;

import de.robv.android.xposed.IXposedHookZygoteInit;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XSharedPreferences;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;

/**
 * Author: shenyonghe
 * Date: 2021/12/25
 * Version: v1.0
 * Description:
 * Modification History:
 * Date Author Version Description
 * ------------------------------------
 * 2021/12/25 shenyonghe v1.0
 * Why & What is modified:
 **/
public class Init implements IXposedHookZygoteInit {
    @Override
    @SuppressLint("LongLogTag")
    public void initZygote(final StartupParam startupParam) throws Throwable {
        Class clazz = XposedHelpers.findClass(android.os.Build.class.getName(), null);

        final Method m = XposedHelpers.findMethodExact(clazz, "getString", String.class);

        m.setAccessible(true);

        XposedBridge.hookMethod(m, new XC_MethodHook() {

            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                Log.e("PrivacyHookManager_QiDian","before call -> " + (String) param.args[0]);
                Log.e("PrivacyHookManager_QiDian",PrivacyHookManager.createStackToStringByIndex(new Throwable("requestLocationUpdates")));
                super.beforeHookedMethod(param);
            }

            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                super.afterHookedMethod(param);
                Log.e("PrivacyHookManager_QiDian","before call -> " + (String) param.args[0]+ ":"+param.args.length);
                if (((String)param.args[0]).equals("ro.build.model") && param.getResult() ==null){
                    String model = (String) m.invoke(null, "ro.build.model");
                }
            }
        });
        String model = (String) m.invoke(null, "ro.build.model");
//        String s = (String) m.invoke(null, "ro.build.model");
        Log.e("Init","call -> " + model);
    }
}
