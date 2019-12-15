package com.example.mobpush

import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.mob.pushsdk.MobPush
import com.mob.pushsdk.MobPushCustomMessage
import com.mob.pushsdk.MobPushNotifyMessage
import com.mob.pushsdk.MobPushReceiver
import java.io.File


class MainActivity : AppCompatActivity(),MobPushReceiver {

    val  TAG = "MobPush"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initMobPush()

        var id = android.os.Process.myPid()
        Log.e(TAG,"my process id = $id")

    }

    fun initMobPush(){
        MobPush.setAlias("Alias2t43232")            //设置别名
        MobPush.addTags(arrayOf("tag3"))            //设置标签
        MobPush.setShowBadge(true)                  //默认是关闭的，设置true为打开显示角标，反之则为关闭显示角标
        MobPush.addPushReceiver(this)
        MobPush.restartPush()
    }

    override fun onAliasCallback(p0: Context?, p1: String?, p2: Int, p3: Int) {
        Log.e(TAG,"onAliasCallback")
        Log.e(TAG,"p1 = $p1,p2 = $p2,p3 = $p3")
    }

    override fun onCustomMessageReceive(p0: Context?, p1: MobPushCustomMessage?) {
        Log.e(TAG,"onCustomMessageReceive")
        Log.e(TAG,"p1 = $p1")
    }

    override fun onNotifyMessageReceive(p0: Context?, p1: MobPushNotifyMessage?) {
        Log.e(TAG,"onNotifyMessageReceive")
        Log.e(TAG,"p1 = $p1")

    }

    override fun onTagsCallback(p0: Context?, p1: Array<out String>?, p2: Int, p3: Int) {
        Log.e(TAG,"onTagsCallback")
        Log.e(TAG,"p1.sz = ${p1?.size}, p2 = $p2, p3 = $p3")
        Log.e(TAG,"process id = " + android.os.Process.myPid())
    }

    override fun onNotifyMessageOpenedReceive(p0: Context?, p1: MobPushNotifyMessage?) {
        Log.e(TAG,"onNotifyMessageOpenedReceive")
        Log.e(TAG,"p1 = $p1")
    }

    fun onStopClicked(v : View){
        while (true){
            MobPush.cleanTags()
            MobPush.clearAllNotification()
            MobPush.clearLocalNotifications()
            MobPush.removePushReceiver(this)
            MobPush.stopPush()
            var intent = Intent()
            var pkg = "com.example.mobpush:mobservice"
            intent.setPackage(pkg)
            stopService(intent)
            var am = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
            am.killBackgroundProcesses(pkg)
            am.killBackgroundProcesses(packageName)
            var processes = am.getRunningAppProcesses()
            for (process in processes){
                Log.e(TAG,"process id = " + process.pid + " name = " + process.processName)
                var pkgList = process.pkgList
                for (p in pkgList){
                    Log.e(TAG,"pkg = " + p.toString())
                }
            }
            android.os.Process.killProcess(processes[1].pid)
            am.restartPackage(packageName)
            SystemClock.sleep(200)
        }
    }
    fun onRestartClicked(v : View){
        MobPush.restartPush()
        var ret = MobPush.isPushStopped()
        Log.e(TAG,"stop = $ret")
//        MobPush.setSilenceTime(11, 0,12, 0)
    }
    fun onIsStoppedClicked(v : View){
        var ret = MobPush.isPushStopped()
        Log.e(TAG,"stop = $ret")
    }
}
