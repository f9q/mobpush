package com.example.mobpush

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.mob.pushsdk.MobPush
import com.mob.pushsdk.MobPushCustomMessage
import com.mob.pushsdk.MobPushNotifyMessage
import com.mob.pushsdk.MobPushReceiver


class MainActivity : AppCompatActivity(),MobPushReceiver {

    val  TAG = "MobPush"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initMobPush()
    }

    fun initMobPush(){
        MobPush.setAlias("Alias")                   //设置别名
        MobPush.addTags(arrayOf("taikang","tag2"))  //设置标签
        MobPush.setShowBadge(true)                  //默认是关闭的，设置true为打开显示角标，反之则为关闭显示角标

        MobPush.addPushReceiver(this)
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
    }

    override fun onNotifyMessageOpenedReceive(p0: Context?, p1: MobPushNotifyMessage?) {
        Log.e(TAG,"onNotifyMessageOpenedReceive")
        Log.e(TAG,"p1 = $p1")
    }
}
