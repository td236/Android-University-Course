package com.example.c3

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView

var calc_state = "0"
var dot_used = false
var time_to_hide = false
var acc :Double = 0.0

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        calc_state = "0"
        dot_used = false
        time_to_hide = false
        acc = 0.0
    }

    @SuppressLint("SetTextI18n")
    fun buttonClick(view: View) {
        val label = findViewById<TextView>(R.id.label)
        var display = label.text.toString()
        if (view.id == R.id.clean) {
            label.text = ""
            calc_state = "0"
            acc = 0.0
            dot_used = false
            return
        }
        var buff = ""
        var state = "0"
        when (view.id) {
            R.id.num1 -> buff = "1"
            R.id.num2 -> buff = "2"
            R.id.num3 -> buff = "3"
            R.id.num4 -> buff = "4"
            R.id.num5 -> buff = "5"
            R.id.num6 -> buff = "6"
            R.id.num7 -> buff = "7"
            R.id.num8 -> buff = "8"
            R.id.num9 -> buff = "9"
            R.id.num0 -> buff = "0"
            R.id.dot  -> buff = "."
            R.id.plus -> state = "+"
            R.id.minus -> state = "-"
            R.id.multiply -> state = "x"
            R.id.divide -> state = "/"
            R.id.eq -> state = "="
        }
        if (buff != "") {
            if (time_to_hide) {
                time_to_hide = false
                display = ""
                label.text = ""
            }
            if (buff == "."){
                if (display == "" || dot_used) {
                    return
                }
                else {
                    label.text = "$display."
                    dot_used = true
                    return
                }
            }
            if (display == "0") {
                display = ""
            }
            display += buff
            label.text = display
            return
        }
        if (calc_state == "0" && state == "=")
            return
        if (calc_state == "0" && state != "0")
        {
            if (label.text == "") return
            calc_state = state
            acc = display.toDouble()
            //label.text = ""
            time_to_hide = true
            dot_used = false
            return
        }
        if (calc_state != "0" && state != "0")
        {
            val acc2 = display.toDouble()
            when (calc_state) {
                "+" -> acc += acc2
                "-" -> acc -= acc2
                "x" -> acc *= acc2
                "/" -> {
                    if (acc2 != 0.0) {
                        acc /= acc2
                    }
                    else {
                        acc = 0.0
                    }
                }
            }
            label.text = acc.toString()
            dot_used = true
            calc_state = state
            if (state == "=") {
                calc_state ="0"
            }
            time_to_hide = true
            return
        }
    }
}