package ru.mirea.karacheviv.mireaproject

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ImageSendFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ImageSendFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var image : ImageView
    private lateinit var progressBar : ProgressBar

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

        return inflater.inflate(R.layout.fragment_image_send, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        image = view.findViewById(R.id.imageView4)
        progressBar = view.findViewById(R.id.progressBar1)
        val workManager = WorkManager.getInstance(view.context)
        val bundle = arguments
        if (bundle != null ) {
            val imageUri: Uri? = bundle.getParcelable("imageUri")

            val request = OneTimeWorkRequestBuilder<PhotoComprassionWorker>()
                .setInputData(
                    workDataOf(
                        PhotoComprassionWorker.KEY_CONTENT_URI to imageUri.toString(),
                        PhotoComprassionWorker.KEY_COMPRESSION_THRESHOLD to 1024 * 20L

                    )
                )
                .setConstraints(Constraints(
                    requiredNetworkType = NetworkType.CONNECTED
                ))
                .build()
            progressBar.visibility = View.VISIBLE
            workManager.enqueue(request)
                workManager.getWorkInfoByIdLiveData(request.id).observe(viewLifecycleOwner) { workInfo ->
                    if (workInfo != null && workInfo.state.isFinished) {
                        val filePath = workInfo.outputData.getString(PhotoComprassionWorker.KEY_RESULT_PATH)
                        if (!filePath.isNullOrEmpty()) {
                            val bitmap = BitmapFactory.decodeFile(filePath)
                            Log.d("gggg", bitmap.toString())
                            image.setImageBitmap(bitmap)
                            progressBar.visibility = View.GONE

                        }
                    }
                }

        }
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ImageSendFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ImageSendFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}