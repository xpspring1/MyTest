package com.blackcat.xpsong.mywidget

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_animal.*

class AnimalActivity : AppCompatActivity() {
    val AN="animal_name"
    val AI="animal_image_id"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_animal)
        var animalName=intent.getStringExtra(AN)
        var animalImageId=intent.getIntExtra(AI,0)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        collapsing_toolbar.title=animalName
        Glide.with(this).load(animalImageId).into(animal_image_view)
        var animalContent:String=generateAnimalContent(animalName)
        animal_content_text.text=animalContent
    }
    fun generateAnimalContent(animalName:String):String{
        var a=animalName
        for(i in 1..10){a+=a}
        return a
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
