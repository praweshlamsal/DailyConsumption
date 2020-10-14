package com.example.dailyconsumption.userlanding.landingfragments.wifiactivity.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.wifi.p2p.WifiP2pManager
import android.widget.Toast
import com.example.dailyconsumption.userlanding.landingfragments.wifiactivity.WifiConnectionActivity

class WifiDirectBroadCastReceiver(mManager:WifiP2pManager, mChannel:WifiP2pManager.Channel, wifiConnectionActivity: WifiConnectionActivity):
    BroadcastReceiver() {

    var mManager = mManager
    var mChannel = mChannel
    var wifiConnectionActivity = wifiConnectionActivity


    override fun onReceive(context: Context?, intent: Intent?) {
        var action = intent!!.action
        if(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION.equals(action)){
            var state = intent.getIntExtra(WifiP2pManager.EXTRA_WIFI_STATE,-1)

            if(state == WifiP2pManager.WIFI_P2P_STATE_ENABLED){
                Toast.makeText(context,"Wifi is on",Toast.LENGTH_SHORT).show()
            }
            else{
                Toast.makeText(context,"Wifi is off",Toast.LENGTH_SHORT).show()
            }
        }
        else if(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION.equals(action)){

            mManager.requestPeers(mChannel,wifiConnectionActivity.peerListListener)
        }
        else if(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION.equals(action)){

        }
        else if(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION.equals(action)){

        }
    }
}