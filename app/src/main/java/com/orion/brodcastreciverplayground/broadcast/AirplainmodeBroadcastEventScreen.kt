package com.orion.brodcastreciverplayground.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext

@Composable
fun AirplaneMode(modifier: Modifier = Modifier) {
    var data by remember {
        mutableStateOf("")
    }
    val context = LocalContext.current
    val broadcastReceiver = remember {
        object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                val bundle = intent?.getBooleanExtra("state" , false)?:return
                data  = if(bundle)
                {
                    "Airplane Mode On"
                }else
                {
                    "Airplane Mode Off"
                }
            }
        }
    }

    DisposableEffect(key1 =true) {
        val intentFilter = IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED)
        context.registerReceiver(broadcastReceiver,intentFilter)
        onDispose {
            context.unregisterReceiver(broadcastReceiver)
        }
    }

    Box(modifier = modifier.fillMaxSize() , contentAlignment = Alignment.Center )
    {
        Text(text = data)
    }
}