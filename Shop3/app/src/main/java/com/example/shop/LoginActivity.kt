package com.example.shop

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Log.INFO
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import okhttp3.internal.platform.Platform.Companion.INFO
import java.util.logging.Level.INFO

//@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)

        val btnLogin = findViewById<Button>(R.id.btnLogin)
        val btnRegister = findViewById<Button>(R.id.btnRegister)
        val btnGoToMap = findViewById<Button>(R.id.btnGoToMap)
        val etLogin = findViewById<EditText>(R.id.etLogin)
        val etPassword = findViewById<EditText>(R.id.etPassword)

        /*val viewModel: ProductViewModel = hiltViewModel()
        val product = ProductViewModel.get
        */
        btnGoToMap.setOnClickListener {
            Intent(this, MapsActivity::class.java).also {
                /*lifecycleScope.launch {
                val hopeProduct = RetrofitInstance.api.getProduct()
                Toast.makeText(this@MainActivity, hopeProduct.toString(), Toast.LENGTH_LONG).show()
            }*/
                //Log.i("hopehope", "onCreate: $hopeProduct")
                startActivity(it)
            }
        }
            btnRegister.setOnClickListener {
                Intent(this, RegisterActivity::class.java).also {
                    startActivity(it)
                }
            }

                btnLogin.setOnClickListener {
                    val login = etLogin.text.toString()
                    val password = etPassword.text.toString()
                    lifecycleScope.launch {
                        try {
                            val canI = RetrofitInstance.api.loginUser(login, password)
                            Toast.makeText(
                                this@LoginActivity,
                                canI.toString(),
                                Toast.LENGTH_LONG
                            )
                                .show()
                        } catch (exc: Exception) {
                            Toast.makeText(this@LoginActivity, exc.toString(), Toast.LENGTH_LONG)
                                .show()
                            Log.i("logging", exc.toString())
                        }
                    }
                    Intent(this, ProductActivity::class.java).also {
                        startActivity(it)
                    }
                }
            //val hopeProduct = igetProduct(11)
            /*Intent(this, LoginActivity::class.java).also {
                startActivity(it)
            }*/


    }
}