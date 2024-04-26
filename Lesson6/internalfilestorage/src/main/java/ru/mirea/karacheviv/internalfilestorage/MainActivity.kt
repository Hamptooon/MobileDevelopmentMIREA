package ru.mirea.karacheviv.internalfilestorage

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException


class MainActivity : AppCompatActivity() {

    private var fileName = "russiaEvent.txt";
    private lateinit var editText : EditText;
    private lateinit var button : Button;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editText = findViewById(R.id.inputText);
        button = findViewById(R.id.button);
        editText.setText(getTextFromFile());

        button.setOnClickListener {
            val outputStream: FileOutputStream
            try {
                outputStream = openFileOutput(fileName, MODE_PRIVATE)
                outputStream.write(editText.text.toString().toByteArray())
                outputStream.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
    }

    fun getTextFromFile(): String? {
        var fin: FileInputStream? = null
        try {
            fin = openFileInput(fileName)
            val bytes = ByteArray(fin.available())
            fin.read(bytes)
            return String(bytes)
        } catch (ex: IOException) {
            Toast.makeText(this, ex.message, Toast.LENGTH_SHORT).show()
        } finally {
            try {
                fin?.close()
            } catch (ex: IOException) {
                Toast.makeText(this, ex.message, Toast.LENGTH_SHORT).show()
            }
        }
        return null
    }
}