package com.anurag.tracer.ui.main

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanResult
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.anurag.tracer.R
import com.anurag.tracer.databinding.MainFragmentBinding
import kotlinx.android.synthetic.main.main_fragment.*
import java.util.Random

class MainFragment : Fragment() {
    private lateinit var binding: MainFragmentBinding
    private lateinit var randomString: String
    private lateinit var blueToothAdapter: BluetoothAdapter
    private val list = mutableListOf<String>()
    private val source = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
    private val adapter = ScannerAdapter(list)
    private val filter = IntentFilter(BluetoothDevice.ACTION_FOUND)

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        contact_list.adapter = ScannerAdapter(list)
        blueToothAdapter =  BluetoothAdapter.getDefaultAdapter()
        val test = blueToothAdapter.enable()
        button.setOnClickListener {
            randomString = Random().ints(10, 0, source.length)
                .toArray()
                .map(source::get)
                .joinToString("")

            activity?.registerReceiver(Receiver,filter)
            val blue = blueToothAdapter.bluetoothLeScanner
            blue.startScan(object :ScanCallback(){
                override fun onScanResult(callbackType: Int, result: ScanResult?) {
                    super.onScanResult(callbackType, result)
                }

                override fun onScanFailed(errorCode: Int) {
                    super.onScanFailed(errorCode)
                }
            })
            blueToothAdapter.startDiscovery()
            list.add(randomString)
        }
    }

    private val Receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
              if(BluetoothDevice.ACTION_FOUND == intent?.action){
                  val device = intent.getParcelableExtra<BluetoothDevice>(BluetoothDevice.EXTRA_DEVICE)
                  if(device.name != null)
                  list.add(device.name)
                  contact_list.adapter = ScannerAdapter(list.distinct())
              }
        }
    }

}
