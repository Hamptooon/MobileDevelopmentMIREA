package ru.mirea.karacheviv.lesson5

import android.R
import android.hardware.Sensor
import android.hardware.SensorManager
import android.os.Bundle
import android.widget.SimpleAdapter
import androidx.appcompat.app.AppCompatActivity
import ru.mirea.karacheviv.lesson5.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var bindidng : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindidng = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bindidng.root)

        val sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        val sensors = sensorManager.getSensorList(Sensor.TYPE_ALL)
        val listView = bindidng.listView

        val arrayList = ArrayList<HashMap<String, Any>>()

        for (i in sensors.indices) {
            val sensorTypeList = HashMap<String, Any>()
            sensorTypeList["Name"] = sensors[i].name
            sensorTypeList["Value"] = sensors[i].maximumRange
            arrayList.add(sensorTypeList)
        }

        val mHistory = SimpleAdapter(
            this, arrayList, R.layout.simple_list_item_2, arrayOf("Name", "Value"), intArrayOf(
                R.id.text1, R.id.text2
            )
        )
        listView.adapter =  mHistory
    }
}