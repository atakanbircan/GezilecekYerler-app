package com.gezilecekyerler.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import com.gezilecekyerler.databinding.ActivityPasswordResetBinding
import com.google.firebase.auth.FirebaseAuth

class PasswordResetActivity : AppCompatActivity() {
    lateinit var binding: ActivityPasswordResetBinding
    private lateinit var auth:FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPasswordResetBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth=FirebaseAuth.getInstance()

        binding.btnPasswordReset.setOnClickListener {
            var resetPasswordEmail = binding.editTextTextEmailAddress.text.toString().trim()
            if (TextUtils.isEmpty(resetPasswordEmail)){
                binding.editTextTextEmailAddress.error= "Email adresini boş bırakmayınız!"
            }else{
                auth.sendPasswordResetEmail(resetPasswordEmail)
                    .addOnCompleteListener(this@PasswordResetActivity){ newPassword ->
                        if (newPassword.isSuccessful){
                            binding.txtPasswordResetInfo.text = "Email adresine sıfırlama bağlantısı gönderildi!"
                            binding.txtPasswordResetInfo.visibility=View.VISIBLE
                        }else{
                            binding.txtPasswordResetInfo.text = "Şifre Yenileme işlemi başarısız!"
                            binding.txtPasswordResetInfo.visibility=View.VISIBLE
                        }
                    }
            }
        }

        binding.btnforSignInPage.setOnClickListener {
          intent = Intent(applicationContext, MainActivity::class.java)
          startActivity(intent)
          finish()
        }
    }
}