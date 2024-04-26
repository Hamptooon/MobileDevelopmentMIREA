package ru.mirea.karacheviv.securesharedpreferences

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import ru.mirea.karacheviv.securesharedpreferences.databinding.ActivityMainBinding
import java.io.IOException
import java.security.GeneralSecurityException


class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding;
    @SuppressLint("CommitPrefEdits")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater);
        setContentView(binding.root);
        val keyGenParameterSpec = MasterKeys.AES256_GCM_SPEC


        try {
            val mainKeyAlias = MasterKeys.getOrCreate(keyGenParameterSpec)
            val secureSharedPreferences = EncryptedSharedPreferences.create(
                "secret_shared_prefs",
                mainKeyAlias,
                baseContext,
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            )
            secureSharedPreferences.edit().putString("secure", "Джек Лондон").apply()
            binding.authorText.text = secureSharedPreferences.getString("secure",
                "...");
        } catch (e: GeneralSecurityException) {
            throw RuntimeException(e)
        } catch (e: IOException) {
            throw RuntimeException(e)
        }

    }
}