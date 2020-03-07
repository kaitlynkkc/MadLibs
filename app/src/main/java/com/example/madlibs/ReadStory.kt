package com.example.madlibs

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_read_story.*
import java.util.*


class ReadStory : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_read_story)
        if (intent != null) {
            var finalStory = ""
            val fileID = intent.getIntExtra("story", 0)
            val replaceList = intent.getSerializableExtra("words") as HashMap<String, String>
            val inputFile = Scanner(resources.openRawResource(fileID))
            val pattern = "<\\w+>|<\\w+-\\w+>|<\\w+'s-+\\w+>|<\\w+-\\w+-\\w+>".toRegex()
            var storyLineNew = ""
            while (inputFile.hasNextLine()) {
                var storyLine = inputFile.nextLine()
                val word = pattern.findAll(storyLine)
                word.forEach { w ->
                    storyLine = storyLine.replace(w.value, replaceList[w.value].toString()) + '\t'
                    Log.d("TAG", storyLineNew)
                }
                finalStory += storyLine
            }
            storyTextView.text = finalStory
            storyTextView.movementMethod = ScrollingMovementMethod()
        }
    }
}
