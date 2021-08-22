package com.greenmug.newspressplay.signup

import android.os.Bundle

import androidx.appcompat.app.AppCompatActivity

import com.greenmug.newspressplay.R

import android.widget.EditText

import android.widget.Toast

import android.content.Intent

import com.google.firebase.firestore.FirebaseFirestore

import android.util.Patterns


import android.view.View

import android.widget.ProgressBar

import com.google.android.material.button.MaterialButton
import com.greenmug.newspressplay.activity.HomeActivity
import com.greenmug.newspressplay.utilities.Constants
import com.greenmug.newspressplay.utilities.PreferenceManager


class SignUpActivity : AppCompatActivity() {
    private var inputFirstName: EditText? = null
    private var inputLastName: EditText? = null
    private var inputEmail: EditText? = null
    private var inputPassword: EditText? = null
    private var inputConfirmPassword: EditText? = null
    private var buttonSignUp: MaterialButton? = null
    private var signUpProgressBar: ProgressBar? = null
    private var preferenceManager: PreferenceManager? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        preferenceManager = PreferenceManager(applicationContext)
        findViewById<View>(R.id.imageBack).setOnClickListener { v: View? -> onBackPressed() }
        findViewById<View>(R.id.textSignIn).setOnClickListener { v: View? -> onBackPressed() }
        inputFirstName = findViewById(R.id.inputFirstName)
        inputLastName = findViewById(R.id.inputLastName)
        inputEmail = findViewById(R.id.inputEmail)
        inputPassword = findViewById(R.id.inputPassword)
        inputConfirmPassword = findViewById(R.id.inputConfirmPassword)
        buttonSignUp = findViewById(R.id.buttonSignUp)
        signUpProgressBar = findViewById(R.id.signUpProgressBar)
        buttonSignUp?.setOnClickListener(View.OnClickListener { v: View? ->
            if (getInputsContentText(inputFirstName).isEmpty()) {
                Toast.makeText(this@SignUpActivity, "Enter first name", Toast.LENGTH_SHORT).show()
            } else if (getInputsContentText(inputLastName).isEmpty()) {
                Toast.makeText(this@SignUpActivity, "Enter last name", Toast.LENGTH_SHORT).show()
            } else if (getInputsContentText(inputEmail).isEmpty()) {
                Toast.makeText(this@SignUpActivity, "Enter email", Toast.LENGTH_SHORT).show()
            } else if (!Patterns.EMAIL_ADDRESS.matcher(getInputsContentText(inputEmail))
                    .matches()
            ) {
                Toast.makeText(this@SignUpActivity, "Enter valid email", Toast.LENGTH_SHORT).show()
            } else if (getInputsContentText(inputPassword).isEmpty()) {
                Toast.makeText(this@SignUpActivity, "Enter password", Toast.LENGTH_SHORT).show()
            } else if (getInputsContentText(inputConfirmPassword).isEmpty()) {
                Toast.makeText(this@SignUpActivity, "Confirm your password", Toast.LENGTH_SHORT)
                    .show()
            } else if (getInputsContentText(inputPassword) != getInputsContentText(
                    inputConfirmPassword
                )
            ) {
                Toast.makeText(
                    this@SignUpActivity,
                    "Password & confirm password must be same",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                signUp()
            }
        })
    }

    private fun signUp() {
        buttonSignUp!!.visibility = View.INVISIBLE
        signUpProgressBar!!.visibility = View.VISIBLE
        val database: FirebaseFirestore = FirebaseFirestore.getInstance()
        val user: HashMap<String, Any> = HashMap()
        user[Constants.KEY_FIRST_NAME] = getInputsContentText(inputFirstName)
        user[Constants.KEY_LAST_NAME] = getInputsContentText(inputLastName)
        user[Constants.KEY_EMAIL] = getInputsContentText(inputEmail)
        user[Constants.KEY_PASSWORD] = getInputsContentText(inputPassword)
        database.collection(Constants.KEY_COLLECTION_USERS)
            .add(user)
            .addOnSuccessListener { documentReference ->
                preferenceManager?.putBoolean(Constants.KEY_IS_SIGNED_IN, true)
                preferenceManager?.putString(Constants.KEY_USER_ID, documentReference.getId())
                preferenceManager?.putString(
                    Constants.KEY_FIRST_NAME,
                    getInputsContentText(inputFirstName)
                )
                preferenceManager?.putString(
                    Constants.KEY_LAST_NAME,
                    getInputsContentText(inputLastName)
                )
                preferenceManager?.putString(Constants.KEY_EMAIL, getInputsContentText(inputEmail))
                val intent = Intent(applicationContext, HomeActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                startActivity(intent)
            }
            .addOnFailureListener { e ->
                signUpProgressBar!!.visibility = View.INVISIBLE
                buttonSignUp!!.visibility = View.VISIBLE
                Toast.makeText(this@SignUpActivity, "Error During SignUp", Toast.LENGTH_SHORT)
                    .show()
            }
    }

    private fun getInputsContentText(editText: EditText?): String {
        return editText!!.text.toString().trim { it <= ' ' }
    }
}