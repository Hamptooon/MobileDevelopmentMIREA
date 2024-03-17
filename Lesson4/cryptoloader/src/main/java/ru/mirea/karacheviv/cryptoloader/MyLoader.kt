package ru.mirea.karacheviv.cryptoloader

import android.content.Context
import android.os.Bundle
import androidx.loader.content.AsyncTaskLoader
import java.security.InvalidKeyException
import java.security.NoSuchAlgorithmException
import javax.crypto.BadPaddingException
import javax.crypto.Cipher
import javax.crypto.IllegalBlockSizeException
import javax.crypto.NoSuchPaddingException
import javax.crypto.SecretKey
import javax.crypto.spec.SecretKeySpec


class MyLoader(context: Context, private val args : Bundle) : AsyncTaskLoader<String>(context) {
//    private var firstName: String? = null
    companion object {
        const val ARG_WORD = "word"
    }

//    init {
//        if(args != null){
//            firstName = args.getString(ARG_WORD);
//        }
//    }

    override fun onStartLoading() {
        super.onStartLoading()
        forceLoad()
    }

    override fun loadInBackground(): String? {
        //	emulate	long-running	operation
//        SystemClock.sleep(5000)
        val cryptText: ByteArray = args.getByteArray(ARG_WORD)!!
        val key: ByteArray = args.getByteArray("key")!!
        val originalKey: SecretKey = SecretKeySpec(key, 0, key.size, "AES")
        return decryptText(cryptText, originalKey)
    }

    private fun decryptText(cryptText : ByteArray, secretKey : SecretKey) : String{

        return try {
            val cipher = Cipher.getInstance("AES")
            cipher.init(Cipher.DECRYPT_MODE, secretKey)
            String(cipher.doFinal(cryptText))
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


}