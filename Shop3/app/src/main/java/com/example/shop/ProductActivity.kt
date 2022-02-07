package com.example.shop

import android.R.layout.simple_list_item_1
import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import android.widget.ArrayAdapter
import android.view.View
import android.widget.ListView
/*
<html>None of the following functions can be called with the arguments supplied:<br/>public
constructor ArrayAdapter&lt;T : Any!&gt;(context: Context, resource: Int, objects: Array&lt;(out)
TypeVariable(T)!&gt;) defined in android.widget.ArrayAdapter<br/>public constructor ArrayAdapter&lt;T :
Any!&gt;(context: Context, resource: Int, textViewResourceId: Int) defined in android.widget.ArrayAdapter<br/>public
constructor ArrayAdapter&lt;T : Any!&gt;(context: Context, resource: Int, objects: (Mutable)List&lt;TypeVariable(T)!&gt;)
defined in android.widget.ArrayAdapter
*/
//@AndroidEntryPoint

class ProductActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.products)

        //val rwProducts = findViewById<RecyclerView>(R.id.rvProducts)
        val lwProducts = findViewById<ListView>(R.id.lwProducts)
        lifecycleScope.launch {
            val hopeProduct = RetrofitInstance.api.getProduct()
            val stringProducts = arrayOfNulls<String>(hopeProduct.size)

            for ((i, e) in hopeProduct.withIndex()) {
                //println("$i: $e")
                stringProducts[i] = e.toString()
            }
            //val adapter = ArrayAdapter(this, simple_list_item_1, stringProducts)
            val adapter = ArrayAdapter(this@ProductActivity, android.R.layout.simple_list_item_1, stringProducts)
            lwProducts.adapter = adapter

            /*
            lwProducts.setOnItemClickListener { _, _, position, _ ->
                //Log.v("EXAMPLE", "clicked on: ${position}")
                //Log.v("EXAMPLE", "clicked on: ${results[position]!!.name}")
                // 1
                //val selectedRecipe = recipeList[position]

                //// 2
                //val detailIntent = RecipeDetailActivity.newIntent(context, selectedRecipe)

                //// 3
                val intent = Intent(this@ProductAc, ProductInfo::class.java)
                intent.putExtra("name",results[position]!!.name)
                intent.putExtra("description",results[position]!!.description)
                startActivity(intent)

             */


            //rwProducts.adapter = adapter


        }
        /*val viewModel: ProductViewModel = hiltViewModel()
        val product = ProductViewModel.get
        */


        //val hopeProduct = igetProduct(11)
        /*Intent(this, LoginActivity::class.java).also {
            startActivity(it)
        }*/

    }
}
