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
import com.example.vkash.Model.FunctionInfo
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
class CreateFunction : AppCompatActivity() {
    // Calling the database
    private val database = FirebaseDatabase.getInstance()
    val myRef = database.reference
    //    val mAuth: FirebaseAuth? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_function)
        var intent = intent
        val title = findViewById<EditText>(R.id.title_of_the_wedding).text.toString()
        val mtnNumber = findViewById<EditText>(R.id.number_for_contribution).text.toString()
        val weddingDescription = findViewById<EditText>(R.id.wedding_description).text.toString()
        val location = findViewById<EditText>(R.id.location_of_the_function).text.toString()
        val names = findViewById<EditText>(R.id.names_of_the_couple).text.toString()
        val uploaded = findViewById<TextView>(R.id.uploadedfile)
        val button = findViewById<Button>(R.id.up_load_budget)
        val buttonCreate = findViewById<Button>(R.id.create_function)

         var itemsAdded = HashMap<String, List<Int>>()
        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener{
            val addDialog = LayoutInflater.from(this).inflate(R.layout.add_item_for_function,null)
            val addBuilder = AlertDialog.Builder(this).setView(addDialog).setTitle("     Add Item    ")

            val addAlertDialog = addBuilder.show()
            addDialog.findViewById<Button>(R.id.add_item_button).setOnClickListener {
                val item = addDialog.findViewById<EditText>(R.id.item_name).text.toString()
                val qty = addDialog.findViewById<EditText>(R.id.item_quantity).text.toString()
                val price = addDialog.findViewById<EditText>(R.id.price_item).text.toString()

                if (item.isNotEmpty() &&  price.isNotEmpty()){
                    val list1 = listOf(qty.toInt(), price.toInt())
                    itemsAdded[item] = list1
                    Toast.makeText(applicationContext, "One item added, so they are"+(itemsAdded.size+1).toString()+"items so far",Toast.LENGTH_SHORT).show()
                    addAlertDialog.dismiss()
                }else{
                    Toast.makeText(applicationContext,"One of the fields is empty",Toast.LENGTH_SHORT).show()
                }

            }

        }
        ////////////////////////////////////////////////////// Uploading file//////////////////////////////////////////////////////
        //button.setOnClickListener {
        // }
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        /*---------------------------------------------------- Create button----------------------------------------------------*/
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        buttonCreate.setOnClickListener {
            val functionId = generateID()
            var email = intent.getStringExtra("email").toString()
            var uidForCurrentUser =  intent.getStringExtra("uid").toString()
            val functionInfo = FunctionInfo(names,location,title,weddingDescription,functionId,mtnNumber,email,uidForCurrentUser,itemsAdded,0)
            myRef.child("function").child(functionId.toString()).setValue(functionInfo)
                      .addOnCompleteListener{
                          // Add some information to the user's information
                          val checkRef = myRef.child("users").child(uidForCurrentUser).child("ListForFunctionIDs")
                          val valueEventListener = object : ValueEventListener {
                              override fun onDataChange(snapshot: DataSnapshot) {
                                  if(snapshot.exists()){
                                      myRef.child("users/$uidForCurrentUser/ListForFunctionIDs").setValue(functionId)
                                  }else{
                                      myRef.child("users/$uidForCurrentUser/ListForFunctionIDs").setValue(functionId)
                                  }
                              }
                              override fun onCancelled(error: DatabaseError) {
                                  Toast.makeText(applicationContext, error.message, Toast.LENGTH_SHORT).show()
                              }
                          }
                          checkRef.addValueEventListener(valueEventListener)
                          checkRef.addListenerForSingleValueEvent(valueEventListener)
                          val dialogBox = LayoutInflater.from(this).inflate(R.layout.function_created_show_function_id,null)
                          val dialogBuilder = AlertDialog.Builder(this).setView(dialogBox).setTitle("Function Information Added")
                          val alertDialogBox = dialogBuilder.show()
//                          dialogBox.findViewById<TextView>(R.id.show_function_id).text = "Your functionID is$functionId"
                          dialogBox.findViewById<Button>(R.id.ok_button_in_show_function_id).setOnClickListener{
                              // need to pass the data of the function that i have to follow
                              startActivity(Intent(applicationContext, DisplayItems::class.java).putExtra("CheckValue", functionId))
                          }
                      }.addOnCanceledListener {
                          Toast.makeText(applicationContext,"Sorry!!", Toast.LENGTH_SHORT).show()
                      }
        }
    }

// Creating a random number as the ID
    fun generateID(): Int{
        return (11111111 until 51029384).random()
    }
}