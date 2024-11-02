import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.launch

@Composable
fun TestChatScreen() {
    // State for the list of messages
    val messages = remember {
        mutableStateListOf(
            "Hello!",
            "Hi, how are you?",
            "I'm good, thank you! How about you?",
            "I'm doing well, thank you!",
            "That's great to hear!"
        )
    }

    // State for text input and LazyList state
    var inputText by remember { mutableStateOf(TextFieldValue()) }
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        // Chat Messages in a LazyColumn
        LazyColumn(
            state = listState,
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(8.dp),
            reverseLayout = true // Show latest messages at the bottom
        ) {
            items(messages) { message ->
                MessageBubble(message = message)
            }
        }

        // Input Field and Send Button
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            BasicTextField(
                value = inputText,
                onValueChange = { inputText = it },
                modifier = Modifier
                    .weight(1f)
                    .padding(8.dp)
                    .background(Color.LightGray, shape = MaterialTheme.shapes.medium)
                    .padding(8.dp),
                decorationBox = { innerTextField ->
                    if (inputText.text.isEmpty()) {
                        Text("Type a message...", color = Color.Gray)
                    }
                    innerTextField()
                }
            )
            Spacer(modifier = Modifier.width(8.dp))
            Button(
                onClick = {
                    if (inputText.text.isNotBlank()) {
                        messages.add(0, inputText.text) // Add message at the top
                        inputText = TextFieldValue() // Clear the input
                        // Scroll to the newest message
                        coroutineScope.launch {
                            listState.scrollToItem(0)
                        }
                    }
                }
            ) {
                Text("Send")
            }
        }
    }
}

@Composable
fun MessageBubble(message: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.Start
    ) {
        Text(
            text = message,
            modifier = Modifier
                .background(Color.LightGray, shape = MaterialTheme.shapes.medium)
                .padding(8.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ChatScreenPreview() {
    TestChatScreen()
}
