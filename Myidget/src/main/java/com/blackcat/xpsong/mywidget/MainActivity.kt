package com.blackcat.xpsong.mywidget

import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebViewClient
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.startActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        /*
        my_web.getSettings().setJavaScriptEnabled(true)
        my_web.setWebViewClient(WebViewClient())
        my_web.loadUrl("http://www.sina.com.cn")
        Thread.sleep(5000)*/
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("http://www.baidu.com")))
    }
}
