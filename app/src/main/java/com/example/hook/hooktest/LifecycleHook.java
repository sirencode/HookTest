package com.example.hook.hooktest;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class LifecycleHook implements IXposedHookLoadPackage {
    public static final String TAG = "LifecycleHook";

    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpp) {
        final String processName = lpp.processName;
        if (!processName.startsWith("com.tencent.qidian")) {
            return;
        }
        XposedHelpers.findAndHookMethod("com.tencent.common.app.BaseApplicationImpl", lpp.classLoader, "attachBaseContext", Context.class, new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) {
                Log.d(TAG, "processName="+processName+" :before BaseApplicationImpl.attachBaseContext = " + System.currentTimeMillis());
            }

            @Override
            protected void afterHookedMethod(MethodHookParam param) {
                Log.d(TAG, "processName="+processName+" :after BaseApplicationImpl.attachBaseContext = " + System.currentTimeMillis());
            }
        });

        XposedHelpers.findAndHookMethod("com.tencent.common.app.BaseApplicationImpl", lpp.classLoader, "onCreate", new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) {
                Log.d(TAG, "processName="+processName+ " :before BaseApplicationImpl.onCreate = " + System.currentTimeMillis());
            }

            @Override
            protected void afterHookedMethod(MethodHookParam param) {
                Log.d(TAG, "processName="+processName+ " :after BaseApplicationImpl.onCreate = " + System.currentTimeMillis());
            }
        });

        XposedHelpers.findAndHookMethod("com.tencent.mobileqq.activity.SplashActivity", lpp.classLoader, "doOnCreate", Bundle.class, new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) {
                Log.d(TAG, "processName="+processName+ " :before SplashActivity.doOnCreate = " + System.currentTimeMillis());
            }

            @Override
            protected void afterHookedMethod(MethodHookParam param) {
                Log.d(TAG, "processName="+processName+ " :after SplashActivity.doOnCreate = " + System.currentTimeMillis());
            }
        });

        XposedHelpers.findAndHookMethod("com.tencent.mobileqq.activity.SplashActivity", lpp.classLoader, "doOnResume", new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) {
                Log.d(TAG, "processName="+processName+ " :before SplashActivity.doOnResume = " + System.currentTimeMillis());
            }

            @Override
            protected void afterHookedMethod(MethodHookParam param) {
                Log.d(TAG, "processName="+processName+ " :after SplashActivity.doOnResume = " + System.currentTimeMillis());
            }
        });

        XposedHelpers.findAndHookMethod("com.tencent.mobileqq.activity.SplashActivity", lpp.classLoader, "doOnStart", new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) {
                Log.d(TAG, "processName="+processName+ " :before SplashActivity.doOnStart = " + System.currentTimeMillis());
            }

            @Override
            protected void afterHookedMethod(MethodHookParam param) {
                Log.d(TAG, "processName="+processName+ " :after SplashActivity.doOnStart = " + System.currentTimeMillis());
            }
        });
    }
}
