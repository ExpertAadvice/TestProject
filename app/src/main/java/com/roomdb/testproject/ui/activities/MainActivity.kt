package com.roomdb.testproject.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.roomdb.testproject.R
import com.roomdb.testproject.databinding.ActivityMainBinding
import com.roomdb.testproject.ui_activity.UiActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.button.setOnClickListener {
            val intent = Intent(this, MainActivity2::class.java)
            startActivity(intent)
        }

        binding.navigateToUi.setOnClickListener {
            val intent = Intent(this, UiActivity::class.java)
            startActivity(intent)
        }

        binding.navigateToSampleApps.setOnClickListener {
            val intent = Intent(this, UiActivity::class.java)
            startActivity(intent)
        }
    }
}