package com.blackcat.xpsong.mywidget

import android.content.Context
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.view.GravityCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.CardView
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import com.blackcat.xpsong.mywidget.R.id.animal_image
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.animal_item.view.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.toast
import java.util.*


class MainActivity : AppCompatActivity() {

    var animals=arrayOf(
            Animal("bear",R.drawable.bear),
            Animal("cat",R.drawable.cat),
            Animal("dog2",R.drawable.dog2),
            Animal("fox",R.drawable.fox),
            Animal("panda",R.drawable.panda),
            Animal("pig",R.drawable.pig),
            Animal("rabbit",R.drawable.rabbit),
            Animal("rat",R.drawable.rat),
            Animal("tiger",R.drawable.tiger))
    var animalList=mutableListOf<Animal>()
    lateinit var myAdapter:AnimalAdapter
    fun initAnimals(){
        animalList.clear()
        for (i in 1..60){
            var index=Random().nextInt(animals.size)
            animalList.add(animals[index])
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(mtoolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.mmenu)
        nav_view.setCheckedItem(R.id.nav_call)
        //NavigationItem监听器，使用{ param -> {} }形式的lambda表达式
        //设定监听器方式为 .setXXXListner{ }的方式
        nav_view.setNavigationItemSelectedListener { item: MenuItem ->
            when (item.itemId) {
                R.id.nav_call -> toast("call clicked")
                R.id.nav_friends -> toast("friends clicked")
            }
            true
        }
        //悬浮按钮监听器
        fab.onClick { mview: View? ->
            Snackbar.make(mview!!, "Data deleted", Snackbar.LENGTH_LONG)
                    .setAction("Undo", { toast("Data restored") })
                    .show()
        }

        initAnimals()   //初始化animalList
        //var mRecyclerView=recycler_view as RecyclerView
        recycler_view.layoutManager=GridLayoutManager(this,3)
        myAdapter=AnimalAdapter(animalList)
        recycler_view.adapter=myAdapter

        //下拉刷新操作
        swipe_refresh.setColorSchemeResources(R.color.colorPrimary)
        swipe_refresh.setOnRefreshListener { refreshanimal() }
    }
    fun refreshanimal(){
        Thread(Runnable{
            Thread.sleep(2000)
            runOnUiThread {
                initAnimals()
                myAdapter.notifyDataSetChanged()
                swipe_refresh.isRefreshing=false
            }
        }).start()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.backup -> toast("开始备份。。。")
            R.id.delete -> toast("删除资料。。。")
            R.id.settings -> toast("打开设置。。。")
            android.R.id.home -> drawer_layout.openDrawer(GravityCompat.START)
        }
        return super.onOptionsItemSelected(item)
    }


}
class Animal(var name:String,var imageId:Int)
/**
 * RecyclerView.Adapter为抽象类，使用其对象前，需实现其所有抽象方法
 * 继承RecyclerView.Adapter，实现其三个方法：onCreateViewHolder，onBindViewHolder，getItemCount
 * 使用传入的MutableList<Animal>来为AnimalAdapter中的ViewHolder赋值
 * 使用时将此Adapter设置给Rcyclerview
 */
class AnimalAdapter(var mAnimalList:MutableList<Animal>):RecyclerView.Adapter<AnimalAdapter.ViewHolder>(){
        lateinit var mContext:Context
        //定义一个ViewHolder类
        class ViewHolder(view: View):RecyclerView.ViewHolder(view){
            var animalImage:ImageView=view.animal_image     //取得view的ImageView对象
            var animalName:TextView=view.animal_name     //取得view的TextView对象
        }
        //生成一个ViewHolder(view)对象，传递给其他方法使用
        override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
            mContext=parent!!.context
            //使用animal_item.xml布局文件进行inflate
            var view:View=LayoutInflater.from(mContext).inflate(R.layout.animal_item,parent,false)
            return ViewHolder(view)
        }
        //接收ViewHolder(view)，及其postion参数，给animla_item.xml 中的对象赋值
        override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
            var animal:Animal=mAnimalList.get(position)     //取出一个Animal对象
            holder?.animalName?.setText(animal.name)        //设置文字
            Glide.with(mContext).load(animal.imageId).into(holder?.animalImage)     //使用glide加载图片
        }

        override fun getItemCount(): Int {
            return mAnimalList.size
        }
    }




