package com.blackcat.xpsong.SocketTest
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Message
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.toast
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.net.Socket

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        toast("启动成功")

        var myHandler:Handler=object:Handler(){
            override fun handleMessage(msg:Message?){
                super.handleMessage(msg)
                receive.setText(msg?.obj.toString())
            }
        }
        sbtn.onClick { toast("点击了发送按钮") }

        rbtn.setOnClickListener {

            Thread(Runnable {
                run {
                    var s = Socket("192.168.1.102", 30000)

                    var out = BufferedWriter(OutputStreamWriter(s.getOutputStream(), Charsets.UTF_8))
                    var bless = send.text.toString()
                    out.write(bless)
                    out.flush()
                    s.shutdownOutput()


                    //接收消息
                    var ins = BufferedReader(InputStreamReader(s.getInputStream(), Charsets.UTF_8))
                    var instext: String = ins.readLine()
                    var msg = Message()
                    msg.obj = instext
                    myHandler.sendMessage(msg)
                    //s.shutdownInput()

                    //ins.close()
                    //out.close()
                    //s.close()
                }
            }).start()

        }
    }

}










