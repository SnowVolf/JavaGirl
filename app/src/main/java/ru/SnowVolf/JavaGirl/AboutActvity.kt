package ru.SnowVolf.JavaGirl

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.view.View

/**
 * Created by Snow Volf on 12.12.2016.
 */
class AboutActivity : FragmentActivity() {
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.about_fragment)
    }
    fun onShareClick(v: View) {
        val share = Intent()
        share.action = Intent.ACTION_SEND
        share.putExtra(Intent.EXTRA_TEXT, getString(R.string.share_msg))
        share.type = "text/plain"
        startActivity(Intent.createChooser(share, getText(R.string.app_name)))
    }
    fun onArcClick(v: View){
        val uri = Uri.parse("https://drive.google.com/folderview?id=0B9vOQ7XJpNqpSDdDVk1IMXlXUXc")
        val intent = Intent(Intent.ACTION_VIEW, uri)
        startActivity(intent)
    }
    fun onBackArrowPressed(v: View){
        finish()
    }
}
