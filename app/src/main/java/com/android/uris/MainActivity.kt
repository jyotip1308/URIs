package com.android.uris

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.core.net.toUri
import com.android.uris.ui.theme.URIsTheme
import java.io.File
import java.io.FileOutputStream

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Resource URIs
        val uri = Uri.parse("android.resource://$packageName/drawable/message")
        val messageBytes = contentResolver.openInputStream(uri)?.use {
            it.readBytes()
        }
        println("Message size: ${messageBytes?.size}")

        //File URIs
       val file = File(filesDir, "message.png")
        FileOutputStream(file).use{
            it.write(messageBytes)
        }
        println(file.toUri())

        //Data URIs
        val dataUri = Uri.parse("data:text/plain;charset=UFT-8,Hello%20World")

        setContent {
            URIsTheme {
            //Content URIs
                val pickImage = rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.GetContent(),
                    onResult = {contentUri ->
                        println(contentUri)
                    }
                )

                Button(onClick = {
                    pickImage.launch("image/*")
                }) {
                    Text(text = "Pick image")

                }

            }
        }
    }
}