package com.example.kashless

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.database.*

class LoggedWithNoFunction : AppCompatActivity() {

    private val database = FirebaseDatabase.getInstance()
    private val functionRef = database.reference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_logined_with_no_function)

        var intent = intent
        val joinButton = findViewById<Button>(R.id.join_the_function)
        val createText = findViewById<TextView>(R.id.create_function)
        // Joining a function
        joinButton.setOnClickListener {
            val checkDialog = LayoutInflater.from(this).inflate(R.layout.check_function_dialog,null)
            val checkBuilder = AlertDialog.Builder(this).setView(checkDialog).setTitle("   Join Function  ")
            // Showing dialog
            val checkAlertDialog = checkBuilder.show()

            checkDialog.findViewById<Button>(R.id.join_function).setOnClickListener {
                // Later use this
                val functionId = checkDialog.findViewById<EditText>(R.id.enter_function_id).text.toString()
                val myFunction = functionRef.child("function")//.child("functionID")
                var getData = object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        for (i in snapshot.children){
                            if (i.key == functionId){
                                var strFunctionID = i.child("functionID").value.toString()
                                startActivity(Intent(this@LoggedWithNoFunction, DisplayItems::class.java).putExtra("CheckValue",strFunctionID))
                                break
                            }else{
                                continue
                            }
                        }
                    }
                    override fun onCancelled(error: DatabaseError) {
                        Toast.makeText(applicationContext, error.message, Toast.LENGTH_SHORT).show()
                    }
                }
                myFunction.addValueEventListener(getData)
                myFunction.addListenerForSingleValueEvent(getData)
            }
        }
         //Creating function
        createText.setOnClickListener{
            var intent2 = Intent(this,CreateFunction::class.java)
            intent2.putExtra("uid",intent.getStringExtra("uid"))
            intent2.putExtra("email",intent.getStringExtra("email"))
            startActivity(intent2)
        }
    }
}