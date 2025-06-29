package com.ahmedonibiyo.websocketapp


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ahmedonibiyo.websocketapp.ui.component.ConnectionState
import com.ahmedonibiyo.websocketapp.ui.component.Message
import com.ahmedonibiyo.websocketapp.ui.component.WebSocketViewModel
import com.ahmedonibiyo.websocketapp.ui.theme.ConnectedGreen
import com.ahmedonibiyo.websocketapp.ui.theme.ConnectingOrange
import com.ahmedonibiyo.websocketapp.ui.theme.DisconnectedGray
import com.ahmedonibiyo.websocketapp.ui.theme.FailedRed
import com.ahmedonibiyo.websocketapp.ui.theme.PureBlack
import com.ahmedonibiyo.websocketapp.ui.theme.PureWhite
import com.ahmedonibiyo.websocketapp.ui.theme.ReceivedMessageBackground
import com.ahmedonibiyo.websocketapp.ui.theme.ReceivedMessageSender
import com.ahmedonibiyo.websocketapp.ui.theme.SentMessageBackground
import com.ahmedonibiyo.websocketapp.ui.theme.SentMessageSender
import com.ahmedonibiyo.websocketapp.ui.theme.TextGray
import com.ahmedonibiyo.websocketapp.ui.theme.WebSocketAppTheme
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WebSocketAppTheme {
                WebSocketApp()
            }
        }
    }
}

@Composable
fun WebSocketApp(viewModel: WebSocketViewModel = viewModel()) {
    var messageText by remember { mutableStateOf("") }
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    // Auto-scroll to bottom when new messages arrive
    LaunchedEffect(viewModel.messages.size) {
        if (viewModel.messages.isNotEmpty()) {
            coroutineScope.launch {
                listState.animateScrollToItem(viewModel.messages.size - 1)
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Connection Status
        ConnectionStatusCard(
            connectionState = viewModel.connectionState.value,
            onConnect = { viewModel.connect() },
            onDisconnect = { viewModel.disconnect() }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Messages List
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            LazyColumn(
                state = listState,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(viewModel.messages) { message ->
                    MessageItem(message = message)
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Message Input
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = messageText,
                onValueChange = { messageText = it },
                label = { Text("Type a message...") },
                modifier = Modifier.weight(1f),
                enabled = viewModel.connectionState.value == ConnectionState.CONNECTED
            )

            Spacer(modifier = Modifier.width(8.dp))

            Button(
                onClick = {
                    viewModel.sendMessage(messageText)
                    messageText = ""
                },
                enabled = viewModel.connectionState.value == ConnectionState.CONNECTED && messageText.isNotBlank()
            ) {
                Text("Send")
            }
        }
    }
}

@Composable
fun ConnectionStatusCard(
    connectionState: ConnectionState,
    onConnect: () -> Unit,
    onDisconnect: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = when (connectionState) {
                ConnectionState.CONNECTED -> ConnectedGreen
                ConnectionState.CONNECTING -> ConnectingOrange
                ConnectionState.FAILED -> FailedRed
                ConnectionState.DISCONNECTED -> DisconnectedGray
            }
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Status: ${connectionState.name}",
                color = PureWhite,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = onConnect,
                    enabled = connectionState != ConnectionState.CONNECTED && connectionState != ConnectionState.CONNECTING,
                    colors = ButtonDefaults.buttonColors(containerColor = PureWhite)
                ) {
                    Text("Connect", color = PureBlack)
                }

                Button(
                    onClick = onDisconnect,
                    enabled = connectionState == ConnectionState.CONNECTED,
                    colors = ButtonDefaults.buttonColors(containerColor = PureWhite)
                ) {
                    Text("Disconnect", color = PureBlack)
                }
            }
        }
    }
}

@Composable
fun MessageItem(message: Message) {
    val timeFormat = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
    val formattedTime = timeFormat.format(Date(message.timestamp))

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = if (message.isReceived) Arrangement.Start else Arrangement.End
    ) {
        Card(
            modifier = Modifier.widthIn(max = 280.dp),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(
                containerColor = if (message.isReceived) ReceivedMessageBackground else SentMessageBackground
            )
        ) {
            Column(
                modifier = Modifier.padding(12.dp)
            ) {
                Text(
                    text = message.sender,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    color = if (message.isReceived) ReceivedMessageSender else SentMessageSender
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = message.content,
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = formattedTime,
                    fontSize = 12.sp,
                    color = TextGray
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun WebSocketAppPreview() {
    WebSocketAppTheme {
        WebSocketApp()
    }
}
