package ru.mirea.karacheviv.favoritebook

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts

class MainActivity : AppCompatActivity() {
    private lateinit var activityResultLauncher : ActivityResultLauncher<Intent>
    private lateinit var textViewUserBook: TextView
    companion object {
        const val KEY = "KEY"
        const val USER_MESSAGE = "MESSAGE"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        textViewUserBook = findViewById(R.id.textView1)
        textViewUserBook.text = "Тут появится название вашей любимой книги!"
        val callback = ActivityResultCallback<ActivityResult> { result ->
                if(result.resultCode == Activity.RESULT_OK){
                    val data = result.data
                    val userBook = data?.getStringExtra(USER_MESSAGE)
                    textViewUserBook.text = userBook
            }
        }
        activityResultLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult(),
            callback
        )
    }



    fun getInfoAboutBook(view: View){
        val intent = Intent(this, ShareActivity::class.java)
        intent.putExtra(KEY, "Граф Монте-Кристо")
        activityResultLauncher.launch(intent)

    }
}