package com.example.rvapp

import android.annotation.SuppressLint
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    private lateinit var rvMain: ConstraintLayout
    private lateinit var EnterField: EditText
    private lateinit var Enterbtn: Button
    private lateinit var messages: ArrayList<String>
    private lateinit var screenTV: TextView

    private var correct = 0
    private var numOfGuess = 3

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        correct = Random.nextInt(10)

        rvMain = findViewById(R.id.rvMain)
        messages = ArrayList()

        screenTV = findViewById(R.id.prompt)
        rvMessages.adapter = MessageAdapter(this, messages)
        rvMessages.layoutManager = LinearLayoutManager(this)

        EnterField = findViewById(R.id.Enterfeild)
        Enterbtn = findViewById(R.id.Enterbtn)

        Enterbtn.setOnClickListener { addMessage() }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun addMessage(){
        val msg = EnterField.text.toString()
        if(msg.isNotEmpty()){
            if(numOfGuess>0){
                if(msg.toInt() == correct){
                    disableEntry()
                    showAlertDialog("You win! Want to play again?")
                }else{
                    numOfGuess--
                    messages.add("You have guessed $msg")
                    messages.add("You have $numOfGuess guesses left")
                }
                if(numOfGuess==0){
                    disableEntry()
                    messages.add("You lose - The correct answer was $correct")
                    messages.add("Game Over")
                    showAlertDialog("You lose... The correct answer was $correct.Want to play again?")
                }
            }
            EnterField.text.clear()
            EnterField.clearFocus()
            rvMessages.adapter?.notifyDataSetChanged()
        }else{
            Snackbar.make(rvMain, "Please enter a number", Snackbar.LENGTH_LONG).show()
        }
    }

    private fun disableEntry(){
        EnterField.isEnabled = false
        Enterbtn.isClickable = false
        EnterField.isEnabled = false
        EnterField.isClickable = false
    }

    private fun showAlertDialog(title: String) {
        // build alert dialog
        val dialogBuilder = AlertDialog.Builder(this)

        // set message of alert dialog
        dialogBuilder.setMessage(title)
            // if the dialog is cancelable
            .setCancelable(false)
            // positive button text and action
            .setPositiveButton("Yes", DialogInterface.OnClickListener {
                    dialog, id -> this.recreate()
            })
            // negative button text and action
            .setNegativeButton("No", DialogInterface.OnClickListener {
                    dialog, id -> dialog.cancel()
            })

        // create dialog box
        val alert = dialogBuilder.create()
        // set title for alert dialog box
        alert.setTitle("Game Over")
        // show alert dialog
        alert.show()
    }
}
