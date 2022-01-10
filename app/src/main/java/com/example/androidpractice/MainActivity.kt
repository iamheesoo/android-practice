package com.example.androidpractice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import com.example.androidpractice.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(LayoutInflater.from(this))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}