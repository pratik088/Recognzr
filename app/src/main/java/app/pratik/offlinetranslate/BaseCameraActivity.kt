package app.pratik.offlinetranslate

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.android.synthetic.main.activity_labeling.*
import kotlinx.android.synthetic.main.activity_labeling.cameraView1



abstract class BaseCameraActivity : AppCompatActivity(), View.OnClickListener {

    val sheetBehavior by lazy {
        BottomSheetBehavior.from(layout_bottom_sheet)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_labeling)
        btnRetry.setOnClickListener {
            if (cameraView1.visibility == View.VISIBLE) showPreview() else hidePreview()
        }
        fab_take_photo.setOnClickListener(this)
        sheetBehavior.peekHeight = 224
        sheetBehavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {}
            override fun onSlide(bottomSheet: View, slideOffset: Float) {}
        })
    }

    override fun onResume() {
        super.onResume()
        cameraView1.start()
    }

    override fun onPause() {
        cameraView1.stop()
        super.onPause()
    }

    protected fun showPreview() {
        framePreview.visibility = View.VISIBLE
        cameraView1.visibility = View.GONE
    }

    protected fun hidePreview() {
        framePreview.visibility = View.GONE
        cameraView1.visibility = View.VISIBLE
    }
}
