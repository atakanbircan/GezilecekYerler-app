package com.gezilecekyerler.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.gezilecekyerler.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {
    lateinit var binding:ActivityMainBinding
    private lateinit var auth:FirebaseAuth
    var databaseReferance:DatabaseReference?=null
    var database:FirebaseDatabase?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()
        //FirebaseApp.initializeApp(this)





        binding.btnSignIn.setOnClickListener {
            var loginemail = binding.txtLoginEmail.text.toString()
            var loginpassword = binding.txtLoginPassword.text.toString()

            if (TextUtils.isEmpty(loginemail)){
                binding.txtLoginEmail.error = "Lütfen e-mail adresinizi yazınız."
                return@setOnClickListener
            }else if(TextUtils.isEmpty(loginpassword)){
                binding.txtLoginPassword.error = "Lütfen şifrenizi yazınız."
                return@setOnClickListener
            }

            auth.signInWithEmailAndPassword(loginemail,loginpassword)
                .addOnCompleteListener(this){
                    if (it.isSuccessful){
                        startActivity(Intent(applicationContext, CitiesNotesActivity::class.java))
                        finish()
                    }else{
                        Toast.makeText(applicationContext,"Giriş hatalı,lütfen tekrar deneyiniz.",Toast.LENGTH_LONG).show()
                    }
                }
        }

        binding.txtInLoginPageSignUp.setOnClickListener {
            intent = Intent(applicationContext, MemberActivity::class.java)
            startActivity(intent)
            //finish()
        }
        binding.txtnewPassword.setOnClickListener {
            intent = Intent(applicationContext, PasswordResetActivity::class.java)
            startActivity(intent)
        }



    }


}