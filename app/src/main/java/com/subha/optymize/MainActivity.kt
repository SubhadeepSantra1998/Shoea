package com.subha.optymize

import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import com.subha.optymize.core.base.BaseActivity
import com.subha.optymize.core.helper.NetworkChangeListener
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : BaseActivity() {

    val networkChangeListener = NetworkChangeListener()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

    override fun onStart() {
        var filter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        registerReceiver(networkChangeListener, filter)
        super.onStart()
    }

    override fun onStop() {
        unregisterReceiver(networkChangeListener)
        super.onStop()
    }


}