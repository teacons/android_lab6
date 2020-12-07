package ru.fbear.imagedownload

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.net.URL

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        download_btn.setOnClickListener {
            if (url.text.isNotEmpty()) {
                DownloadImageTask(image).execute(url.text.toString())
            } else {
                DownloadImageTask(image).execute("https://old.fbear.ru/image/android_cat.webp")
            }

        }
    }

    class DownloadImageTask(private val imageView: ImageView) : AsyncTask<String, Void, Bitmap>() {
        override fun onPostExecute(result: Bitmap?) {
            imageView.setImageBitmap(result)
        }

        override fun doInBackground(vararg params: String): Bitmap? {
            val url = params[0]
            try {
                val inputStream = URL(url).openStream()
                return BitmapFactory.decodeStream(inputStream)
            } catch (e: Exception) {
                Log.e("Error", e.message)
                e.printStackTrace()
                return null
            }
        }
    }
}

