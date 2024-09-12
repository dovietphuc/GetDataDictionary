package com.phucdv.getdatadictionary

import android.content.ContentUris
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    companion object {
        val AUTHORITY = "com.phucdv.dictionarydemo.provider"
        val WORD_TABLE_NAME = "word"
        val URI = "content://$AUTHORITY/$WORD_TABLE_NAME".toUri()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val projection = arrayOf(
            "id",
            "text"
        )
        val cursor = contentResolver.query(
            URI,
            projection,
            null,
            null,
            null
        )
        cursor?.let { cursor ->
            while(cursor.moveToNext()) {
                val idIndex = cursor.getColumnIndex("id")
                val textIndex = cursor.getColumnIndex("text")

                val id = cursor.getInt(idIndex)
                val text = cursor.getString(textIndex)

                Log.d("PhucDV", "$id - $text")
            }
            cursor.close()
        }
    }
}