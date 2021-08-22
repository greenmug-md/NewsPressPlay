package com.greenmug.newspressplay.signup

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.greenmug.newspressplay.R
import android.widget.EditText
import android.widget.Toast
import android.content.Intent
import android.util.Patterns
import android.view.View
import android.widget.ProgressBar
import com.google.android.material.button.MaterialButton
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.greenmug.newspressplay.activity.HomeActivity
import com.greenmug.newspressplay.utilities.Constants
import com.greenmug.newspressplay.utilities.PreferenceManager


class SignInActivity : AppCompatActivity() {
    private var inputEmail: EditText? = null
    private var inputPassword: EditText? = null
    private var buttonSignIn: MaterialButton? = null
    private var signInProgressBar: ProgressBar? = null
    private var preferenceManager: PreferenceManager? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        preferenceManager = PreferenceManager(applicationContext)
        if (preferenceManager != null && preferenceManager!!.getBoolean(Constants.KEY_IS_SIGNED_IN)) {
            val intent = Intent(applicationContext, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }
        findViewById<View>(R.id.textSignUp).setOnClickListener { v: View? ->
            startActivity(
                Intent(
                    applicationContext,
                    SignUpActivity::class.java
                )
            )
        }
        inputEmail = findViewById(R.id.inputEmail)
        inputPassword = findViewById(R.id.inputPassword)
        buttonSignIn = findViewById(R.id.buttonSignIn)
        signInProgressBar = findViewById(R.id.signInProgressBar)
        buttonSignIn?.setOnClickListener(View.OnClickListener { v: View? ->
            if (getInputsContentText(inputEmail).isEmpty()) {
                Toast.makeText(this@SignInActivity, "Enter email", Toast.LENGTH_SHORT).show()
            } else if (!Patterns.EMAIL_ADDRESS.matcher(getInputsContentText(inputEmail))
                    .matches()
            ) {
                Toast.makeText(this@SignInActivity, "Enter valid email", Toast.LENGTH_SHORT).show()
            } else if (getInputsContentText(inputPassword).isEmpty()) {
                Toast.makeText(this@SignInActivity, "Enter password", Toast.LENGTH_SHORT).show()
            } else {
                signIn()
            }
        })
    }

    private fun signIn() {
        buttonSignIn!!.visibility = View.INVISIBLE
        signInProgressBar!!.visibility = View.VISIBLE
        val database: FirebaseFirestore = FirebaseFirestore.getInstance()
        database.collection(Constants.KEY_COLLECTION_USERS)
            .whereEqualTo(Constants.KEY_EMAIL, getInputsContentText(inputEmail))
            .whereEqualTo(Constants.KEY_PASSWORD, getInputsContentText(inputPassword))
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful() && task.getResult() != null && task?.getResult()?.getDocuments() != null && task!!.getResult()!!.getDocuments()!!.size > 0
                ) {
                    val documentSnapshot: DocumentSnapshot = task?.getResult()!!.getDocuments().get(0)
                    preferenceManager?.putBoolean(Constants.KEY_IS_SIGNED_IN, true)
                    preferenceManager?.putString(Constants.KEY_USER_ID, documentSnapshot.getId())
                    preferenceManager?.putString(
                        Constants.KEY_FIRST_NAME,
                        documentSnapshot.getString(Constants.KEY_FIRST_NAME)
                    )
                    preferenceManager?.putString(
                        Constants.KEY_LAST_NAME,
                        documentSnapshot.getString(Constants.KEY_LAST_NAME)
                    )
                    preferenceManager?.putString(
                        Constants.KEY_EMAIL,
                        documentSnapshot.getString(Constants.KEY_EMAIL)
                    )
                    val intent = Intent(applicationContext, HomeActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    startActivity(intent)
                } else {
                    buttonSignIn!!.visibility = View.VISIBLE
                    signInProgressBar!!.visibility = View.INVISIBLE
                    Toast.makeText(this@SignInActivity, "Unable to sign in", Toast.LENGTH_SHORT)
                        .show()
                }
            }
    }

    private fun getInputsContentText(editText: EditText?): String {
        return editText!!.text.toString().trim { it <= ' ' }
    }
}
