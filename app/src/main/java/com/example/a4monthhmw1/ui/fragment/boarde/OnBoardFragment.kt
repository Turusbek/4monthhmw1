package com.example.a4monthhmw1.ui.fragment.boarde

import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.example.a4monthhmw1.R
import com.example.a4monthhmw1.base.BaseFragment
import com.example.a4monthhmw1.databinding.FragmentOnBoardBinding
import com.example.a4monthhmw1.ui.App
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class OnBoardFragment : BaseFragment<FragmentOnBoardBinding>(FragmentOnBoardBinding::inflate),
    BoardAdapter.StartListener {
    private lateinit var adapter: BoardAdapter
    private lateinit var auth : FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient
    override fun setupUI() {
        adapter = BoardAdapter(this)
        binding.onBoardPager.adapter = adapter
        initGoogleSignClient()
    }

    private fun initGoogleSignClient(){
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(requireActivity(),gso)
        auth = Firebase.auth
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN ){
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                account.idToken?.let { firebaseAuthWithGoogle(it) }
            }catch (e:ApiException){
                Log.e("-----", e.toString())
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken,null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(requireActivity()) { task->
                if (task.isSuccessful){
                    controller.navigateUp()
                }else {
                    Toast.makeText(requireContext(),task.exception.toString(),Toast.LENGTH_SHORT)
                        .show()
                }
            }
    }
    private fun signIn(){
        val intent = googleSignInClient.signInIntent
        startActivityForResult(intent,RC_SIGN_IN)
    }

    override fun start() {
        App.prefs.saveBoardState()
        signIn()

    }

    companion object{
        private const val RC_SIGN_IN = 9001
    }
}