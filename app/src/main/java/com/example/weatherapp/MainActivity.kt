package com.example.weatherapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.widget.TextView;
import java.io.IOException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

class MainActivity : AppCompatActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
//    }
//}
    private lateinit var mTextViewResult:TextView

    val CITY: String = "California,us"
    val API: String = "061be710594c2b1d55f9d109fc7a7b95" // It is own API key from open weather

    //Asynchronous Get
    protected override fun onCreate(savedInstanceState:Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mTextViewResult = findViewById(R.id.text_view_result)

        val client = OkHttpClient()
        val url = "https://api.openweathermap.org/data/2.5/weather?q=$CITY&units=metric&appid=$API"
        val request = Request.Builder()
            .url(url)
            .build()

        client.newCall(request).enqueue(object: Callback {
            override fun onFailure(call:Call, e:IOException) {
                e.printStackTrace()
            }

            @Throws(IOException::class)
            override fun onResponse(call:Call, response:Response) {
                if (!response.isSuccessful) throw IOException("Unexpected code $response")
                if (response.isSuccessful()) {
                    val myResponse = response.body()?.string()
                    this@MainActivity.runOnUiThread(object:Runnable {
                        public override fun run() {
                            mTextViewResult.setText(myResponse)
                        }
                    })
                }
            }
            //val CITY: String = "California,US"
            //    val API: String = "061be710594c2b1d55f9d109fc7a7b95" // It is own API key from open weather
        })
    }
}
