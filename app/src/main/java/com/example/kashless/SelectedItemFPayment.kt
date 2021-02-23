package com.example.kashless

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.flutterwave.raveandroid.RavePayActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import org.jetbrains.anko.doAsync
import com.example.kashless.momopay.RequestToPay

class SelectedItemFPayment : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_selected_item_f_payment)

        // ClientID: a4c2f14f59f60c68
        // SecretID: 	a1a3cad8b13b146e
        //Hover.initialize(this)
        val intent = intent
        val amount = "1000"// intent.getStringExtra("Price")
        val qty = intent.getStringExtra("Quantity")
        val itemName = intent.getStringExtra("ItemName")
        val functionID = intent.getStringExtra("CheckValue")
        val textToShow = findViewById<TextView>(R.id.text2)
        val textViewToAddText = findViewById<TextView>(R.id.text_for_the_item_selected)
        val button = findViewById<Button>(R.id.pay_button)
        val phoneNumber = findViewById<EditText>(R.id.foni_number).text.toString()
        //textToShow = textViewToAddText.text.toString() + intent.getStringExtra()
        //intent.getStringExtra("itemTitle") + " has a pending amount of" + intent.getStringExtra("price") +"for contribution"
        ("$itemName has a pending amount of $amount for contribution").also { textToShow.text = it }
        textViewToAddText.text = functionID//R.string.selected_item_info.toString()

        button.setOnClickListener{
            val dialog = LayoutInflater.from(this).inflate(R.layout.proceed_to_pay_dialog, null)
            val dialogBuilder = AlertDialog.Builder(this).setView(dialog).setTitle(R.string.confirm_contribution)
            val dialogBox =dialogBuilder.show()
            //////////////////////////////////////////////////////////////////////////////////////////////////
            /*-------------------------------Confirm pay button--------------------------------------------*/
            //////////////////////////////////////////////////////////////////////////////////////////////////
            dialog.findViewById<Button>(R.id.button_ok_to_pay).setOnClickListener {
                val boolCheck = RequestToPay().requestToPay("1000",phoneNumber)
                if(boolCheck == true){
                    Toast.makeText(this@SelectedItemFPayment,"$boolCheck",Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(this@SelectedItemFPayment,"$boolCheck",Toast.LENGTH_SHORT).show()
                    dialogBox.dismiss()
                }
            }

            ////////////////////////////////////////////////////////////////////////////////////////////////// rel 3:5
            /*--------------------------------Cancel pay button---------------------------------------------*/
            ////////////////////////////////////////////////////////////////////////////////////////////////
            dialog.findViewById<Button>(R.id.cancel_button).setOnClickListener {
                // cancel the process of paying for contribution
                dialogBox.dismiss()
            }
        }
    }
}