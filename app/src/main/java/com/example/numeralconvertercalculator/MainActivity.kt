package com.example.numeralconvertercalculator

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.View.OnFocusChangeListener
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {
    private var etvDecimal: EditText? = null
    private var etvBinary: EditText? = null
    private var etvOctal: EditText? = null
    private var etvHexadecimal: EditText? = null
    private var btnClear: Button? = null
    private var value: String? = null
    private var onFocusChangeListener: OnFocusChangeListener? = null
    private var focusViewId = 0
    private var textWatcher: TextWatcher? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        etvDecimal = findViewById(R.id.etvDecimal)
        etvBinary = findViewById(R.id.etvBinary)
        etvOctal = findViewById(R.id.etvOctal)
        etvHexadecimal = findViewById(R.id.etvHexadecimal)
        btnClear = findViewById(R.id.btnClear)
        btnClear?.setOnClickListener(View.OnClickListener { clearFields() })
        textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                value =
                    (findViewById<View>(focusViewId) as EditText).text.toString().trim { it <= ' ' }
                if (value!!.isNotEmpty()) {
                    convertNumber()
                }
            }

            override fun afterTextChanged(s: Editable) {}
        }
        onFocusChangeListener = OnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                //Toast.makeText(getApplicationContext(),"Focus IN" , Toast.LENGTH_LONG).show();
                focusViewId = v.id
                (findViewById<View>(focusViewId) as EditText).addTextChangedListener(textWatcher)
                val gradientDrawable = GradientDrawable(
                    GradientDrawable.Orientation.TR_BL, intArrayOf(
                        Color.parseColor("#31b5b5"), Color.parseColor("#E6E6FA")
                    )
                )
                gradientDrawable.shape = GradientDrawable.RECTANGLE
                gradientDrawable.cornerRadius = 32f
                if (focusViewId == R.id.etvDecimal) {
                    gradientDrawable.setStroke(
                        8,
                        ContextCompat.getColor(applicationContext, android.R.color.holo_blue_bright)
                    )
                } else {
                    gradientDrawable.setStroke(
                        8,
                        ContextCompat.getColor(applicationContext, android.R.color.holo_blue_dark)
                    )
                }
                v.background = gradientDrawable
            } else {
                (findViewById<View>(focusViewId) as EditText).removeTextChangedListener(textWatcher)
                if (focusViewId == R.id.etvDecimal) {
                    v.background =
                        ContextCompat.getDrawable(applicationContext, R.drawable.focusview_design)
                } else {
                    v.background =
                        ContextCompat.getDrawable(applicationContext, R.drawable.focusview_design)
                }
            }
        }
        etvDecimal!!.onFocusChangeListener = onFocusChangeListener
        etvBinary!!.onFocusChangeListener = onFocusChangeListener
        etvOctal!!.onFocusChangeListener = onFocusChangeListener
        etvHexadecimal!!.onFocusChangeListener = onFocusChangeListener
    }

    private fun clearFields() {
        etvDecimal!!.setText("")
        etvBinary!!.setText("")
        etvOctal!!.setText("")
        etvHexadecimal!!.setText("")
    }

    private fun convertNumber() {
        try {
            var num: Long = 0
            when (focusViewId) {
                R.id.etvDecimal -> {
                    num = value!!.toLong()
                    etvBinary!!.setText(java.lang.Long.toBinaryString(num).toString())
                    etvOctal!!.setText(java.lang.Long.toOctalString(num).toString())
                    etvHexadecimal!!.setText(java.lang.Long.toHexString(num).toString())
                }
                R.id.etvBinary -> {
                    num = value!!.toLong(2)
                    etvDecimal!!.setText(num.toString())
                    etvOctal!!.setText(java.lang.Long.toOctalString(num).toString())
                    etvHexadecimal!!.setText(java.lang.Long.toHexString(num).toString())
                }
                R.id.etvOctal -> {
                    num = value!!.toLong(8)
                    etvDecimal!!.setText(num.toString())
                    etvBinary!!.setText(java.lang.Long.toBinaryString(num).toString())
                    etvHexadecimal!!.setText(java.lang.Long.toHexString(num).toString())
                }
                R.id.etvHexadecimal -> {
                    num = value!!.toLong(16)
                    etvDecimal!!.setText(num.toString())
                    etvOctal!!.setText(java.lang.Long.toOctalString(num).toString())
                    etvBinary!!.setText(java.lang.Long.toBinaryString(num).toString())
                }
            }
        } catch (e: NumberFormatException) {
            e.printStackTrace()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}