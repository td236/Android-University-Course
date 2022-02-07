package com.example.shop

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
//import com.example.shop.databinding.ActivityMainBinding

//private lateinit var binding: ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //binding = ActivityMainBinding.inflate(layoutInflater)
        //setContentView(binding.root)
        setContentView(R.layout.greeding_screen)
        val btnOneStep = findViewById<Button>(R.id.btnOneStep)

        btnOneStep.setOnClickListener {
            Toast.makeText(this, "Are you sure?", Toast.LENGTH_LONG).show()
            //SystemClock.wait(1000)

            Intent(this, LoginActivity::class.java).also {
                    lifecycleScope.launch {
                        try {
                            val hopeProduct = RetrofitInstance.api.getOneProduct(77)
                            Toast.makeText(
                                this@MainActivity,
                                hopeProduct.toString(),
                                Toast.LENGTH_LONG
                            )
                                .show()
                        } catch (exc: Exception) {
                            Toast.makeText(this@MainActivity, exc.toString(), Toast.LENGTH_LONG)
                                .show()
                        }
                    }
                //Log.i("hopehope", "onCreate: $hopeProduct")
                startActivity(it)
            }
        }
        /*
        setContentView(R.layout.activity_main)


        val btnLogin = findViewById<Button>(R.id.btnLogin)
        val etLogin = findViewById<TextView>(R.id.etLogin)

        btnLogin.setOnClickListener {
            val login = etLogin.text.toString()
            Log.d( "onCreate: MainActivity", "Login: $login")

        }
        */
    }
}