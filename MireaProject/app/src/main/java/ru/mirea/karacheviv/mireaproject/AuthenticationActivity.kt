package ru.mirea.karacheviv.mireaproject

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import ru.mirea.karacheviv.mireaproject.databinding.ActivityAuthenticationBinding


class AuthenticationActivity : AppCompatActivity() {

    private lateinit var binding : ActivityAuthenticationBinding
    private lateinit var mAuth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthenticationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mAuth = FirebaseAuth.getInstance()

        binding.createAccountButton.setOnClickListener {
            createAccount(binding.emailField.text.toString(), binding.passwordField.text.toString())
        }
        binding.signInButton.setOnClickListener {
            signIn(binding.emailField.text.toString(), binding.passwordField.text.toString())
        }


    }

    override fun onStart() {
        super.onStart()
//        val currentUser = mAuth.currentUser
//        updateUI(currentUser)

    }
    private fun showAppHome() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    private fun updateUI(user: FirebaseUser?) {
        if (user != null) {
            showAppHome()
        }
    }

    private fun createAccount(email : String, password : String){
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this, OnCompleteListener<AuthResult>(){
                if(it.isSuccessful){
                    val user = mAuth.currentUser
                    updateUI(user);
                }
                else{
                    Toast.makeText(this, "Create Account Fail, Try again", Toast.LENGTH_SHORT).show()
                    updateUI(null)
                }
            })
    }

    private fun signIn(email : String, password : String){
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this, OnCompleteListener<AuthResult>(){
                if(it.isSuccessful){
                    val user = mAuth.currentUser
                    updateUI(user);
                }
                else{
                    Toast.makeText(this, "Authentication Fail", Toast.LENGTH_SHORT).show()
                    updateUI(null)
                }
            })
    }
}