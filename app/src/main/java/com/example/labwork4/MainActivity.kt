package com.example.labwork4

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Exception

class MainActivity : AppCompatActivity() {


    private var result: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE) //will hide the title
        supportActionBar?.hide()
        setContentView(R.layout.activity_main)

        result = findViewById(R.id.result)

    }

     fun calc(view: View) {

        try {
            result?.text = " "
            val a = fromA.text.toString().toDouble()
            val b = toB.text.toString().toDouble()
            val e = eps.text.toString().toDouble()

            val expr = expression.text.toString()
            val work = WorkClass()
            val res = work.solve(expr, a, b, e)[0].toString()
            if (res.toDouble() == a){
                result?.append("Не існує розв'язку в заданому інтервалі.")
            }else {
                result?.append(res)
            }

        }catch (e: Exception){
            result?.append("Помилка!")
            Toast.makeText( this, "Input correct values!", Toast.LENGTH_SHORT).show()
            return
        }
    }
}
