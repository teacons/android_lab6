package ru.fbear.imagedownload

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.coroutineScope
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.URL

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        download_btn.setOnClickListener {
            val urlText = if (url.text.isNotEmpty()) {
                url.text.toString()
            } else {
                "https://old.fbear.ru/image/android_cat.webp"
            }
            lifecycle.coroutineScope.launchWhenResumed {
                image.setImageBitmap(downloadImage(urlText))
            }
        }
    }

    private suspend fun downloadImage(url: String): Bitmap? =
        withContext(Dispatchers.IO) {
            try {
                BitmapFactory.decodeStream(URL(url).openStream())
            } catch (e: Exception) {
                e.message?.let { Log.e("Error", it) }
                e.printStackTrace()
                null
            }
        }
}

