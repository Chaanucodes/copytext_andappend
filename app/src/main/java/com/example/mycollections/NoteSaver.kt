package com.example.mycollections


import android.app.Application
import android.content.Context
import android.content.Intent
import android.graphics.drawable.Icon
import android.os.Build
import android.service.quicksettings.Tile
import android.service.quicksettings.TileService
import android.util.Log
import androidx.annotation.RequiresApi


@RequiresApi(Build.VERSION_CODES.N)
class NoteSaver : TileService(){

    companion object{
        val listOfData = mutableListOf<String>()
    }


    override fun onClick() {

        if (qsTile.state == Tile.STATE_ACTIVE){

            qsTile.state = Tile.STATE_INACTIVE
            qsTile.updateTile()
            stopService(Intent(applicationContext, Clipboard::class.java))
            Log.i("hurrica : if", "Tile should be inactive")
        }
        else{
            qsTile.state = Tile.STATE_ACTIVE
            qsTile.updateTile()
            startService(Intent(applicationContext, Clipboard::class.java))
            Log.i("hurrica : if", "Tile should be active")
        }
        super.onClick()
    }

    override fun onTileAdded() {
        super.onTileAdded()
        val tile = qsTile
        tile.state = Tile.STATE_ACTIVE
        tile.updateTile()
    }


    override fun onStartListening() {

//        val tile = qsTile
//        tile.icon = Icon.createWithResource(this,
//            R.drawable.ic_baseline_beach_access_24)
//        tile.label = getString(R.string.tile_label)
//        tile.contentDescription = getString(R.string.tile_content_description)
//        tile.state = Tile.STATE_ACTIVE
//        tile.updateTile()
        super.onStartListening()
    }

    override fun onStopListening() {

//        val tile = qsTile
//        tile.icon = Icon.createWithResource(this,
//            R.drawable.ic_baseline_beach_inactive)
//        tile.label = getString(R.string.tile_label)
//        tile.contentDescription = getString(R.string.tile_content_description)
//        tile.state = Tile.STATE_INACTIVE
//        tile.updateTile()
//        super.onStopListening()
    }

}
