package com.practice.practicemvvm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.w3c.dom.Text

class MainActivity : AppCompatActivity() {

    private val RESULT_1 = "RESULT 1"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val button = findViewById<Button>(R.id.button1)

        button.setOnClickListener {
            //3 TYPES io-network requests/local database, main-main thread, default-heavy computation work
            //                                                              like filter large lists
            CoroutineScope(IO ).launch {
                fakeApiResult()
            }
        }
    }

    private suspend fun setTextOnMainThread(input: String){
        withContext(Main){
            setText(input)
        }
    }

    private fun setText(input: String){
        val text = findViewById<TextView>(R.id.text1)
        text.text = input
    }

    private suspend fun result1CallApi(): String{
        logThread("restul1CallApi")
        delay(1000)
        return RESULT_1
    }

    private suspend fun fakeApiResult(){
        val result1 = result1CallApi()
        println("debug: $result1")
        setTextOnMainThread(result1)
    }

    private fun logThread(MethodString: String){
        println("DEBUG: $MethodString: {${Thread.currentThread().name}}")
    }
}