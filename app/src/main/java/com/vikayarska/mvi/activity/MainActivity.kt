package com.vikayarska.mvi.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.vikayarska.mvi.R
import com.vikayarska.mvi.viewmodel.UserDetailsViewModel
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.components.ActivityComponent

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }



}