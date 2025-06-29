package com.ahmedonibiyo.websocketapp.model

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import okio.ByteString
import java.util.concurrent.TimeUnit

// Data class for messages
data class Message(
    val sender: String,
    val content: String,
    val timestamp: Long,
    val isReceived: Boolean
)

// Connection states
enum class ConnectionState {
    DISCONNECTED, CONNECTING, CONNECTED, FAILED
}

// ViewModel to handle WebSocket logic
class WebSocketViewModel : ViewModel() {
    private var webSocket: WebSocket? = null
    private var client: OkHttpClient? = null

    // WebSocket server URL - using echo server for testing
    private val SERVER_URL = "wss://echo.websocket.org"

    var messages = mutableStateListOf<Message>()
        private set

    var connectionState = mutableStateOf(ConnectionState.DISCONNECTED)
        private set

    init {
        client = OkHttpClient.Builder()
            .readTimeout(3, TimeUnit.SECONDS)
            .build()
    }

    fun connect() {
        if (connectionState.value == ConnectionState.CONNECTED) return

        connectionState.value = ConnectionState.CONNECTING

        val request = Request.Builder()
            .url(SERVER_URL)
            .build()

        client?.let {
            webSocket = it.newWebSocket(request, createWebSocketListener())
        }
    }

    fun disconnect() {
        webSocket?.close(1000, "User disconnected")
        connectionState.value = ConnectionState.DISCONNECTED
    }

    fun sendMessage(message: String) {
        if (message.isNotBlank() && connectionState.value == ConnectionState.CONNECTED) {
            webSocket?.send(message)
            addMessage("You", message, false)
        }
    }

    private fun addMessage(sender: String, content: String, isReceived: Boolean) {
        messages.add(Message(sender, content, System.currentTimeMillis(), isReceived))
    }

    private fun createWebSocketListener() = object : WebSocketListener() {
        override fun onOpen(webSocket: WebSocket, response: Response) {
            connectionState.value = ConnectionState.CONNECTED
            addMessage("System", "Connected to server", true)
        }

        override fun onMessage(webSocket: WebSocket, text: String) {
            addMessage("Server", text, true)
        }

        override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
            addMessage("Server", bytes.hex(), true)
        }

        override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
            webSocket.close(1000, null)
            connectionState.value = ConnectionState.DISCONNECTED
        }

        override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
            connectionState.value = ConnectionState.DISCONNECTED
            addMessage("System", "Disconnected from server", true)
        }

        override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
            connectionState.value = ConnectionState.FAILED
            addMessage("System", "Connection failed: ${t.message}", true)
        }
    }

    override fun onCleared() {
        super.onCleared()
        webSocket?.close(1000, "App closed")
        client?.dispatcher?.executorService?.shutdown()
    }
}