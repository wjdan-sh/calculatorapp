package com.example.calculatorapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView



class MainActivity : AppCompatActivity() {
    lateinit var tvsec: TextView
    lateinit var tvMain: TextView
    lateinit var bcle: Button
    lateinit var bc: Button
    lateinit var b0: Button
    lateinit var b9: Button
    lateinit var b8: Button
    lateinit var b7: Button
    lateinit var b6: Button
    lateinit var b5: Button
    lateinit var b4: Button
    lateinit var b3: Button
    lateinit var b2: Button
    lateinit var b1: Button
    lateinit var bnig: Button
    lateinit var bmul: Button
    lateinit var bminus: Button
    lateinit var bplus: Button
    lateinit var bequal: Button
    lateinit var bdot: Button
    lateinit var bdiv: Button
    var num1 = 0
    var num2 = 0
    var op =' '



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvsec = findViewById(R.id.idTVSecondary)
        tvMain = findViewById(R.id.idTVprimary)
        bcle = findViewById(R.id.bcle)
        bc = findViewById(R.id.bc)
        b0 = findViewById(R.id.b0)
        b9 = findViewById(R.id.b9)
        b8 = findViewById(R.id.b8)
        b7 = findViewById(R.id.b7)
        b6 = findViewById(R.id.b6)
        b5 = findViewById(R.id.b5)
        b4 = findViewById(R.id.b4)
        b3 = findViewById(R.id.b3)
        b2 = findViewById(R.id.b2)
        b1 = findViewById(R.id.b1)
        bnig = findViewById(R.id.bnig)
        bmul = findViewById(R.id.bmul)
        bminus = findViewById(R.id.bminus)
        bplus = findViewById(R.id.bplus)
        bequal = findViewById(R.id.bequal)
        bdot = findViewById(R.id.bdot)
        bdiv = findViewById(R.id.bdiv)


        b1.setOnClickListener {

            tvMain.text = (tvMain.text.toString() + "1")
        }
        b2.setOnClickListener {

            tvMain.text = (tvMain.text.toString() + "2")
        }
        b3.setOnClickListener {

            tvMain.text = (tvMain.text.toString() + "3")
        }
        b4.setOnClickListener {
            tvMain.text = (tvMain.text.toString() + "4")
        }
        b5.setOnClickListener {
            tvMain.text = (tvMain.text.toString() + "5")
        }
        b6.setOnClickListener {
            tvMain.text = (tvMain.text.toString() + "6")
        }
        b7.setOnClickListener {
            tvMain.text = (tvMain.text.toString() + "7")
        }
        b8.setOnClickListener {
            tvMain.text = (tvMain.text.toString() + "8")
        }
        b9.setOnClickListener {
            tvMain.text = (tvMain.text.toString() + "9")
        }
        b0.setOnClickListener {
            tvMain.text = (tvMain.text.toString() + "0")
        }
        bdot.setOnClickListener {
            tvMain.text = (tvMain.text.toString() + ".")
        }
        bplus.setOnClickListener {
            tvMain.text = (tvMain.text.toString() + "+")
        }
        bdiv.setOnClickListener {
            tvMain.text = (tvMain.text.toString() + "/")
        }

        bnig.setOnClickListener {
            tvMain.text = (tvMain.text.toString() + "-")
            tvsec.text = (bnig.text.toString())
        }

        bminus.setOnClickListener {

            val str: String = tvMain.text.toString()
            if (!str.get(index = str.length - 1).equals("-")) {
                tvMain.text = (tvMain.text.toString() + "-")
            }
        }
        bmul.setOnClickListener {
            val str: String = tvMain.text.toString()
            if (!str.get(index = str.length - 1).equals("*")) {
                tvMain.text = (tvMain.text.toString() + "*")
            }
        }

        bequal.setOnClickListener {
            val str: String = tvMain.text.toString()
            val result: Double = evaluate(str)
            val r = result.toString()
            tvMain.setText(r)
            tvsec.text = str
        }
        bcle.setOnClickListener {
            tvMain.setText("")
            tvsec.setText("")
        }
        bc.setOnClickListener {
            var str: String = tvMain.text.toString()
            if (!str.equals("")) {
                str = str.substring(0, str.length - 1)
                tvMain.text = str
            }
        }

    }


    fun evaluate(str: String): Double {
        return object : Any() {
            var pos = -1
            var ch = 0

            fun nextChar() {
                ch = if (++pos < str.length) str[pos].toInt() else -1
            }

            fun emty(emty: Int): Boolean {
                while (ch == ' '.toInt()) nextChar()
                if (ch == emty) {
                    nextChar()
                    return true
                }
                return false
            }

            fun parse(): Double {
                nextChar()
                val x = parseExpression()
                if (pos < str.length) throw RuntimeException("Unexpected: " + ch.toChar())
                return x
            }

            fun parseExpression(): Double {
                var x = parseTerm()
                while (true) {
                    if (emty('+'.toInt())) x += parseTerm()
                    else if (emty('-'.toInt())) x -= parseTerm()
                    else return x
                }
            }
            fun parseTerm(): Double {
                var x = parseFactor()
                while (true) {
                    if (emty('*'.toInt())) x *= parseFactor()
                    else if (emty('/'.toInt())) x /= parseFactor()
                    else return x
                }
            }

            fun parseFactor(): Double {

                if (emty('+'.toInt())) return parseFactor()
                if (emty('-'.toInt())) return -parseFactor()
                var x: Double
                val startPos = pos

                if (ch >= '0'.toInt() && ch <= '9'.toInt() || ch == '.'.toInt()) {

                    while (ch >= '0'.toInt() && ch <= '9'.toInt() || ch == '.'.toInt()) nextChar()

                    x = str.substring(startPos, pos).toDouble()
                } else if (ch >= 'a'.toInt() && ch <= 'z'.toInt()) {

                    while (ch >= 'a'.toInt() && ch <= 'z'.toInt()) nextChar()
                    val func = str.substring(startPos, pos)

                    x = parseFactor()

                } else {
                    throw RuntimeException("Unexpected: " + ch.toChar())

                }
                return x
            }

        }.parse()
    }

}
