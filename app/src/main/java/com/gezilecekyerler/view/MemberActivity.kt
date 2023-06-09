package com.gezilecekyerler.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.gezilecekyerler.databinding.ActivityMemberBinding
import com.gezilecekyerler.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class MemberActivity : AppCompatActivity() {
    lateinit var binding:ActivityMemberBinding
    private lateinit var auth:FirebaseAuth
    var databaseReferance: DatabaseReference?=null
    var database:FirebaseDatabase?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMemberBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()

        databaseReferance = database?.reference!!.child("members")


        binding.btnSignUp.setOnClickListener {
            var username = binding.txtUserName.text.toString()
            var useremail = binding.txtUserMail.text.toString()
            var userpassword = binding.txtPassword.text.toString()
            val user = User(username,useremail,userpassword)

            if (TextUtils.isEmpty(username)){
                binding.txtUserName.error = "Lütfen kullanıcı adınızı giriniz!"
            }else if (TextUtils.isEmpty(useremail)){
                binding.txtUserMail.error = "Lütfen mail adresinizi giriniz!"
            }else if (TextUtils.isEmpty(userpassword)){
                binding.txtPassword.error = "Lütfen şifre giriniz!"
            }


            auth.createUserWithEmailAndPassword(useremail,userpassword)
                .addOnCompleteListener(this){task ->

                    if (task.isSuccessful){

                        var currentUser = auth.currentUser

                        var currentUserDb = currentUser?.let { it1 -> databaseReferance?.child(it1.uid)}
                        currentUserDb?.child("username")?.setValue(username)
                        Toast.makeText(this@MemberActivity,"Kayıt oluşturuldu,üyelik başarılı!",Toast.LENGTH_LONG).show()
                        intent = Intent(applicationContext, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                        }
                }.addOnFailureListener { exception ->
                    Toast.makeText(applicationContext,exception.localizedMessage,Toast.LENGTH_LONG).show()
                }
        }




    }
}