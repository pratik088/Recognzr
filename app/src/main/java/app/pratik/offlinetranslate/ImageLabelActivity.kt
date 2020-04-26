package app.pratik.offlinetranslate

import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import kotlinx.android.synthetic.main.activity_labeling.*
import kotlinx.android.synthetic.main.activity_labeling.progressBar

class ImageLabelActivity : BaseCameraActivity() {

    private val itemAdapter: ImageLabelAdapter by lazy {
        ImageLabelAdapter(listOf())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        rvLabel.layoutManager = LinearLayoutManager(this)
        rvLabel.adapter = itemAdapter
    }

    private fun runImageLabeling(bitmap: Bitmap) {
        //Create a FirebaseVisionImage
        val image = FirebaseVisionImage.fromBitmap(bitmap)

        //Get access to an instance of FirebaseImageDetector
        val detector = FirebaseVision.getInstance().onDeviceImageLabeler

        //Use the detector to detect the labels inside the image
        detector.processImage(image)
            .addOnSuccessListener {
                // Task completed successfully
                progressBar.visibility = View.GONE
                itemAdapter.setList(it)
                sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED)
            }
            .addOnFailureListener {
                // Task failed with an exception
                progressBar.visibility = View.GONE
                Toast.makeText(baseContext, "Sorry, something went wrong!", Toast.LENGTH_SHORT).show()
            }
    }
    private fun runCloudImageLabeling(bitmap: Bitmap) {
        //Create a FirebaseVisionImage
        val image = FirebaseVisionImage.fromBitmap(bitmap)

        //Get access to an instance of FirebaseCloudImageDetector
        val detector = FirebaseVision.getInstance().getCloudImageLabeler()

        //Use the detector to detect the labels inside the image
        detector.processImage(image)
            .addOnSuccessListener {
                // Task completed successfully
                progressBar.visibility = View.GONE
                itemAdapter.setList(it)
                sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED)
            }
            .addOnFailureListener {
                // Task failed with an exception
                progressBar.visibility = View.GONE
                Toast.makeText(baseContext, "Sorry, something went wrong!", Toast.LENGTH_SHORT).show()
            }
    }

    override fun onClick(v: View?) {
        progressBar.visibility = View.VISIBLE
        cameraView1.captureImage { cameraKitImage ->
            // Get the Bitmap from the captured shot
            runCloudImageLabeling(cameraKitImage.bitmap)
            runOnUiThread {
                showPreview()
                imagePreview.setImageBitmap(cameraKitImage.bitmap)
            }
        }
    }

}

