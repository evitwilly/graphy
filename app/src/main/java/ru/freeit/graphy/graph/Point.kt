package ru.freeit.graphy.graph

/**
 * точка на графике
 *
 * @constructor
 * @property factorX - координата на оси X, где 0.0 - начало оси, 1.0 - конец
 * @property factorY - кооридната на оси Y, где 0.0 - начало оси, 1.0 - конец
 *
 */
data class Point(val factorX: Float, val factorY: Float)