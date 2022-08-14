package com.kelvinsugiarto.gituserapp.data.network

import okhttp3.mockwebserver.MockWebServer

class CustomMockWebServer {
    private val server = MockWebServer()

    var port: Int = 8080
        set(value)  {
            if (value >= 0) field = value
        }


    fun startServer() {
        server.start(port)
    }

    fun startServer(port: Int) {
        this.port = port
        startServer()
    }

    fun closeServer() { server.shutdown() }
}