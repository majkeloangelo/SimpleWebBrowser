package com.example.webview

import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.webview.ui.theme.WebViewTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WebViewTheme {
                Surface(
                    modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)
                ) {
                    WebViewer()
                }
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun WebViewer() {
    var url by remember { mutableStateOf("https://www.google.com") }
    val webViewState = rememberWebViewState()
    val keyboardController = LocalSoftwareKeyboardController.current

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
            Text(
                text = "Simple web browser",
                style = TextStyle(
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                    color = Color(15, 33, 68),
                )
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextField(
                    value = url,
                    onValueChange = { url = it },
                    label = { Text("Enter URL") },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            keyboardController?.hide()
                        }
                    ),
                    modifier = Modifier
                        .weight(1f)
                        .size(width = 350.dp, height = 60.dp)
                        .border(1.dp, Color.Gray),
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color(222, 222, 222),
                        cursorColor = Color(15, 33, 68),
                        focusedIndicatorColor = Color(15, 33, 68),
                        unfocusedIndicatorColor = Color.Gray,
                        focusedPlaceholderColor = Color.Transparent,
                        unfocusedPlaceholderColor = Color.Gray,
                        disabledTextColor = Color.Gray,
                        focusedTextColor = Color(15, 33, 68),
                    )
                )

                Button(
                    modifier = Modifier
                        .size(width = 70.dp, height = 60.dp)
                        .border(1.dp, Color.Gray),
                    shape = RectangleShape,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(200, 200, 200),
                        contentColor = Color(15, 33, 68),
                    ),
                    onClick = {
                        webViewState.loadUrl(url)
                    }
                ) {
                    Text(
                        "Go",
                        style = TextStyle(fontSize = 18.sp)
                    )
                }
            }
            AndroidView(
                factory = { context ->
                    WebView(context).apply {
                        webViewClient = WebViewClient()
                        settings.javaScriptEnabled = true
                        loadUrl(ValidUrl(url))
                        webViewState.setWebView(this)
                    }
                },
                modifier = Modifier
                    .fillMaxSize()
                    .border(1.dp, Color.Gray)
            )
        }
    }

@Composable
fun rememberWebViewState(): WebViewState {
    return remember { WebViewState() }
}

class WebViewState {
    private var webView: WebView? = null

    fun setWebView(webView: WebView) {
        this.webView = webView
    }

    fun loadUrl(url: String) {
        webView?.loadUrl(ValidUrl(url))
    }

}
fun ValidUrl(url: String): String {
    var validUrl = ""

    if (url.startsWith("https://") || url.startsWith("http://")) {
        validUrl = url
        return validUrl
    } else {
        if (url.startsWith("www.")) {
            validUrl = "https://$url"
            return validUrl
        } else {
            validUrl = "https://$url"
            return validUrl
        }
    }
}
