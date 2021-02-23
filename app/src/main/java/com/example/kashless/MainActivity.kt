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
import com.example.vkash.Model.ItemList
import com.example.vkash.Model.UserSignUpInfo
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class MainActivity : AppCompatActivity() {
    // Accessing the Real time database
    private val database = FirebaseDatabase.getInstance()
    private val myRef = database.reference
    var myFirebaseDatabase: DatabaseReference? = null
    // Creating member variable of FirebaseAuth
    private var mAuth: FirebaseAuth?=null
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mAuth = FirebaseAuth.getInstance()
        //Initialing Hover
        //Hover.initialize(this)
        /////////////////////////////////////////////////////////////////////////////////////////////////////////////
                        /*----------------------- CHECK FUNCTION SECTION --------------------------------- */
                        /**
                         * This is the checkFunction section that is able to check whether a
                         * given FunctionID exists in the system
                         * It also uses a dialog box
                         */
        /////////////////////////////////////////////////////////////////////////////////////////////////////////////
        // Dialog for check function
        val buttonCheckFunction = findViewById<Button>(R.id.check_button)
        buttonCheckFunction.setOnClickListener {
            val checkDialog = LayoutInflater.from(this).inflate(R.layout.check_function_dialog,null)
            val checkBuilder = AlertDialog.Builder(this).setView(checkDialog).setTitle("       Join Function     ")
            // Showing dialog
            val checkAlertDialog = checkBuilder.show()
            checkDialog.findViewById<Button>(R.id.join_function).setOnClickListener {
                val functionId = checkDialog.findViewById<EditText>(R.id.enter_function_id).text.toString()
                val myFunction = myRef.child("function")//.child("functionID")
                val getData = object : ValueEventListener{
                   override fun onDataChange(snapshot: DataSnapshot){
                       for (i in snapshot.children){
                          if(i.key == functionId){
                              val strFunctionID = i.child("functionID").value
                              startActivity(Intent(applicationContext, DisplayItems::class.java).putExtra("CheckValue",strFunctionID.toString()))
                              //Toast.makeText(this@MainActivity, "Yes",Toast.LENGTH_SHORT).show()
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
        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                            /*----------------------- LOGIN SECTION --------------------------------- */
                            /**
                             * This is the Login Section of the MainActivity
                             * It uses a dialog box to login to the application in order to be able to get
                             * access to the rest of the system
                             */
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        // This dialog brings the login
        val buttonForLogin = findViewById<Button>(R.id.login_button)
        buttonForLogin.setOnClickListener {
            val mDialog = LayoutInflater.from(this).inflate(R.layout.login_dialog,null)
            val mBuilder = AlertDialog.Builder(this).setView(mDialog).setTitle("Login Form")
            // showing dialog
            val mAlertDialog = mBuilder.show()
            // Login button click of custom layout
            mDialog.findViewById<Button>(R.id.dialog_button).setOnClickListener {
                // Getting data
                val email = mDialog.findViewById<EditText>(R.id.username_edit).text.toString()
                val password = mDialog.findViewById<EditText>(R.id.password_edit).text.toString()
                if(email.isNotEmpty() && password.isNotEmpty()){
                    var checkTruth: Boolean = false
                    mAuth!!.signInWithEmailAndPassword(email, password).addOnCompleteListener(this){
                        if(it.isSuccessful){
                            val user = mAuth!!.currentUser
                            val uid = user!!.uid
                            val intent = Intent(applicationContext,LoggedWithNoFunction::class.java)
                            intent.putExtra("uid", uid)
                            intent.putExtra("email", email)
                            startActivity(intent)/*.putExtra("CheckValue",user)*/
                            Toast.makeText(applicationContext, "Successful",Toast.LENGTH_SHORT).show()
                        }else{
                            Toast.makeText(applicationContext, it.exception.toString(),Toast.LENGTH_SHORT).show()
                        }
                    }
                }else{
                    Toast.makeText(applicationContext, "Enter all details", Toast.LENGTH_SHORT).show()
                }
            }
        }
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////
                            /*----------------------- SIGN UP SECTION--------------------------------- */
                            /**
                            * This is the sign up section for the MainActivity
                            * It uses a dialog box to get information from the user to the
                            * Database of the system, In this case the firebase
                            * */
        /////////////////////////////////////////////////////////////////////////////////////////////////////////////
        // Sign up to create account
        val textSignUp = findViewById<TextView>(R.id.sign_up_text)
        textSignUp.setOnClickListener{
            val signDialog = LayoutInflater.from(this).inflate(R.layout.sign_up,null)
            val signBuilder = AlertDialog.Builder(this).setView(signDialog).setTitle("Sign up")
            // showing dialog
            val signAlertDialog = signBuilder.show()
            // signUp button in the dialog box
            signDialog.findViewById<Button>(R.id.create_account).setOnClickListener {
                val fName = signDialog.findViewById<EditText>(R.id.first_name).text.toString()
                val lName = signDialog.findViewById<EditText>(R.id.last_name).text.toString()
                val email = signDialog.findViewById<EditText>(R.id.enter_email).text.toString()
                val uName = signDialog.findViewById<EditText>(R.id.user_name_for_display).text.toString()
                val pNumber = signDialog.findViewById<EditText>(R.id.phone_number).text.toString()
                val pwd = signDialog.findViewById<EditText>(R.id.passcode).text.toString()
                val cPWD = signDialog.findViewById<EditText>(R.id.confirm_passcode).text.toString()

                if (fName.isNotEmpty() && lName.isNotEmpty() && pNumber.isNotEmpty() && email.isNotEmpty() && uName.isNotEmpty()
                        && pwd.isNotEmpty() && cPWD.isNotEmpty()){
                            if(pwd == cPWD){
                                myFirebaseDatabase = database.getReference("users")
                                mAuth!!.createUserWithEmailAndPassword(email,pwd).addOnCompleteListener(this){ task ->
                                    if (task.isSuccessful){
                                        mAuth!!.currentUser!!.sendEmailVerification()
                                                .addOnCompleteListener(this){
                                                    if(it.isSuccessful){
                                                        // Creating and sending data to an object
                                                        Toast.makeText(applicationContext,
                                                            "Verification email sent to $email", Toast.LENGTH_SHORT).show()
                                                        // getting current user from auth
                                                        val user = FirebaseAuth.getInstance().currentUser
                                                        // username and email
                                                        val userId = user!!.uid
                                                        val emailAddress = user.email
                                                        val newUser = UserSignUpInfo(fName,lName,pNumber,emailAddress,pwd,uName,null)
                                                        // Getting reference to the user's node in the database
                                                        myRef.child("users").child(userId).setValue(newUser).addOnSuccessListener {
                                                            signAlertDialog.dismiss()
                                                        }
                                                    }else{
                                                        Toast.makeText(applicationContext,"Verification email not sent but signup was successful",Toast.LENGTH_SHORT).show()
                                                    }
                                                }
                                    }else{
                                        Toast.makeText(applicationContext,"Not Successful", Toast.LENGTH_SHORT).show()
                                    }
                                }
                            }else{
                                Toast.makeText(applicationContext, "Passwords don't match",Toast.LENGTH_SHORT).show()
                            }
                }else{
                    Toast.makeText(applicationContext,"One of the information is not provided", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    fun getMoreInformation(){
        val uid = FirebaseAuth.getInstance().currentUser!!.uid
        val rootRef = FirebaseDatabase.getInstance().reference
        val uidRef = rootRef.child("users").child(uid)
        val valueEventListener = object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var user = snapshot.getValue(ItemList::class.java)
            }
            override fun onCancelled(error: DatabaseError) {
            }
        }
    }

//    private fun update(user: FirebaseUser){
//        startActivity(Intent(this,LoginedWithNoFunction::class::java))
//    }

}