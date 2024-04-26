package ru.mirea.karacheviv.httpurlconnection

import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.AsyncTask
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONException
import org.json.JSONObject
import ru.mirea.karacheviv.httpurlconnection.databinding.ActivityMainBinding
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL
import java.util.Locale


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.showInfoButton.setOnClickListener {
            val connectivityManager = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
            var networkInfo: NetworkInfo? = null
            if (connectivityManager != null) {
                networkInfo = connectivityManager.activeNetworkInfo
            }
            if (networkInfo != null && networkInfo.isConnected) {
                DownloadPageTask().execute("https://ipinfo.io/json")
            } else {
                Toast.makeText(
                    this@MainActivity, "Нет интернета",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private inner class DownloadPageTask : AsyncTask<String, Void?, String>() {
        override fun doInBackground(vararg params: String?): String {
            try {
                return downloadIpInfo(params[0])
            } catch (e: IOException) {
                e.printStackTrace()
                return "error"
            }
        }


        override fun onPreExecute() {
            super.onPreExecute()
        }

        override fun onPostExecute(result: String?) {

            try {
                val responseJson = JSONObject(result)
                if (responseJson.has("current_weather")) {
                    val currentWeatherUnits = responseJson.getJSONObject("current_weather_units")
                    val currentWeather = responseJson.getJSONObject("current_weather")
                    val weatherData: MutableMap<String, Any> = HashMap()
                    weatherData["temperature"] =
                        currentWeather.getDouble("temperature").toString() +
                                currentWeatherUnits.getString("temperature")
                    weatherData["windspeed"] = currentWeather.getDouble("windspeed").toString() +
                            currentWeatherUnits.getString("windspeed")
                    for ((key, value1) in weatherData) {
                        val value = value1.toString()
                        if (key == "temperature") {
                            binding.weatherTextView.setText(
                                String.format(
                                    Locale.getDefault(),
                                    "Температура: %s", value
                                )
                            )
                        } else {
                            binding.windspeedTextView.setText(
                                String.format(
                                    Locale.getDefault(),
                                    "Скорость ветра: %s", value
                                )
                            )
                        }
                    }
                } else {
                    val city = responseJson.getString("city")
                    val region = responseJson.getString("region")
                    val country = responseJson.getString("country")
                    val timezone = responseJson.getString("timezone")
                    binding.cityTextView.setText("Город: $city")
                    binding.regionTextView.setText("Регион: $region")
                    binding.countryTextView.setText("Страна: $country")
                    binding.timezoneTextView.setText("Временная зона: $timezone")
                    val latitudeLongitude = responseJson.getString("loc")
                    val parts =
                        latitudeLongitude.split(",")
                    if (parts.size == 2) {
                        val latitude = parts[0].trim().toFloat()
                        val longitude = parts[1].trim().toFloat()
                        DownloadPageTask().execute("https://api.open-meteo.com/v1/forecast?latitude=$latitude&longitude=$longitude&current_weather=true")
                    }
                }
            } catch (e: JSONException) {
                e.printStackTrace()
            }
            super.onPreExecute()
        }


    }

    @Throws(IOException::class)
    private fun downloadIpInfo(address: String?): String {
        var inputStream: InputStream? = null
        var data = ""
        try {
            val url = URL(address)
            val connection = url.openConnection() as HttpURLConnection
            connection.readTimeout = 100000
            connection.connectTimeout = 100000
            connection.requestMethod = "GET"
            connection.instanceFollowRedirects = true
            connection.useCaches = false
            connection.doInput = true
            val responseCode = connection.responseCode
            if (responseCode == HttpURLConnection.HTTP_OK) {
                inputStream = connection.inputStream
                val bos = ByteArrayOutputStream()
                var read = 0
                while (inputStream.read().also { read = it } != -1) {
                    bos.write(read)
                }
                bos.close()
                data = bos.toString()
            } else {
                data = connection.responseMessage + ". Error code: " + responseCode
            }
            connection.disconnect()
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            inputStream?.close()
        }
        return data
    }
}