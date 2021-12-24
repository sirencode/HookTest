package com.example.hook.hooktest;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanSettings;
import android.content.ClipData;
import android.content.ContentResolver;
import android.hardware.camera2.CameraDevice;
import android.location.Criteria;
import android.location.LocationListener;
import android.media.MediaSyncEvent;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.telephony.NetworkScanRequest;
import android.telephony.TelephonyManager;
import android.telephony.TelephonyScanManager;
import android.util.Log;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.Executor;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class PrivacyHookManager implements IXposedHookLoadPackage {
    public static final String TAG = "PrivacyHookManager_QiDian";

    @SuppressLint("LongLogTag")
    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpp) throws Throwable {
        if (!lpp.processName.startsWith("com.tencent.qidian")) {
            return;
        }
        Log.d(TAG, "hookmethod processName" + lpp.processName);
        Log.d(TAG, "hookmethod packageName" + lpp.packageName);
        try {
            try {
                XposedHelpers.findAndHookMethod("android.content.ContentResolver", lpp.classLoader, "query", Uri.class, String[].class, String.class, String[].class, String.class, new XC_MethodHook() {
                    @SuppressLint("LongLogTag")
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        String url = ((Uri) param.args[0]).toString();
                        if (!url.contains("tencent") && !url.contains("qidian")) {
                            Log.e(TAG, "query url===>>>" + url);
                            Log.e(TAG, createStackToStringByIndex(new Throwable("query url===>>>" + url)));
                        }
                    }
                });
            } catch (Exception e) {
            }

            // TelephonyManager:getImei、getDeviceId、getSubscriberId、getMeid、getLine1Number、getSimSerialNumber
            hook_method("android.telephony.TelephonyManager", lpp.classLoader, "getDeviceId");

            hook_method("android.telephony.TelephonyManager", lpp.classLoader, "getSubscriberId");
            hook_method("android.telephony.TelephonyManager", lpp.classLoader, "getMeid");
            hook_method("android.telephony.TelephonyManager", lpp.classLoader, "getImei");
            hook_method("android.telephony.TelephonyManager", lpp.classLoader, "getLine1Number");
            hook_method("android.telephony.TelephonyManager", lpp.classLoader, "getSimSerialNumber");
            //android.provider.Settings.Secure
            try {
                XposedHelpers.findAndHookMethod("android.provider.Settings.Secure", lpp.classLoader, "getString", ContentResolver.class, String.class, new XC_MethodHook() {
                    @SuppressLint("LongLogTag")
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        String id = (String) param.args[1];
                        Log.e(TAG, "Settings  getString:===>>>" + id);
                        Log.e(TAG, createStackToStringByIndex(new Throwable("Settings  getString:===>>>" + id)));
                    }
                });
            } catch (Exception e) {
            }

            hook_method("android.os.Build", lpp.classLoader, "getSerial");
            hook_method("android.net.wifi.WifiInfo", lpp.classLoader, "getMacAddress");
            hook_method("android.net.wifi.WifiInfo", lpp.classLoader, "getSSID");
            hook_method("android.net.wifi.WifiInfo", lpp.classLoader, "getBSSID");
            hook_method("android.net.wifi.WifiManager", lpp.classLoader, "startScan");
            hook_method("android.net.wifi.WifiManager", lpp.classLoader, "getScanResults");

            hook_method("java.net.NetworkInterface", lpp.classLoader, "getHardwareAddress");
            hook_method("java.net.NetworkInterface", lpp.classLoader, "getNetworkInterfaces");


            try {
                XposedHelpers.findAndHookMethod("android.location.LocationManager", lpp.classLoader, "requestLocationUpdates", String.class, long.class, float.class, LocationListener.class,
                        new XC_MethodHook() {
                            @SuppressLint("LongLogTag")
                            @Override
                            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                                Log.e(TAG, createStackToStringByIndex(new Throwable("requestLocationUpdates")));
                            }
                        });
            } catch (Exception e) {
            }
            try {
                XposedHelpers.findAndHookMethod("android.location.LocationManager", lpp.classLoader, "requestLocationUpdates", String.class, long.class, float.class, LocationListener.class, Looper.class,
                        new XC_MethodHook() {
                            @SuppressLint("LongLogTag")
                            @Override
                            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                                Log.e(TAG, createStackToStringByIndex(new Throwable("requestLocationUpdates")));
                            }
                        });
            } catch (Exception e) {
            }

            try {
                XposedHelpers.findAndHookMethod("android.location.LocationManager", lpp.classLoader, "requestLocationUpdates", long.class, float.class, Criteria.class, LocationListener.class, Looper.class,
                        new XC_MethodHook() {
                            @SuppressLint("LongLogTag")
                            @Override
                            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                                Log.e(TAG, createStackToStringByIndex(new Throwable("requestLocationUpdates")));
                            }
                        });
            } catch (Exception e) {
            }


            try {
                XposedHelpers.findAndHookMethod("android.location.LocationManager", lpp.classLoader, "requestLocationUpdates", String.class, long.class, float.class, PendingIntent.class,
                        new XC_MethodHook() {
                            @SuppressLint("LongLogTag")
                            @Override
                            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                                Log.e(TAG, createStackToStringByIndex(new Throwable("requestLocationUpdates")));
                            }
                        });
            } catch (Exception e) {
            }

            try {
                XposedHelpers.findAndHookMethod("android.location.LocationManager", lpp.classLoader, "requestLocationUpdates", long.class, float.class, Criteria.class, PendingIntent.class,
                        new XC_MethodHook() {
                            @SuppressLint("LongLogTag")
                            @Override
                            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                                Log.e(TAG, createStackToStringByIndex(new Throwable("requestLocationUpdates")));
                            }
                        });
            } catch (Exception e) {
            }

            try {
                XposedHelpers.findAndHookMethod("android.location.LocationManager", lpp.classLoader, "getLastKnownLocation", String.class,
                        new XC_MethodHook() {
                            @SuppressLint("LongLogTag")
                            @Override
                            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                                Log.e(TAG, createStackToStringByIndex(new Throwable("getLastKnownLocation")));
                            }
                        });
            } catch (Exception e) {
            }

            try {
                XposedHelpers.findAndHookMethod("android.location.LocationManager", lpp.classLoader, "requestSingleUpdate", String.class, LocationListener.class, Looper.class,
                        new XC_MethodHook() {
                            @SuppressLint("LongLogTag")
                            @Override
                            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                                Log.e(TAG, createStackToStringByIndex(new Throwable("requestSingleUpdate")));
                            }
                        });
            } catch (Exception e) {
            }
            try {
                XposedHelpers.findAndHookMethod("android.location.LocationManager", lpp.classLoader, "requestSingleUpdate", Criteria.class, LocationListener.class, Looper.class,
                        new XC_MethodHook() {
                            @SuppressLint("LongLogTag")
                            @Override
                            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                                Log.e(TAG, createStackToStringByIndex(new Throwable("requestSingleUpdate")));
                            }
                        });
            } catch (Exception e) {
            }
            try {
                XposedHelpers.findAndHookMethod("android.location.LocationManager", lpp.classLoader, "requestSingleUpdate", String.class, PendingIntent.class,
                        new XC_MethodHook() {
                            @SuppressLint("LongLogTag")
                            @Override
                            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                                Log.e(TAG, createStackToStringByIndex(new Throwable("requestSingleUpdate")));
                            }
                        });
            } catch (Exception e) {
            }
            try {
                XposedHelpers.findAndHookMethod("android.location.LocationManager", lpp.classLoader, "requestSingleUpdate", Criteria.class, PendingIntent.class,
                        new XC_MethodHook() {
                            @SuppressLint("LongLogTag")
                            @Override
                            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                                Log.e(TAG, createStackToStringByIndex(new Throwable("requestSingleUpdate")));
                            }
                        });
            } catch (Exception e) {
            }

            hook_method("android.telephony.TelephonyManager", lpp.classLoader, "getCellLocation");
            hook_method("android.telephony.TelephonyManager", lpp.classLoader, "getAllCellInfo");
            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    XposedHelpers.findAndHookMethod("android.telephony.TelephonyManager", lpp.classLoader, "requestCellInfoUpdate", Executor.class, TelephonyManager.CellInfoCallback.class,
                            new XC_MethodHook() {
                                @SuppressLint("LongLogTag")
                                @Override
                                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                                    Log.e(TAG, createStackToStringByIndex(new Throwable("requestCellInfoUpdate")));
                                }
                            });
                }
            } catch (Exception e) {
            }
            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    XposedHelpers.findAndHookMethod("android.telephony.TelephonyManager", lpp.classLoader, "requestNetworkScan", NetworkScanRequest.class, Executor.class, TelephonyScanManager.NetworkScanCallback.class,
                            new XC_MethodHook() {
                                @SuppressLint("LongLogTag")
                                @Override
                                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                                    Log.e(TAG, createStackToStringByIndex(new Throwable("requestNetworkScan")));
                                }
                            });
                }
            } catch (Exception e) {
            }
            hook_method("android.net.wifi.WifiManager", lpp.classLoader, "getConnectionInfo");
            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    XposedHelpers.findAndHookMethod("android.bluetooth.le.BluetoothLeScanner", lpp.classLoader, "startScan", ScanCallback.class,
                            new XC_MethodHook() {
                                @SuppressLint("LongLogTag")
                                @Override
                                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                                    Log.e(TAG, createStackToStringByIndex(new Throwable("BluetoothLeScanner startScan")));
                                }
                            });
                }
            } catch (Exception e) {
            }
            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    XposedHelpers.findAndHookMethod("android.bluetooth.le.BluetoothLeScanner", lpp.classLoader, "startScan", List.class, ScanSettings.class, ScanCallback.class,
                            new XC_MethodHook() {
                                @SuppressLint("LongLogTag")
                                @Override
                                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                                    Log.e(TAG, createStackToStringByIndex(new Throwable("BluetoothLeScanner startScan")));
                                }
                            });
                }
            } catch (Exception e) {
            }
            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    XposedHelpers.findAndHookMethod("android.bluetooth.le.BluetoothLeScanner", lpp.classLoader, "startScan", List.class, ScanSettings.class, PendingIntent.class,
                            new XC_MethodHook() {
                                @SuppressLint("LongLogTag")
                                @Override
                                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                                    Log.e(TAG, createStackToStringByIndex(new Throwable("BluetoothLeScanner startScan")));
                                }
                            });
                }
            } catch (Exception e) {
            }
            hook_method("android.bluetooth.BluetoothAdapter", lpp.classLoader, "startDiscovery");
            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    XposedHelpers.findAndHookMethod("android.bluetooth.BluetoothAdapter", lpp.classLoader, "startLeScan", BluetoothAdapter.LeScanCallback.class,
                            new XC_MethodHook() {
                                @SuppressLint("LongLogTag")
                                @Override
                                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                                    Log.e(TAG, createStackToStringByIndex(new Throwable("BluetoothAdapter startLeScan")));
                                }
                            });
                }
            } catch (Exception e) {
            }
            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    XposedHelpers.findAndHookMethod("android.bluetooth.BluetoothAdapter", lpp.classLoader, "startLeScan", UUID[].class, BluetoothAdapter.LeScanCallback.class,
                            new XC_MethodHook() {
                                @SuppressLint("LongLogTag")
                                @Override
                                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                                    Log.e(TAG, createStackToStringByIndex(new Throwable("BluetoothAdapter startLeScan")));
                                }
                            });
                }
            } catch (Exception e) {
            }
            hook_method("android.telephony.TelephonyManager", lpp.classLoader, "getServiceState");

            hook_method("android.media.MediaRecorder", lpp.classLoader, "start");
            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    XposedHelpers.findAndHookMethod("android.media.MediaRecorder", lpp.classLoader, "setAudioSource", int.class,
                            new XC_MethodHook() {
                                @SuppressLint("LongLogTag")
                                @Override
                                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                                    Log.e(TAG, createStackToStringByIndex(new Throwable("MediaRecorder setAudioSource")));
                                }
                            });
                }
            } catch (Exception e) {
            }
            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    XposedHelpers.findAndHookMethod("android.media.MediaRecorder", lpp.classLoader, "setVideoSource", int.class,
                            new XC_MethodHook() {
                                @SuppressLint("LongLogTag")
                                @Override
                                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                                    Log.e(TAG, createStackToStringByIndex(new Throwable("MediaRecorder setVideoSource")));
                                }
                            });
                }
            } catch (Exception e) {
            }
            hook_method("android.media.AudioRecord", lpp.classLoader, "startRecording");
            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    XposedHelpers.findAndHookMethod("android.media.AudioRecord", lpp.classLoader, "startRecording", MediaSyncEvent.class,
                            new XC_MethodHook() {
                                @SuppressLint("LongLogTag")
                                @Override
                                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                                    Log.e(TAG, createStackToStringByIndex(new Throwable("AudioRecord startRecording")));
                                }
                            });
                }
            } catch (Exception e) {
            }

            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    XposedHelpers.findAndHookMethod("android.hardware.camera2.CameraManager", lpp.classLoader, "openCamera", String.class, CameraDevice.StateCallback.class, Handler.class,
                            new XC_MethodHook() {
                                @SuppressLint("LongLogTag")
                                @Override
                                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                                    Log.e(TAG, createStackToStringByIndex(new Throwable("CameraManager openCamera")));
                                }
                            });
                }
            } catch (Exception e) {
            }

            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    XposedHelpers.findAndHookMethod("android.hardware.Camera", lpp.classLoader, "open", int.class,
                            new XC_MethodHook() {
                                @SuppressLint("LongLogTag")
                                @Override
                                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                                    Log.e(TAG, createStackToStringByIndex(new Throwable("Camera open")));
                                }
                            });
                }
            } catch (Exception e) {
            }
            hook_method("android.hardware.Camera", lpp.classLoader, "open");
            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    XposedHelpers.findAndHookMethod("android.content.ClipboardManager", lpp.classLoader, "setPrimaryClip", ClipData.class,
                            new XC_MethodHook() {
                                @SuppressLint("LongLogTag")
                                @Override
                                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                                    Log.e(TAG, createStackToStringByIndex(new Throwable("ClipboardManager setPrimaryClip")));
                                }
                            });
                }
            } catch (Exception e) {
            }

            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    XposedHelpers.findAndHookMethod("android.content.ClipboardManager", lpp.classLoader, "setPrimaryClip", ClipData.class,
                            new XC_MethodHook() {
                                @SuppressLint("LongLogTag")
                                @Override
                                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                                    Log.e(TAG, createStackToStringByIndex(new Throwable("ClipboardManager setPrimaryClip")));
                                }
                            });
                }
            } catch (Exception e) {
            }
            hook_method("android.content.ClipboardManager", lpp.classLoader, "clearPrimaryClip");
            hook_method("android.content.ClipboardManager", lpp.classLoader, "getPrimaryClip");
            hook_method("android.content.ClipboardManager", lpp.classLoader, "getPrimaryClipDescription");
            hook_method("android.content.ClipboardManager", lpp.classLoader, "hasPrimaryClip");
            hook_method("android.content.ClipboardManager", lpp.classLoader, "getText");
            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    XposedHelpers.findAndHookMethod("android.content.ClipboardManager", lpp.classLoader, "setText", CharSequence.class,
                            new XC_MethodHook() {
                                @SuppressLint("LongLogTag")
                                @Override
                                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                                    Log.e(TAG, createStackToStringByIndex(new Throwable("ClipboardManager setText")));
                                }
                            });
                }
            } catch (Exception e) {
            }
            hook_method("android.content.ClipboardManager", lpp.classLoader, "hasText");
            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    XposedHelpers.findAndHookMethod("android.content.pm.PackageManager", lpp.classLoader, "getInstalledPackages", int.class,
                            new XC_MethodHook() {
                                @SuppressLint("LongLogTag")
                                @Override
                                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                                    Log.e(TAG, createStackToStringByIndex(new Throwable("PackageManager getInstalledPackages")));
                                }
                            });
                }
            } catch (Exception e) {
            }
            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    XposedHelpers.findAndHookMethod("android.content.pm.PackageManager", lpp.classLoader, "getInstalledApplications", int.class,
                            new XC_MethodHook() {
                                @SuppressLint("LongLogTag")
                                @Override
                                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                                    Log.e(TAG, createStackToStringByIndex(new Throwable("PackageManager getInstalledApplications")));
                                }
                            });
                }
            } catch (Exception e) {
            }
        } catch (Exception e) {
//            e.printStackTrace();
        }
    }

    private void hook_method(final String className, ClassLoader classLoader, final String methodName) {
        try {
            XposedHelpers.findAndHookMethod(className, classLoader, methodName, new XC_MethodHook() {
                @SuppressLint("LongLogTag")
                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    Log.e(TAG, createStackToStringByIndex(new Throwable(className + "===>>>" + methodName)));
                }
            });
        } catch (Exception e) {
        }
    }

    public static String createStackToStringByIndex(Throwable t) {
        StackTraceElement[] stackArray = t.getStackTrace();
        StringBuilder sb = new StringBuilder();
        for (StackTraceElement stackTraceElement : stackArray) {
            sb.append(stackTraceElement);
            sb.append("\n");
        }
        return sb.toString(); // 处理堆栈信息
    }
}
