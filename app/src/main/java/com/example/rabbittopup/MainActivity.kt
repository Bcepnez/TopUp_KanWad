package com.example.rabbittopup


import android.app.Activity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.database.*
import java.text.SimpleDateFormat

class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var cardID : EditText = findViewById(R.id.cardID)
        var editText : EditText = findViewById(R.id.editText)
        var sendBTN : Button = findViewById(R.id.btnSend)
        var showArea : TextView = findViewById(R.id.textView1)

        val database = FirebaseDatabase.getInstance()


        lateinit var databaseRf: DatabaseReference


        sendBTN.setOnClickListener {
            var ids : String = cardID.text.toString()
            var message : String = (editText.text).toString().toInt().toString()
            val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(java.util.Date())


            showArea.setText("")
            val root = database.getReference(ids)
//            val ref = database.getReference(ids+"/"+timeStamp)
            val cardVal = database.getReference(ids+"/"+timeStamp+"/Value")
            val valid = database.getReference(ids+"/"+timeStamp+"/Redeemable")

            if(message.isNotEmpty()&& ids.isNotEmpty()){
                cardVal.setValue(message)
                valid.setValue(true)
                Toast.makeText(this,"Added to RDB", Toast.LENGTH_SHORT).show()
            }
            cardID.editableText.clear()
            editText.editableText.clear()

            root.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(p0: DataSnapshot) {
                    showArea.setText("")
                    for (i in p0.children) {
                        if (i.child("Redeemable").getValue().toString().equals("true")) {
                            showArea.append("Timestamp : " + i.key.toString() + " ")
                            showArea.append("Amount Data : " + i.child("Value").getValue().toString() + "\n")
                        }
                    }
                }

                override fun onCancelled(p0: DatabaseError) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }
            })
        }
    }

}

//class DataSt {
//    var cardID : String? = null
//    var amount : Double = 0.00
//    var timestamp : Timestamp? = null
//    var redeems : Boolean? = null
//    constructor(){}
//    constructor(id:String, timestamp: Timestamp, redeem : Boolean, amounts : Double){
//        this.cardID = id
//        this.amount = amounts
//        this.redeems = redeem
//        this.timestamp = timestamp
//    }
//
//}
