package com.john.attendance.conf


class SharedCodeConfiguration {
    companion object {
        val url: String
            get() {
                return "https://www.weightwatchers.com/"
            }
        val connectTimeoutMillis: Long
            get() {
                return 60000
            }
        val requestTimeoutMillis: Long
            get() {
                return 60000
            }
    }
}