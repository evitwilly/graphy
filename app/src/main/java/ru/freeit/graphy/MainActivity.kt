package ru.freeit.graphy

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import ru.freeit.graphy.graph.GraphDefinedData
import ru.freeit.graphy.graph.GraphView
import ru.freeit.graphy.graph.Point

object Colors {
    val red = Color.rgb(255, 0, 0)
    val green = Color.rgb(0, 210, 0)
    val blue = Color.rgb(0, 0, 255)
    val white = Color.rgb(255, 255, 255)
}

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            val graphView = GraphView(context).apply {
                setPadding(16, 16, 16, 16)
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    600
                )
                changeLineColor(Colors.green)
            }
            addView(graphView)

            addView(Button(context).apply {
                text = "INCREASE && LINEAR"
                setTextColor(Colors.white)
                backgroundTintList = ColorStateList.valueOf(Colors.green)
                setOnClickListener {
                    graphView.changeLineColor(Colors.green)
                    graphView.changeMask(GraphDefinedData.positiveLinear)
                }
            })

            addView(Button(context).apply {
                text = "INCREASE && BROKEN"
                setTextColor(Colors.white)
                backgroundTintList = ColorStateList.valueOf(Colors.green)
                setOnClickListener {
                    graphView.changeLineColor(Colors.green)
                    graphView.changeMask(GraphDefinedData.positiveBroken)
                }
            })

            addView(Button(context).apply {
                text = "DECREASE && LINEAR"
                setTextColor(Colors.white)
                backgroundTintList = ColorStateList.valueOf(Colors.red)
                setOnClickListener {
                    graphView.changeLineColor(Colors.red)
                    graphView.changeMask(GraphDefinedData.negativeLinear)
                }
            })

            addView(Button(context).apply {
                text = "DECREASE && BROKEN"
                setTextColor(Colors.white)
                backgroundTintList = ColorStateList.valueOf(Colors.red)
                setOnClickListener {
                    graphView.changeLineColor(Colors.red)
                    graphView.changeMask(GraphDefinedData.negativeBroken)
                }
            })

            addView(Button(context).apply {
                text = "CUSTOM"
                setTextColor(Colors.white)
                backgroundTintList = ColorStateList.valueOf(Colors.blue)
                setOnClickListener {
                    graphView.changeLineColor(Colors.blue)
                    graphView.changeMask(listOf(
                        Point(0f, 0f),
                        Point(0.5f, 0.8f),
                        Point(0.9f, 0.1f)
                    ))
                }
            })

        })
    }
}