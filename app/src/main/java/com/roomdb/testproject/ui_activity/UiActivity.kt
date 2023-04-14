package com.roomdb.testproject.ui_activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.roomdb.testproject.R
import com.roomdb.testproject.databinding.ActivityUiBinding

class UiActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUiBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_ui)

    }
}