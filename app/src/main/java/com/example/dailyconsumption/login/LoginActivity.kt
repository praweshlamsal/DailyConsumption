package com.example.dailyconsumption.login

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.cardview.widget.CardView
import com.example.dailyconsumption.R
import com.example.dailyconsumption.base.BaseActivity
import com.example.dailyconsumption.userlanding.UserLandingActivity
import com.example.dailyconsumption.utils.network.ConnectionCheck
import com.example.dailyconsumption.utils.okdialog.okDialog
import com.example.dailyconsumption.utils.progressdialog.CustomProgressDialog
import com.example.dailyconsumption.utils.sharedprefs.SharedKeys
import com.example.dailyconsumption.utils.sharedprefs.SharedPrefsHelper
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.*

class LoginActivity : BaseActivity<LoginPresenter>(), LoginContractor {

    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private lateinit var gmailSignin: CardView
    private val RC_SIGN_IN = 1
    private lateinit var connectionCheck: ConnectionCheck
    lateinit var customProgressDialog: CustomProgressDialog
    val sharedPrefsHelper:SharedPrefsHelper = SharedPrefsHelper()
    private lateinit var authStateListener : FirebaseAuth.AuthStateListener



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        supportActionBar?.hide()
        initiatefirebase()
        forgoogle()
        gmailSignin = findViewById<CardView>(R.id.sign_in_btn)
//        signout = findViewById<Button>(R.id.sign_out)
        presenter.activity = this.getActivityContext()
        connectionCheck = ConnectionCheck()
        customProgressDialog = CustomProgressDialog(getActivityContext())
        setupFirebaseAuth()
        gmailSignin.setOnClickListener(View.OnClickListener {
            signin()
        })

//        signout.setOnClickListener(View.OnClickListener {
//            signout()
//        })


    }

    private fun setupFirebaseAuth() {
        authStateListener = FirebaseAuth.AuthStateListener {
            firebaseAuth ->
            val user = firebaseAuth.currentUser

            if(user != null){
                Log.d("username",user.displayName)
            }
            else{
                Log.d("notloggedin","not loggedin")
            }
        }

    }

    private fun initiatefirebase() {
        presenter.initializeFCMToken()
    }

    private fun forgoogle(){
        var gso: GoogleSignInOptions =
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
    }

    private fun signin() {
        if(connectionCheck.isNetworkAvailable(this)){
            customProgressDialog.showDialog("Loggin in","Please Wait okayman")
            var signInIntent: Intent = mGoogleSignInClient.signInIntent
            startActivityForResult(signInIntent, RC_SIGN_IN)
        }
        else{
            okDialog().okDialog("No Internet Connection",getActivityContext())
            customProgressDialog.dismissDialog()
        }


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        customProgressDialog.dismissDialog()
        if (resultCode == Activity.RESULT_OK) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
        else{
            Log.d("oaksdasd",resultCode.toString())
        }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
//            customProgressDialog.showDialog("Loggin in","Please Wait")
            var acc = completedTask.getResult(ApiException::class.java)!!
            presenter.googleResult(acc)
            Toast.makeText(this.getActivityContext(), "Google Sign In Successful", Toast.LENGTH_SHORT)
                .show()
//            signout.visibility = View.VISIBLE
        } catch (e: ApiException) {
            customProgressDialog.dismissDialog()
            ShowError(e.toString())
            Toast.makeText(this.getActivityContext(), "Google Sign In Failed", Toast.LENGTH_SHORT).show()
        }
    }


    override fun onResume() {
        super.onResume()

    }

    override fun onStart() {
        super.onStart()
    }

//    private fun signout() {
////        signout.visibility = View.GONE
//    }

    override fun getActivityContext(): Activity {
        return this
    }

    override fun instantiatePresenter(): LoginPresenter {
        return LoginPresenter(this)
    }


    override fun loginSuccess() {
        sharedPrefsHelper.setSharedPreferences(getActivityContext(), SharedKeys.IsLoggedIn, "y")
        sharedPrefsHelper.setSharedPreferences(
            getActivityContext(),
            SharedKeys.CurrentUserRole,
            "normal"
        )

        customProgressDialog.dismissDialog()

    }

    override fun ShowError(error: String) {
        customProgressDialog.dismissDialog()
        okDialog().okDialog(error,getActivityContext())
    }

    override fun showLoading() {
    }

    override fun hideLoading() {
        customProgressDialog.dismissDialog()
    }

    override fun loginUser() {
        customProgressDialog.dismissDialog()
        var intent = Intent(this,UserLandingActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun loginAdmin() {
        Log.d("loginAdmin","loginAdmin Login")
    }



}
