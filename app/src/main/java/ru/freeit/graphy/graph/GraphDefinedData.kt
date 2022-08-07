package ru.freeit.graphy.graph

/**
 * уже определенные данные для графика
 *
 */
object GraphDefinedData {

    val positiveBroken = listOf(
        Point(0f, 0f),
        Point(0.2f, 0.4f),
        Point(0.35f, 0.3f),
        Point(0.55f, 0.6f),
        Point(0.75f, 0.5f),
        Point(0.92f, 0.78f),
    )

    val negativeBroken = listOf(
        Point(0f, 0.8f),
        Point(0.12f, 0.55f),
        Point(0.3f, 0.65f),
        Point(0.5f, 0.35f),
        Point(0.7f, 0.45f),
        Point(0.92f, 0.2f)
    )

    val positiveLinear = listOf(
        Point(0f, 0.1f),
        Point(0.95f, 0.7f)
    )

    val negativeLinear = listOf(
        Point(0f, 0.8f),
        Point(0.95f, 0.2f)
    )

}