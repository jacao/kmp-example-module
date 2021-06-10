package com.unidays.headertestmodule

class HeaderBuilder {
    fun getHeader(headerId: Int): String {
        return if (headerId == TEST_HEADER_ID)
            TEST_HEADER
        else
            SOME_OTHER_HEADER
    }
    companion object {
        const val TEST_HEADER = "Header String"
        const val TEST_HEADER_ID = 1
        const val SOME_OTHER_HEADER = "Some other shit"
    }
}