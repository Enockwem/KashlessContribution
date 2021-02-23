package com.example.kashless

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class DisplayItems : AppCompatActivity(), RecyclerAdapter.OnItemClickedListener{
    private lateinit var recyclerAdapter: RecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display_items)
        // Passed intents
        // RecyclerView for this layout
        val hashMap1 = getFunctionInfo()
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerAdapter = RecyclerAdapter(hashMap1,this)
        val layoutManager = LinearLayoutManager(applicationContext)
        recyclerView.layoutManager = layoutManager
        recyclerView.itemAnimator = DefaultItemAnimator()
         recyclerView.adapter = recyclerAdapter
    }
///////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////
    private fun getFunctionInfo():HashMap<String, List<String>>{
    val functionID = intent.getStringExtra("CheckValue")
        val rootRef = FirebaseDatabase.getInstance().reference
        val uidRef = rootRef.child("function").child(functionID!!).child("items")
        var hashMap = HashMap<String,List<String>>()
        lateinit var strKey: String
        lateinit var strPrice: String
        lateinit var strQty: String
        val valueEventListener = object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    for(i in snapshot.children){
                        strKey = i.key.toString()
                        strQty = i.child("0").value.toString()
                        strPrice  = i.child("1").value.toString()
                        hashMap[strKey] = listOf(strQty, strPrice)
                }
                    //Toast.makeText(this@DisplayItems,"${hashMap.keys.elementAt(0)}",Toast.LENGTH_SHORT).show()
                }
            }
            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(applicationContext, error.message, Toast.LENGTH_SHORT).show()
            }
        }

    uidRef.addValueEventListener(valueEventListener)
    uidRef.addListenerForSingleValueEvent(valueEventListener)
//    return hashMap
    return hashMap
    }
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////
    override fun onItemClicked(position: Int) {
//        val intent1 = intent
        val keyValues: MutableCollection<String> = getFunctionInfo().keys
        val valuesList: MutableCollection<List<String>> = getFunctionInfo().values
//        val quantity = valuesList.elementAt(0)
//        val money =valuesList.elementAt(position).elementAt(1)
        val intent2 = Intent(applicationContext, SelectedItemFPayment::class.java)
        intent2.putExtra("CheckValue",intent.getStringExtra("CheckValue"))
//        intent2.putExtra("Price",money)
//        intent2.putExtra("Quantity",quantity)
//        intent2.putExtra("ItemName",keyValues.elementAt(position))
        startActivity(intent2)
    }
}