package ru.mirea.karacheviv.notebook

import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import java.io.BufferedReader
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.nio.charset.StandardCharsets


class MainActivity : AppCompatActivity() {
    private var REQUEST_CODE_PERMISSION = 200
    private lateinit var fileNameText : EditText;
    private lateinit var quoteText : EditText;

    private lateinit var saveButton : Button;
    private lateinit var loadButton : Button;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fileNameText = findViewById(R.id.fileNameText)
        quoteText = findViewById(R.id.editText);

        saveButton = findViewById(R.id.saveBtn);
        loadButton = findViewById(R.id.loadBtn);


        saveButton.setOnClickListener {
            writeFileToExternalStorage(it);
        }

        loadButton.setOnClickListener{
            readFileFromExternalStorage(it)
        }




    }

    fun isExternalStorageWritable(): Boolean {
        val state = Environment.getExternalStorageState()
        return if (Environment.MEDIA_MOUNTED == state) {
            true
        } else false
    }

    fun isExternalStorageReadable(): Boolean {
        val state = Environment.getExternalStorageState()
        return if (Environment.MEDIA_MOUNTED == state || Environment.MEDIA_MOUNTED_READ_ONLY == state) {
            true
        } else false
    }


    fun writeFileToExternalStorage(view: View?) {
        val path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)
        val file = File(path, fileNameText.text.toString())
        try {
            val fileOutputStream = FileOutputStream(file.absoluteFile)
            val output = OutputStreamWriter(fileOutputStream)

            output.write(quoteText.text.toString())

            output.close()
        } catch (e: IOException) {
            Log.w("ExternalStorage", "Error writing $file", e)
        }
    }

    fun readFileFromExternalStorage(view: View?) {
        val path = Environment.getExternalStoragePublicDirectory(
            Environment.DIRECTORY_DOCUMENTS
        )
        val file = File(path, fileNameText.text.toString())
        try {
            val fileInputStream = FileInputStream(file.absoluteFile)
            val inputStreamReader = InputStreamReader(fileInputStream, StandardCharsets.UTF_8)
            val lines: MutableList<String> = ArrayList()
            val reader = BufferedReader(inputStreamReader)
            var line = reader.readLine()
            while (line != null) {
                lines.add(line)
                line = reader.readLine()
            }
            val listString = java.lang.String.join("\n", lines)
            quoteText.setText(listString)
            Log.w(
                "ExternalStorage",
                String.format("Read	from	file	%s	successful", lines.toString())
            )
        } catch (e: Exception) {
            Log.w(
                "ExternalStorage",
                String.format("Read	from	file	%s	failed", e.message)
            )
        }
    }
}