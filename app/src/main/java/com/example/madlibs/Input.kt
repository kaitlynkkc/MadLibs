package com.example.madlibs

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import kotlinx.android.synthetic.main.activity_input.*
import java.io.File
import java.io.File.separator
import java.util.*
import kotlin.collections.HashMap

class Input : AppCompatActivity() {
    private val wordsToReplace = mutableListOf<String>()
    private val hint = mutableListOf<String>()
    private var wordCount = 0
    private var count = 0
    private var fileID = 0
    private val replace = HashMap<String, String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_input)
        if (intent != null) {
            val storyName = intent.getStringExtra("story")
            fileID = resources.getIdentifier(storyName, "raw", packageName)
            val inputFile = Scanner(resources.openRawResource(fileID))
            val pattern = "<\\w+>|<\\w+-\\w+>|<\\w+'s-+\\w+>|<\\w+-\\w+-\\w+>".toRegex()
            while(inputFile.hasNextLine()) {
                val storyLine = inputFile.nextLine()
                Log.d("LINE", storyLine)
                val word = pattern.findAll(storyLine)
                word.forEach { w ->
                    wordsToReplace.add(w.value)
                    var h = w.value.split("-")
                    var addHint = ""
                    for (word in h) {
                        addHint += word + " "
                    }
                    addHint = addHint.substring(1, addHint.length-3)
                    Log.d("TAG", addHint)
                    hint.add(addHint)
                    Log.d("TAG", w.value)
                    wordCount++
                }
            }
            editText.hint = hint[count]
            wordsRemaining.text = wordCount.toString() + " words remaining"
        }
    }

    fun inputClick(view: View) {
        replace[wordsToReplace[count]] = editText.text.toString().trim()
        editText.text = null
        wordCount--
        wordsRemaining.text = wordCount.toString() + " words remaining"
        if (wordCount > 0) {
            count++
            editText.hint = hint[count]
        } else {
            editText.isEnabled = false
            editText.hint = ""
        }
    }

    fun seeStory(view: View) {
        val myIntent = Intent(this, ReadStory::class.java)
        myIntent.putExtra("story", fileID)
        myIntent.putExtra("words", replace)
        startActivity(myIntent)
    }
}
