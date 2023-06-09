package com.gezilecekyerler.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import com.gezilecekyerler.R
import com.gezilecekyerler.adapter.MyAdapter
import com.gezilecekyerler.databinding.ActivityCitiesnotesBinding
import com.gezilecekyerler.model.City
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class CitiesNotesActivity : AppCompatActivity() {

    lateinit var binding: ActivityCitiesnotesBinding
    private lateinit var auth: FirebaseAuth
    var databaseReferance: DatabaseReference? = null


    lateinit var btnSave: Button
    lateinit var txtTitle: EditText
    lateinit var txtDetail: EditText
    lateinit var listView: ListView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCitiesnotesBinding.inflate(layoutInflater)
        var arr = mutableListOf<City>()
        val currentUser = FirebaseAuth.getInstance().currentUser
        setContentView(binding.root)
        currentUser?.let {
            val database = FirebaseDatabase.getInstance()
            val rootNode = database.reference.child("members/${it.uid}")
            val citiesNode = rootNode.child("cities")
            val cityId = binding.editTxtTitle.text.toString()
            val cityNode = citiesNode.child(cityId)


            databaseReferance =FirebaseDatabase.getInstance().reference
            btnSave = binding.btnSave
            txtTitle = binding.editTxtTitle
            txtDetail = binding.editTxtDetail
            listView = binding.listView

            auth = FirebaseAuth.getInstance()


            btnSave.setOnClickListener {


                if (binding.editTxtTitle.text.toString().isNotEmpty() && binding.editTxtDetail.text.toString().isNotEmpty()) {

                    cityNode.child(binding.editTxtTitle.text.toString()).setValue(binding.editTxtDetail.text.toString())
                }else{
                    Toast.makeText(this@CitiesNotesActivity,"Lütfen isim ve detay kısmını doldrunuz!",Toast.LENGTH_LONG).show()
                }

            }


            var getData = object : ValueEventListener{

                override fun onDataChange(snapshot: DataSnapshot) {
                    var sb = StringBuilder()
                    for (memberSnapshot in snapshot.children) {
                        val citiesSnapshot = memberSnapshot.child("cities")

                        for (citySnapshot in citiesSnapshot.children) {
                            val cityName = citySnapshot.key
                            val cityDescription = citySnapshot.value


                            var city = City(sb.append(cityName).toString(),sb.append(cityDescription).toString())
                            arr.add(city)

                            println("City: $cityName, Description: $cityDescription")
                        }
                    }
                    var adapter1 = MyAdapter(this@CitiesNotesActivity, arr)
                    adapter1.notifyDataSetChanged()
                    listView.adapter= adapter1

                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            }


            rootNode.addValueEventListener(getData)
        }


        }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.activity_cities_notes, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_logout -> {
                FirebaseAuth.getInstance().signOut()

                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                Toast.makeText(this@CitiesNotesActivity, "Logout successful", Toast.LENGTH_SHORT).show()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}