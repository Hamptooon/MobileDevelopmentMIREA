package ru.mirea.karacheviv.firebaseauth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import ru.mirea.karacheviv.firebaseauth.databinding.ActivityMainBinding
import java.util.Objects

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private var TAG = "MainActivity"

    private lateinit var mAuth : FirebaseAuth;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mAuth = FirebaseAuth.getInstance()

        binding.buttonCreateAccount.setOnClickListener {
            val email = binding.emailField.text.toString()
            val password = binding.passwordField.text.toString()

            if(email.isEmpty() ||  password.isEmpty()){
                Toast.makeText(this, "Fill all fields!", Toast.LENGTH_SHORT).show()
            }
            else{
                createAccount(email,password)
            }
        }

        binding.buttonSignIn.setOnClickListener {
            val email = binding.emailField.text.toString()
            val password = binding.passwordField.text.toString()

            if(email.isEmpty() ||  password.isEmpty()){
                Toast.makeText(this, "Fill all fields!", Toast.LENGTH_SHORT).show()
            }
            else{
                signIn(email,password)
            }
        }

        binding.buttonSignOut.setOnClickListener {
            signOut()
        }

        binding.buttonVerifyEmail.setOnClickListener {
            sendEmailVerification()
        }



    }

    override fun onStart() {
        super.onStart()

        val currentUser = mAuth.currentUser
        updateUI(currentUser)
    }

    private fun updateUI(user : FirebaseUser?){
        if(user != null){
            binding.statusTextView.text = getString(R.string.emailpassword_status_fmt, user.email, user.isEmailVerified)
            binding.detailTextView.text = getString(R.string.firebase_status_fmt, user.uid)
            binding.authLayout.visibility = View.GONE
            binding.signedLayout.visibility = View.VISIBLE
            binding.buttonVerifyEmail.isEnabled = !user.isEmailVerified
        }
        else{
            binding.statusTextView.text = getString(R.string.sign_out)
            binding.detailTextView.text = null
            binding.authLayout.visibility = View.VISIBLE
            binding.signedLayout.visibility = View.GONE
        }
    }


    private fun createAccount(email  : String, password : String){
        Log.d(TAG, "createAccount " + email)
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this, OnCompleteListener<AuthResult>(){
                if(it.isSuccessful){
                    Log.d(TAG, "createUserAccount:success")
                    val user = mAuth.currentUser
                    updateUI(user)
                }
                else{
                    Log.d(TAG, "createUserAccount:fail", it.exception)
                    Toast.makeText(this, "Authentication:Fail", Toast.LENGTH_SHORT).show()
                    updateUI(null)

                }
            })

    }

    private fun signIn(email: String, password: String){
        Log.d(TAG, "signIn "+ email)

        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this, OnCompleteListener<AuthResult>(){
                if(it.isSuccessful){
                    Log.d(TAG, "signIn:success")

                    val user =  mAuth.currentUser
                    updateUI(user)
                }
                else{
                    Log.d(TAG, "signIn:fail", it.exception)
                    Toast.makeText(this, "Authentication failed.", Toast.LENGTH_SHORT).show()
                    updateUI(null)
                }
                if(!it.isSuccessful){
                    binding.statusTextView.text = getString(R.string.auth_failed)
                }
            })
    }

    private fun signOut(){
        mAuth.signOut()
        updateUI(null)
    }

    private fun sendEmailVerification(){
        binding.buttonVerifyEmail.isEnabled = false

        var user = mAuth.currentUser

        Objects.requireNonNull(user)?.sendEmailVerification()
            ?.addOnCompleteListener(this, OnCompleteListener<Void>(){
                binding.buttonVerifyEmail.isEnabled = true

                if(it.isSuccessful){
                    Toast.makeText(this, "Verification email sent to" + user?.email, Toast.LENGTH_SHORT).show()

                }
                else{
                    Log.d(TAG, "sendEmailVerification", it.exception)
                    Toast.makeText(this, "Fail verification email sent to" + user?.email, Toast.LENGTH_SHORT).show()
                }

            })
    }
}