package ru.mirea.karacheviv.mireaproject

import android.os.AsyncTask
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.fragment.app.Fragment
import org.json.JSONObject
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HttpFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HttpFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private val ACCEPT_PROPERTY = "application/geo+json;version=1"
    private val USER_AGENT_PROPERTY = "newsapi.org (karachevigor21@gmail.com)"
    private var newsList = ArrayList<News>()


    private lateinit var newsListView: ListView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_http, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HttpFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HttpFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        newsListView = view.findViewById(R.id.listView)
        fetchNews()
    }


    private fun fetchNews() {
        val apiKey = "1d07977107bf4fb08de683828c2926bf"
        val newsUrl = "https://newsapi.org/v2/top-headlines?country=us&apiKey=$apiKey"

        NewsTask().execute(newsUrl)
    }

    private inner class NewsTask : AsyncTask<String, Void, String>() {

        override fun doInBackground(vararg urls: String): String {

            try {
                return downloadIpInfo(urls[0])
            } catch (e: IOException) {
                e.printStackTrace()
                return "error"
            }

        }

        override fun onPostExecute(result: String) {

            try {
                val jsonObject = JSONObject(result)
                val jsonArray = jsonObject.getJSONArray("articles")

                for (i in 0 until jsonArray.length()) {
                    val article = jsonArray.getJSONObject(i)
                    val title = article.getString("title")
                    val description = article.getString("description")
                    addNews(title, description)
                }

                val adapter = NewsAdapter(requireContext(), newsList)
                newsListView.adapter = adapter
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(requireContext(), "Ошибка загрузки новостей", Toast.LENGTH_SHORT).show()
            }
            super.onPostExecute(result)
        }

        private fun addNews(title: String, description: String) {
            val news = News(title, description)
            newsList.add(news)
            // Уведомить адаптер об изменениях в данных
            (newsListView.adapter as? NewsAdapter)?.notifyDataSetChanged()
        }

        override fun onPreExecute() {
            super.onPreExecute()
        }

        @Throws(IOException::class)
        private fun downloadIpInfo(address: String): String {
            var inputStream: InputStream? = null
            var data = ""
            try {
                val url = URL(address)
                val connection = url.openConnection() as HttpURLConnection
                connection.readTimeout = 100000
                connection.connectTimeout = 100000
                connection.requestMethod = "GET"
                connection.setRequestProperty("Accept", ACCEPT_PROPERTY)
                connection.setRequestProperty("User-Agent", USER_AGENT_PROPERTY)
                connection.instanceFollowRedirects = true
                connection.useCaches = false
                connection.doInput = true
                val responseCode = connection.responseCode
                if (responseCode == HttpURLConnection.HTTP_OK) { // 200 OK
                    inputStream = connection.inputStream
                    val bos = ByteArrayOutputStream()
                    var read = 0
                    while (inputStream.read().also { read = it } != -1) {
                        bos.write(read)
                    }
                    bos.close()
                    data = bos.toString()
                } else {
                    data = connection.responseMessage + ". Error Code: " + responseCode
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
}