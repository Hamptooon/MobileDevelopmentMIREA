package ru.mirea.karacheviv.mireaproject

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class NewsAdapter(context: Context, private val newsList: List<News>) :
    ArrayAdapter<News>(context, R.layout.list_item_news, newsList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var itemView = convertView
        if (itemView == null) {
            itemView = LayoutInflater.from(context).inflate(R.layout.list_item_news, parent, false)
        }

        val news = newsList[position]

        val titleTextView = itemView!!.findViewById<TextView>(R.id.textViewTitle)
        val descriptionTextView = itemView.findViewById<TextView>(R.id.textViewDescription)

        titleTextView.text = news.title
        descriptionTextView.text = news.description

        return itemView
    }
}