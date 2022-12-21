package com.maruthimourya.games.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.maruthimourya.databinding.ActivityContainerBinding

class ContainerActivity : AppCompatActivity() {

    private lateinit var containerBinding: ActivityContainerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        containerBinding = ActivityContainerBinding.inflate(layoutInflater)
        setContentView(containerBinding.root)
    }

}