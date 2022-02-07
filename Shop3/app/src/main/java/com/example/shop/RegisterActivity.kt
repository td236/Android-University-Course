package com.example.shop

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.hilt.navigation.compose.hiltViewModel
import dagger.hilt.android.AndroidEntryPoint

//@AndroidEntryPoint
class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register)

        val btnLogin = findViewById<Button>(R.id.btnLogin)
        val etLogin = findViewById<EditText>(R.id.etLogin)
        val etPassword = findViewById<EditText>(R.id.etPassword)

        /*val viewModel: ProductViewModel = hiltViewModel()
        val product = ProductViewModel.get
        */


            //val hopeProduct = igetProduct(11)
            /*Intent(this, LoginActivity::class.java).also {
                startActivity(it)
            }*/

        }
    }
