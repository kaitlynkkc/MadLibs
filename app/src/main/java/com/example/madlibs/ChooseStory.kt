package com.example.madlibs

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import java.io.File


class ChooseStory : AppCompatActivity() {

    private val stories = arrayOf("simple_story", "clothes", "dr_sykes_welcome", "dance", "tarzan", "university")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_story)

        val adapter = ArrayAdapter<String>(
            this, android.R.layout.simple_list_item_1, stories
        )
        val listView: ListView = findViewById(R.id.storyListView)
        listView.adapter = adapter

        listView.onItemClickListener =
            AdapterView.OnItemClickListener{_, view, position, _ ->
                val itemValue = listView.getItemAtPosition(position) as String
                storyClick(view, itemValue)
            }

    }

    fun storyClick(view: View, item: String) {
        val myIntent = Intent(this, Input::class.java)
        myIntent.putExtra("story", item)
        startActivity(myIntent)
    }
}
