package com.example.mycollections

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.ClipboardManager
import android.content.ClipboardManager.OnPrimaryClipChangedListener
import android.content.Context.CLIPBOARD_SERVICE
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat.getSystemService

const val CHANNEL_ID = "MY_NOTE_TEMP_CHAN_ID"
const val CHANNEL_NAME = "MY_NOTE_TEMP_CHAN"

class Clipboard : Service() {
    private var mCM: ClipboardManager? = null
    var mBinder: IBinder? = null
    private var mStartMode = 0
    companion object{
        var shouldDisplayNotification = false
    }
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        mCM =
            getSystemService(applicationContext, ClipboardManager::class.java) as ClipboardManager?
        this.mCM?.addPrimaryClipChangedListener(OnPrimaryClipChangedListener {
            val newClip: String = mCM?.text.toString()
//            Toast.makeText(
//                applicationContext,
//                newClip,
//                Toast.LENGTH_LONG
//            ).show()
//            Log.i("LOG", newClip + "")
            if (!shouldDisplayNotification){
                NoteSaver.listOfData.clear()
                NoteSaver.listOfData.add(newClip)
                createChannel(channelId = CHANNEL_ID, channelName = CHANNEL_NAME)
                val notificationManager = applicationContext.getSystemService(
                    NotificationManager::class.java
                )
                notificationManager.sendNotification("Noting...", applicationContext)
                shouldDisplayNotification = true
            }else{
                if(!newClip.equals(NoteSaver.listOfData.last()))
                NoteSaver.listOfData.add(newClip)
            }
        })
        return mStartMode
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createChannel(channelId: String, channelName: String) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            val notificationChannel = NotificationChannel(
                channelId,
                channelName,
                NotificationManager.IMPORTANCE_LOW
            )

            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.RED
            notificationChannel.enableVibration(true)
            notificationChannel.description = getString(R.string.notification_desc)

            val notificationManager = applicationContext.getSystemService(
                NotificationManager::class.java
            )
            notificationManager.createNotificationChannel(notificationChannel)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}