package ru.mirea.karacheviv.cryptoloader

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.loader.app.LoaderManager
import androidx.loader.content.Loader
import com.google.android.material.snackbar.Snackbar
import ru.mirea.karacheviv.cryptoloader.databinding.ActivityMainBinding
import java.security.InvalidKeyException
import java.security.InvalidParameterException
import java.security.NoSuchAlgorithmException
import java.security.SecureRandom
import javax.crypto.BadPaddingException
import javax.crypto.Cipher
import javax.crypto.IllegalBlockSizeException
import javax.crypto.KeyGenerator
import javax.crypto.NoSuchPaddingException
import javax.crypto.SecretKey
import javax.crypto.spec.SecretKeySpec


class MainActivity : AppCompatActivity(), LoaderManager.LoaderCallbacks<String> {
    private var TAG = this.javaClass.simpleName
    private var LoaderID = 1234
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
    fun onClickButton(view : View){
        val bundle = Bundle()
        bundle.putString(MyLoader.ARG_WORD, "mirea")
        LoaderManager.getInstance(this).initLoader(LoaderID, bundle, this)
    }
    override fun onCreateLoader(id: Int, bundle: Bundle?): Loader<String> {
        if(id == LoaderID){
            Toast.makeText(this, "onCreateLoader:$id", Toast.LENGTH_SHORT).show()
            return MyLoader(this, bundle!!)
        }
        throw InvalidParameterException("Invalid loader id")
    }

    override fun onLoaderReset(loader: Loader<String>) {
        Log.d(TAG, "onLoaderReset")
    }

    override fun onLoadFinished(loader: Loader<String>, data: String?) {
        if(loader.id == LoaderID){
            Log.d(TAG, "onLoadFinished")
            Snackbar.make(binding.root, data!!, Snackbar.LENGTH_SHORT).show()

        }
    }
    private fun generateKey(): SecretKey? {
        return try {
            val sr = SecureRandom.getInstance("SHA1PRNG")
            sr.setSeed("any	data	used	as	random	seed".toByteArray())
            val kg = KeyGenerator.getInstance("AES")
            kg.init(256, sr)
            SecretKeySpec(kg.generateKey().encoded, "AES")
        } catch (e: NoSuchAlgorithmException) {
            throw RuntimeException(e)
        }
    }
    private fun encryptMsg(message: String, secret: SecretKey?): ByteArray? {
        return try {
            val cipher = Cipher.getInstance("AES")
            cipher.init(Cipher.ENCRYPT_MODE, secret)
            cipher.doFinal(message.toByteArray())
        } catch (e: NoSuchAlgorithmException) {
            throw java.lang.RuntimeException(e)
        } catch (e: NoSuchPaddingException) {
            throw java.lang.RuntimeException(e)
        } catch (e: InvalidKeyException) {
            throw java.lang.RuntimeException(e)
        } catch (e: BadPaddingException) {
            throw java.lang.RuntimeException(e)
        } catch (e: IllegalBlockSizeException) {
            throw java.lang.RuntimeException(e)
        }
    }

    fun onClickEncryptBtn(view: View){
        val textToEncrypt = binding.editText1.text.toString()
        val key = generateKey()
        val cryptText = encryptMsg(textToEncrypt, key)
        val bundle = Bundle()
        bundle.putByteArray(MyLoader.ARG_WORD, cryptText)
        bundle.putByteArray("key", key!!.encoded)
        LoaderManager.getInstance(this).initLoader(LoaderID, bundle, this)

    }

}