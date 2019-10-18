package com.gaston.meliintegration.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.gaston.meliintegration.R
import kotlinx.android.synthetic.main.activity_congrats.*

class CongratsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_congrats)

        btn_catalogo.setOnClickListener {
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        }
    }
}
