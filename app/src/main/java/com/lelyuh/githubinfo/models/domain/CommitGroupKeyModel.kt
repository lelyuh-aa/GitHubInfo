package com.lelyuh.githubinfo.models.domain

/**
 * Custom model for key of grouping map for collecting repo commits by months
 * Support compare obiects of this class to use it in containers with sorted order
 *
 * @constructor
 * @param       keyPar  pair integer values representing given month and year respectively (<0,2022> is January 2022)
 * @property    keyStr  string representation of month and year (for example, January 2022) to show in UI further
 *
 * @author Leliukh Aleksandr
 */
internal class CommitGroupKeyModel(
    private val keyPar: Pair<Int, Int>,
    val keyStr: String
) : Comparable<CommitGroupKeyModel> {

    override fun compareTo(other: CommitGroupKeyModel): Int {
        if (keyPar == other.keyPar) return 0
        if (keyPar.second < other.keyPar.second) return -1
        if (keyPar.second > other.keyPar.second) return 1
        return if (keyPar.first < other.keyPar.first) -1 else 1
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as CommitGroupKeyModel

        if (keyPar != other.keyPar) return false
        if (keyStr != other.keyStr) return false

        return true
    }

    override fun hashCode(): Int {
        var result = keyPar.hashCode()
        result = 31 * result + keyStr.hashCode()
        return result
    }
}
