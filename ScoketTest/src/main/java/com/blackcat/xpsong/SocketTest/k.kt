package com.blackcat.xpsong.SocketTest

import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.net.ServerSocket


/**
 * Created by xpsong on 2017/12/20.
 */

fun main(args:Array<String>){
    var ss= ServerSocket(30000)
    while(true){

        var s=ss.accept()
        //给客户端发送消息
        var out= BufferedWriter(OutputStreamWriter(s.getOutputStream(),Charsets.UTF_8))
        var bless = "这是来自服务器的最新消息！"
        out.write(bless)
        out.flush()
        println("写入了服务器的数据")

        //获取客户端信息
        var ins= BufferedReader(InputStreamReader(s.getInputStream(),Charsets.UTF_8))
        println("执行reader成功")
        val instext=ins.readText()
        println("读取reader成功")

        if (instext== "发送内容") {
            println("连接成功!")
        } else {
            println("密码错误！客户端信息： $instext")
        }

        ins.close()
        out.close()
        s.close()
    }
}