package ru.mirea.karacheviv.mireaproject

import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FilesWorkFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FilesWorkFragment : Fragment() {


    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private val PICK_IMAGE_REQUEST = 1
    private var selectedImageUri: Uri? = null

    private lateinit var imageView : ImageView;
    private lateinit var buttonChooseImage : Button;
    private lateinit var floatingActionButton : FloatingActionButton;

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
        return inflater.inflate(R.layout.fragment_files_work, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FilesWorkFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FilesWorkFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        imageView = view.findViewById(R.id.imageCompressView);
        buttonChooseImage = view.findViewById(R.id.buttonChooseImage)
        floatingActionButton = view.findViewById(R.id.floatingActionCompressButton);

        buttonChooseImage.setOnClickListener {
            openGallery()
        }

        floatingActionButton.setOnClickListener {
            if (selectedImageUri != null) {
                showCompressionDialog()
            } else {
                Snackbar.make(view, "Выберите изображение сначала", Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    private fun openGallery() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Выберите изображение"), PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.data != null) {
            selectedImageUri = data.data
            imageView.setImageURI(selectedImageUri)
        }
    }

    private fun showCompressionDialog() {
        val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_comprassion, null)

        MaterialAlertDialogBuilder(requireContext())
            .setView(dialogView)
            .setTitle("Сжатие изображения")
            .setPositiveButton("Сжать") { dialog, _ ->
                val compressionPercent = dialogView.findViewById<EditText>(R.id.editTextCompression).text.toString().toIntOrNull()
                if (compressionPercent != null) {
                    compressImage(compressionPercent)
                } else {
                    Snackbar.make(requireView(), "Введите корректный процент сжатия", Snackbar.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Отмена") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    private fun compressImage(compressionPercent: Int) {
        try {
            val inputStream = requireContext().contentResolver.openInputStream(selectedImageUri!!)
            val bitmap = BitmapFactory.decodeStream(inputStream)
            val imageFile = File(requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES), "compressed_image.jpg")
            val outputStream: OutputStream = FileOutputStream(imageFile)
            bitmap.compress(Bitmap.CompressFormat.JPEG, compressionPercent, outputStream)
            outputStream.flush()
            outputStream.close()

            val values = ContentValues().apply {
                put(MediaStore.Images.Media.DISPLAY_NAME, "compressed_image.jpg")
                put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
                put(MediaStore.Images.Media.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
            }

            val contentResolver = requireContext().contentResolver
            val uri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)

            contentResolver.openOutputStream(uri!!).use { outputStream ->
                if (outputStream != null) {
                    bitmap.compress(Bitmap.CompressFormat.JPEG, compressionPercent, outputStream)
                }
            }

            imageView.setImageURI(uri)
            Snackbar.make(requireView(), "Сжатие прошло успешно, изображение сохранено в галереи", Snackbar.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Snackbar.make(requireView(), "Не удалось сжать файл", Snackbar.LENGTH_SHORT).show()
            e.printStackTrace()
        }
    }
}