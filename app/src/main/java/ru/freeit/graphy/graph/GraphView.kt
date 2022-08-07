package ru.freeit.graphy.graph

import android.animation.Animator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.view.View
import androidx.core.animation.doOnStart

class GraphView(ctx: Context) : View(ctx) {

    /**
     * внутреннее представление точки
     *
     */
    private class InternalPoint(
        private val x: Float,
        private val y: Float
    ) {

        fun circleCoordinates(strokeWidth: Float = 0f) = Pair(x, y + strokeWidth / 2f)

        fun moveTo(path: Path) {
            path.moveTo(x, y)
        }
        fun lineTo(path: Path) {
            path.lineTo(x, y)
        }

    }

    private val defaultColor = Color.rgb(0, 255, 0)

    private var lineColor: Int = defaultColor
    private var currentMask = GraphDefinedData.positiveBroken

    private var circleX = 0f
    private var circleY = 0f

    private var bottom = 0f
    private var left = 0f
    private var right = 0f
    private var top = 0f

    private var currentAnimator: Animator? = null

    private val path = Path()

    private val linePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = lineColor
        style = Paint.Style.STROKE
        strokeWidth = 15f
        strokeJoin = Paint.Join.ROUND
        strokeCap = Paint.Cap.ROUND
        pathEffect = CornerPathEffect(strokeWidth)
    }

    private val strokePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.rgb(0, 0, 0)
        style = Paint.Style.STROKE
        strokeWidth = 10f
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        left = paddingStart.toFloat()
        top = paddingTop.toFloat()
        right = w - paddingEnd.toFloat()
        bottom = h - paddingBottom.toFloat()

        clipBounds = Rect(paddingStart, paddingTop, w - paddingEnd, h - paddingBottom)

        calculatePoints()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawPath(path, linePaint)
        canvas.drawCircle(circleX, circleY,10f, linePaint)
        val strokeWidth = strokePaint.strokeWidth / 2f
        canvas.drawLine(left + strokeWidth, top, left + strokeWidth, bottom, strokePaint)
        canvas.drawLine(left, bottom - strokeWidth, right, bottom - strokeWidth, strokePaint)
    }

    private fun calculatePoints() {

        path.reset()

        val points = generatePoints(currentMask)

        currentAnimator?.cancel()
        currentAnimator = animatePoints(points)
    }

    private fun generatePoints(mask: List<Point>): List<InternalPoint> {
        var currentIndex = 1
        val points = mutableListOf<InternalPoint>()
        while (currentIndex < mask.size) {
            val startPoint = mask[currentIndex - 1]
            val endPoint = mask[currentIndex]

            var currentFactorX = startPoint.factorX
            val step = (endPoint.factorX - startPoint.factorX) / 100

            val linearCoefficient = (endPoint.factorY - startPoint.factorY) / (endPoint.factorX - startPoint.factorX)
            val numberCoefficient = endPoint.factorY - linearCoefficient * endPoint.factorX

            while (currentFactorX <= endPoint.factorX) {

                val x = (left + right * currentFactorX).coerceAtMost(right)
                val y = (bottom - bottom * (linearCoefficient * currentFactorX + numberCoefficient)).coerceAtLeast(top)

                points.add(InternalPoint(x, y))

                currentFactorX += step
            }

            currentIndex++
        }
        return points
    }

    private fun animatePoints(points: List<InternalPoint>): Animator {
        val animator = ValueAnimator.ofInt(0, points.size - 1)
        animator.doOnStart {
            points.first().moveTo(path)
        }
        animator.addUpdateListener { animator ->
            val progress = animator.animatedValue as Int

            val point = points[progress]
            point.lineTo(path)

            val circleCoordinates = point.circleCoordinates()
            circleX = circleCoordinates.first
            circleY = circleCoordinates.second

            invalidate()
        }
        animator.duration = 1000
        animator.start()
        return animator
    }

    fun changeMask(mask: List<Point>) {
        if (currentMask == mask) return

        this.currentMask = mask
        calculatePoints()
    }

    fun changeLineColor(color: Int) {
        if (linePaint.color == color) return
        linePaint.color = color
        calculatePoints()
    }

}