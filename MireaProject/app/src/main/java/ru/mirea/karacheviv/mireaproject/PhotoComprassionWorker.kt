package ru.mirea.karacheviv.mireaproject

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import java.io.ByteArrayOutputStream
import java.io.File
import kotlin.math.roundToInt

class PhotoComprassionWorker(
    private val context: Context,
    private val params : WorkerParameters
) : CoroutineWorker(context, params) {

    companion object{
        const val KEY_CONTENT_URI = "KEY_CONTENT_URI"
        const val KEY_COMPRESSION_THRESHOLD = "KEY_COMPRESSION_THRESHOLD"
        const val KEY_RESULT_PATH = "KEY_RESULT_PATH"
    }


    override suspend fun doWork(): Result {
        val strigUri = params.inputData.getString(KEY_CONTENT_URI)
        val comprassionThresholdInBytes = params.inputData.getLong(
            KEY_COMPRESSION_THRESHOLD,
            0L
        )
        val uri = Uri.parse(strigUri)
        val bytes = context.contentResolver.openInputStream(uri)?.use{
            it.readBytes()
        } ?: return Result.failure()
        val bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)

        var outputBytes : ByteArray
        var quality = 100
        do {
            val outputStream = ByteArrayOutputStream()
            outputStream.use {outputStreamCurr ->
                bitmap.compress(Bitmap.CompressFormat.PNG, quality, outputStream)
                outputBytes = outputStreamCurr.toByteArray()
                quality -= (quality * 0.1).roundToInt()

            }
        } while (outputBytes.size > comprassionThresholdInBytes && quality > 5)
        val imgFile = File(context.cacheDir, "${params.id}.png")
        imgFile.writeBytes(outputBytes)

        return Result.success(
            workDataOf(
                KEY_RESULT_PATH to imgFile.absolutePath
            )
        )
    }
}