package com.example.dailyconsumption.userlanding.landingfragments.wifiactivity

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.IntentFilter
import android.net.wifi.WifiManager
import android.net.wifi.p2p.WifiP2pDevice
import android.net.wifi.p2p.WifiP2pDeviceList
import android.net.wifi.p2p.WifiP2pManager
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import com.example.dailyconsumption.R
import com.example.dailyconsumption.base.BaseActivity
import com.example.dailyconsumption.userlanding.landingfragments.wifiactivity.broadcast.WifiDirectBroadCastReceiver
import java.util.ArrayList


class WifiConnectionActivity : BaseActivity<WifiConnectionPresenter>(), WifiConnectionContractor {


    lateinit var mIntentFilter: IntentFilter
    lateinit var mReceiver: BroadcastReceiver
    lateinit var btn_discover: Button
    lateinit var listView:ListView
    lateinit var wifiManager: WifiManager
    lateinit var mManager: WifiP2pManager
    lateinit var mChannel: WifiP2pManager.Channel
    var peers: ArrayList<WifiP2pDevice> = ArrayList<WifiP2pDevice>()
    lateinit var deviceNamearray :Array<String?>
    lateinit var deviceArray:Array<WifiP2pDevice?>





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wifi_connections)
        supportActionBar?.hide()
        wifiManager = applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
        mManager = getSystemService(Context.WIFI_P2P_SERVICE) as WifiP2pManager
        mChannel = mManager.initialize(this, Looper.getMainLooper(), null)
        mReceiver = WifiDirectBroadCastReceiver(mManager, mChannel, this) as BroadcastReceiver
        mIntentFilter = IntentFilter()
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION)
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION)
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION)
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION)
        btn_discover = findViewById(R.id.btn_discover)
        listView = findViewById(R.id.listview)
        ondiscoverclick()
    }

    private fun ondiscoverclick() {
        btn_discover.setOnClickListener(View.OnClickListener {
            mManager.discoverPeers(mChannel, object : WifiP2pManager.ActionListener {
                override fun onSuccess() {
                    Toast.makeText(getActivityContext(), "Discovery Started", Toast.LENGTH_LONG)
                }

                override fun onFailure(reason: Int) {
                    Toast.makeText(getActivityContext(), "Discovery Failed", Toast.LENGTH_LONG)

                }
            })
        })
    }

    var peerListListener = object:WifiP2pManager.PeerListListener{
        override fun onPeersAvailable(peerList: WifiP2pDeviceList?) {
            if(peerList!!.deviceList!!.equals(peers) == false && peers.size != 0){
                peers.clear()
                peers.addAll(peerList.deviceList)
                deviceNamearray = arrayOfNulls<String>(peerList.deviceList.size)
                deviceArray = arrayOfNulls<WifiP2pDevice>(peerList.deviceList.size)
                var index = 0
                for(device in peerList.deviceList){
                    deviceNamearray[index] = device.deviceName
                    deviceArray[index] = device
                    index++
                }
                Log.d("okay231",peerList.deviceList.size.toString())
                var arrayAdapter:ArrayAdapter<String?> =  ArrayAdapter(getActivityContext(),R.layout.simple_list_item,deviceNamearray)
                listView.adapter = arrayAdapter
            }
            else if(peers.size == 0){
                Toast.makeText(getActivityContext(),"No device found",Toast.LENGTH_LONG).show()

            }

        }

    }

    override fun getContext(): Context {
        return this
    }

    override fun getActivityContext(): Activity {
        return this
    }

    override fun instantiatePresenter(): WifiConnectionPresenter {
        return WifiConnectionPresenter(this)
    }

    override fun loadUsers() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showLoading() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun hideLoading() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onResume() {
        super.onResume()
        registerReceiver(mReceiver, mIntentFilter)
    }

    override fun onPause() {
        super.onPause()
        unregisterReceiver(mReceiver)
    }
}