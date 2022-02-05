package net.codinux.kotlin.example.favicon.model

import kotlinx.serialization.Serializable


@Serializable
data class Size(val width: Int, val height: Int) : Comparable<Size?> {

    fun isSquare(): Boolean {
        return width == height
    }

    fun getDisplayText(): String {
        return "$width x $height"
    }


    override fun compareTo(other: Size?): Int {
        if (other == null) {
            return 1
        }

        if (width == other.width) {
            return height.compareTo(other.height)
        }

        return width.compareTo(other.width)
    }


    override fun toString(): String {
        return getDisplayText()
    }

}