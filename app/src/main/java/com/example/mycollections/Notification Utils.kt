package com.example.mycollections

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import androidx.core.app.NotificationCompat

const val NOTIFICATION_ID = 2
const val REQUEST_CODE = 2
const val FLAGS = 0

fun NotificationManager.sendNotification(messageBody: String, applicationContext: Context) {


    val contentIntent = Intent(applicationContext, MainActivity::class.java)
    val contentPendingIntent = PendingIntent.getActivity(
        applicationContext,
        NOTIFICATION_ID,
        contentIntent,
        PendingIntent.FLAG_UPDATE_CURRENT
    )

    val snoozeIntent = Intent(applicationContext, ProcessTextActivity::class.java)
    val snoozePendingIntent: PendingIntent =
        PendingIntent.getActivity(applicationContext, REQUEST_CODE, snoozeIntent, PendingIntent.FLAG_UPDATE_CURRENT)

    val beachImage = BitmapFactory.decodeResource(
        applicationContext.resources,
        R.drawable.ic_baseline_beach_access_24
    )

    // Build the notification
    val builder = NotificationCompat.Builder(
        applicationContext,
        CHANNEL_ID
    )
        .setSmallIcon(R.drawable.ic_baseline_beach_access_24)
        .setContentTitle(applicationContext.getString(R.string.notification_title))
        .setContentText(messageBody)
        .setContentIntent(contentPendingIntent)
        .setLargeIcon(beachImage)
        .addAction(
            R.drawable.ic_baseline_beach_access_24,
            applicationContext.getString(R.string.save),
            snoozePendingIntent
        )
        .setPriority(NotificationCompat.PRIORITY_LOW)
        .setAutoCancel(true)
    // Deliver the notification
    notify(NOTIFICATION_ID, builder.build())
}