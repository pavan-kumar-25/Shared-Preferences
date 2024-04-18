package com.example.lab9shared

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout

class MainActivity : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

        val saveButton = findViewById<Button>(R.id.saveButton)
        val clearButton = findViewById<Button>(R.id.clearButton)
        val retrieveButton = findViewById<Button>(R.id.retrieveButton)

        val cityAutoCompleteTextView = findViewById<AutoCompleteTextView>(R.id.cityAutoCompleteTextView)
        val cityLayout = findViewById<TextInputLayout>(R.id.cityLayout)

        // List of pre-listed city names
        val cities = listOf("New York", "Los Angeles", "Chicago", "India")

        // Create an ArrayAdapter
        val adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, cities)

        // Set the adapter to the AutoCompleteTextView
        cityAutoCompleteTextView.setAdapter(adapter)

        // Set a click listener to clear any error when the AutoCompleteTextView is clicked
        cityAutoCompleteTextView.setOnClickListener {
            cityLayout.error = null
        }

        saveButton.setOnClickListener {
            saveData()
            showToast("Data saved")
        }

        clearButton.setOnClickListener {
            clearFields()
            showToast("Fields cleared")
        }

        retrieveButton.setOnClickListener {
            retrieveData()
            showToast("Data retrieved")
        }
    }

    private fun saveData() {
        val firstName = findViewById<EditText>(R.id.firstNameEditText).text.toString()
        val lastName = findViewById<EditText>(R.id.lastNameEditText).text.toString()
        val email = findViewById<EditText>(R.id.emailEditText).text.toString()
        val age = findViewById<EditText>(R.id.ageEditText).text.toString()
        val college = findViewById<EditText>(R.id.collegeEditText).text.toString()
        val city = findViewById<AutoCompleteTextView>(R.id.cityAutoCompleteTextView).text.toString()
        val gender = when (findViewById<RadioButton>(findViewById<RadioGroup>(R.id.genderRadioGroup).checkedRadioButtonId).id) {
            R.id.maleRadioButton -> "Male"
            R.id.femaleRadioButton -> "Female"
            else -> "Other"
        }

        if (firstName.isNotEmpty() && lastName.isNotEmpty() && email.isNotEmpty() && age.isNotEmpty() && college.isNotEmpty() && city.isNotEmpty()) {
            val editor = sharedPreferences.edit()
            editor.putString("firstName", firstName)
            editor.putString("lastName", lastName)
            editor.putString("email", email)
            editor.putString("age", age)
            editor.putString("college", college)
            editor.putString("city", city)
            editor.putString("gender", gender)
            editor.apply()
        } else {
            showSnackbar("Fields can't be empty")
        }
    }

    private fun clearFields() {
        findViewById<EditText>(R.id.firstNameEditText).setText("")
        findViewById<EditText>(R.id.lastNameEditText).setText("")
        findViewById<EditText>(R.id.emailEditText).setText("")
        findViewById<EditText>(R.id.ageEditText).setText("")
        findViewById<EditText>(R.id.collegeEditText).setText("")
        findViewById<AutoCompleteTextView>(R.id.cityAutoCompleteTextView).setText("")
        findViewById<RadioGroup>(R.id.genderRadioGroup).clearCheck()
    }

    private fun retrieveData() {
        val firstName = sharedPreferences.getString("firstName", "")
        val lastName = sharedPreferences.getString("lastName", "")
        val email = sharedPreferences.getString("email", "")
        val age = sharedPreferences.getString("age", "")
        val college = sharedPreferences.getString("college", "")
        val city = sharedPreferences.getString("city", "")
        val gender = sharedPreferences.getString("gender", "")

        findViewById<EditText>(R.id.firstNameEditText).setText(firstName)
        findViewById<EditText>(R.id.lastNameEditText).setText(lastName)
        findViewById<EditText>(R.id.emailEditText).setText(email)
        findViewById<EditText>(R.id.ageEditText).setText(age)
        findViewById<EditText>(R.id.collegeEditText).setText(college)
        findViewById<AutoCompleteTextView>(R.id.cityAutoCompleteTextView).setText(city)

        val genderRadioGroup = findViewById<RadioGroup>(R.id.genderRadioGroup)
        val selectedRadioButton = when (gender) {
            "Male" -> R.id.maleRadioButton
            "Female" -> R.id.femaleRadioButton
            else -> R.id.otherRadioButton
        }
        genderRadioGroup.check(selectedRadioButton)
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun showSnackbar(message: String) {
        val snackbar = Snackbar.make(findViewById(R.id.main), message, Snackbar.LENGTH_SHORT)
        snackbar.show()
    }
}

