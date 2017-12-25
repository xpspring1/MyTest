package com.blackcat.xpsong.myasync

import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.toast


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        toast("hello world")
        btn.onClick {
            MyAsyncTask().execute("按钮给定信息")
        }

    }


   inner class MyAsyncTask:AsyncTask<String,Void,String>(){
       override fun doInBackground(vararg params: String): String {

           var mytext="这是后台进行处理的信息"
           mytext+=params[0]
           return mytext
       }

       override fun onPostExecute(result: String?) {
           super.onPostExecute(result)
           text.setText(result)


       }
   }
}

/*/---
class MainActivity : Activity(), Button.OnClickListener {

    internal var textView: TextView? = null
    internal var btnDownload: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        textView = findViewById(R.id.textView) as TextView
        btnDownload = findViewById(R.id.btnDownload) as Button
        Log.i("iSpring", "MainActivity -> onCreate, Thread name: " + Thread.currentThread().name)
    }

    fun onClick(v: View) {
        //要下载的文件地址
        val urls = arrayOf("http://blog.csdn.net/iispring/article/details/47115879", "http://blog.csdn.net/iispring/article/details/47180325", "http://blog.csdn.net/iispring/article/details/47300819", "http://blog.csdn.net/iispring/article/details/47320407", "http://blog.csdn.net/iispring/article/details/47622705")

        val downloadTask = DownloadTask()
        downloadTask.execute(*urls)
    }

    //public abstract class AsyncTask<Params, Progress, Result>
    //在此例中，Params泛型是String类型，Progress泛型是Object类型，Result泛型是Long类型
    private inner class DownloadTask : AsyncTask<String, Any, Long>() {
        override fun onPreExecute() {
            Log.i("iSpring", "DownloadTask -> onPreExecute, Thread name: " + Thread.currentThread().name)
            super.onPreExecute()
            btnDownload!!.setEnabled(false)
            textView!!.text = "开始下载..."
        }

        override fun doInBackground(vararg params: String): Long? {
            Log.i("iSpring", "DownloadTask -> doInBackground, Thread name: " + Thread.currentThread().name)
            //totalByte表示所有下载的文件的总字节数
            var totalByte: Long = 0
            //params是一个String数组
            for (url in params) {
                //遍历Url数组，依次下载对应的文件
                val result = downloadSingleFile(url)
                val byteCount = result[0] as Int
                totalByte += byteCount.toLong()
                //在下载完一个文件之后，我们就把阶段性的处理结果发布出去
                publishProgress(*result)
                //如果AsyncTask被调用了cancel()方法，那么任务取消，跳出for循环
                if (isCancelled) {
                    break
                }
            }
            //将总共下载的字节数作为结果返回
            return totalByte
        }

        //下载文件后返回一个Object数组：下载文件的字节数以及下载的博客的名字
        private fun downloadSingleFile(str: String): Array<Any> {
            val result = arrayOfNulls<Any>(2)
            var byteCount = 0
            var blogName = ""
            var conn: HttpURLConnection? = null
            try {
                val url = URL(str)
                conn = url.openConnection() as HttpURLConnection
                val `is` = conn!!.getInputStream()
                val baos = ByteArrayOutputStream()
                val buf = ByteArray(1024)
                var length = -1
                while ((length = `is`.read(buf)) != -1) {
                    baos.write(buf, 0, length)
                    byteCount += length
                }
                val respone = String(baos.toByteArray(), "utf-8")
                var startIndex = respone.indexOf("<title>")
                if (startIndex > 0) {
                    startIndex += 7
                    val endIndex = respone.indexOf("</title>")
                    if (endIndex > startIndex) {
                        //解析出博客中的标题
                        blogName = respone.substring(startIndex, endIndex)
                    }
                }
            } catch (e: MalformedURLException) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            } finally {
                if (conn != null) {
                    conn!!.disconnect()
                }
            }
            result[0] = byteCount
            result[1] = blogName
            return result
        }

        override fun onProgressUpdate(vararg values: Any) {
            Log.i("iSpring", "DownloadTask -> onProgressUpdate, Thread name: " + Thread.currentThread().name)
            super.onProgressUpdate(*values)
            val byteCount = values[0] as Int
            val blogName = values[1] as String
            var text = textView!!.text.toString()
            text += "\n博客《" + blogName + "》下载完成，共" + byteCount + "字节"
            textView!!.text = text
        }

        override fun onPostExecute(aLong: Long?) {
            Log.i("iSpring", "DownloadTask -> onPostExecute, Thread name: " + Thread.currentThread().name)
            super.onPostExecute(aLong)
            var text = textView!!.text.toString()
            text += "\n全部下载完成，总共下载了" + aLong + "个字节"
            textView!!.text = text
            btnDownload!!.setEnabled(true)
        }

        override fun onCancelled() {
            Log.i("iSpring", "DownloadTask -> onCancelled, Thread name: " + Thread.currentThread().name)
            super.onCancelled()
            textView!!.text = "取消下载"
            btnDownload!!.setEnabled(true)
        }
    }
}
*/