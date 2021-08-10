package com.example.mvvmkotlinexample.view

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.example.mvvmkotlinexample.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {
    lateinit var activitySplashBinding: ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activitySplashBinding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(activitySplashBinding.root)
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this@SplashActivity, ProductListActivity::class.java))
            finish()
        }, 3000)

    }
}