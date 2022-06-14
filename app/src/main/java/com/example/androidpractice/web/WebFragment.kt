package com.example.androidpractice.web

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.*
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.*
import android.widget.Toast
import androidx.annotation.MainThread
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.androidpractice.databinding.FragmentWebBinding

class WebFragment : Fragment(){

    private val binding: FragmentWebBinding by lazy {
        FragmentWebBinding.inflate(LayoutInflater.from(context))
    }

    private val webUrl: String = "https://www.naver.com"
    private val htmlData: String = "\n" +
            "<!doctype html>\n" +
            "<html> \n" +
            "    <head>\n" +
            "        <title>Example: 2-1</title>\n" +
            "    </head>\n" +
            "    <body>\n" +
            "        <h1>example 2-1</h1>\n" +
            "        <h2>html example</h2>\n" +
            "        \n" +
            "        <p>hello world</p>\n" +
            "        <p>hello\n" +
            "            world\n" +
            "        </p>\n" +
            "        <p>hello<br>world</p>\n" +
            "        <pre>\n" +
            "            hello world\n" +
            "            hello\n" +
            "                world\n" +
            "        </pre>\n" +
            "        <pre>\n" +
            "            <h2>hello\n" +
            "                    world\n" +
            "            </h2>\n" +
            "        </pre>\n" +
            "    </body>\n" +
            "</html>"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    @SuppressLint("JavascriptInterface")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with (binding) {
            webView.apply {
                webViewClient = object : WebViewClient() {
                    override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                        super.onPageStarted(view, url, favicon)
                    }

                    override fun shouldOverrideUrlLoading(
                        view: WebView?,
                        request: WebResourceRequest?
                    ): Boolean {
                        val redirectUrl = request?.url.toString()
                        if (redirectUrl.startsWith("redirect/success")) {
                            return true
                        } else if (redirectUrl.startsWith("redirect/fail")) {
                            return true
                        }
                        return false
                    }

                    override fun onPageFinished(view: WebView?, url: String?) {
                        super.onPageFinished(view, url)
                    }
                }
                webChromeClient = object : WebChromeClient() {
                    override fun onCloseWindow(window: WebView?) {
                        if (window?.canGoBack() == true) {
                            window.goBack()
                        }
                    }

                    override fun onCreateWindow(
                        view: WebView?,
                        isDialog: Boolean,
                        isUserGesture: Boolean,
                        resultMsg: Message?
                    ): Boolean {
                        return super.onCreateWindow(view, isDialog, isUserGesture, resultMsg)
                    }
                }
                settings.run {
                    javaScriptEnabled = true
                }
                addJavascriptInterface(WebBridgeInterface(root.context), "WebBridge")
                evaluateJavascript("javascript:executeFunction()", { callback ->

                })
                loadUrl(webUrl)
//                loadDataWithBaseURL(null, htmlData, "text/html", "UTF-8", null)
            }
        }
    }


    private fun openWebByIntent() {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(webUrl))
        startActivity(intent)
    }
}

class WebBridgeInterface(private val context: Context) {

    @JavascriptInterface
    fun eventHandler(type: String, message: String) {
        Handler(Looper.getMainLooper()).post {
            Toast.makeText(context, "$type $message", Toast.LENGTH_SHORT).show()
        }
    }
}