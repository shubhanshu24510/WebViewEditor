package com.example.webview

import android.content.Context
import android.os.Environment
import android.util.Base64
import android.util.Log
import android.webkit.JavascriptInterface
import android.webkit.MimeTypeMap
import android.widget.Toast
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class JSInterface(private val context: Context) {
    @JavascriptInterface
    fun receiveBase64(base64Data: String, mimeType: String) {
        val decodedBytes = Base64.decode(base64Data, Base64.DEFAULT)
        val fileName =
            "downloaded_file.${MimeTypeMap.getSingleton().getExtensionFromMimeType(mimeType)}"
        try {
            val file = File(context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), fileName)
            val outputStream = FileOutputStream(file)
            outputStream.write(decodedBytes)
            outputStream.close()
            Toast.makeText(context, "File saved to ${file.absolutePath}", Toast.LENGTH_LONG).show()
            Log.d("JSInterface", "Received data with MIME type: $mimeType")

        } catch (e: IOException) {
            e.printStackTrace()
            Toast.makeText(context, "Error saving file", Toast.LENGTH_LONG).show()
        }
    }
}