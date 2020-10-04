package com.example.dailyconsumption.login

import android.app.Activity
import android.util.Log
import com.example.dailyconsumption.base.BasePresenter
import com.example.dailyconsumption.utils.sharedprefs.SharedKeys
import com.example.dailyconsumption.utils.sharedprefs.SharedPrefsHelper
import com.example.dailyconsumption.utils.sharedprefs.SharedPrefsObject
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.iid.InstanceIdResult
import java.util.ArrayList
import java.util.HashMap

class LoginPresenter(loginContractor: LoginContractor) :
    BasePresenter<LoginContractor>(loginContractor) {

    private var fcmToken: String = ""
    private lateinit var db: FirebaseFirestore
    val sharedPrefsHelper:SharedPrefsHelper = SharedPrefsHelper()
    lateinit var activity:Activity



    fun initializeFCMToken() {
        FirebaseInstanceId.getInstance().instanceId.addOnCompleteListener { task ->
            fcmToken = task.result!!.token
        }
    }

    fun googleResult(account: GoogleSignInAccount) {
        var email = account.email!!
        var firstName = account.givenName!!
        var lastName = account.familyName!!
        var photo = account.photoUrl!!.toString()
        var personName = account.displayName!!
        val sharedPrefsObjects = ArrayList<SharedPrefsObject>()
        sharedPrefsObjects.add(SharedPrefsObject(SharedKeys.UserEmail, email))
        sharedPrefsObjects.add(SharedPrefsObject(SharedKeys.FullName, firstName + " " + lastName))
        sharedPrefsObjects.add(SharedPrefsObject(SharedKeys.UserPhoto, photo))
        sharedPrefsObjects.add(SharedPrefsObject(SharedKeys.CurrentLoginType, "google"))
        Log.d("newdata",firstName.toString())
        requestUserDetails(sharedPrefsObjects)

    }

    private fun requestUserDetails(mlist: ArrayList<SharedPrefsObject>) {
        db = FirebaseFirestore.getInstance()
        db.collection("users")
            .whereEqualTo("email",mlist.get(0).value)
            .get()
            .addOnCompleteListener{
                task ->
                if(task.isSuccessful){
                    var fullname =  ""
                    var email = ""
                    var role =""
                    var documentId = ""
                    var userid:Long = 0;
                    for (document in task.result!!) {
                        documentId = document.id
                        fullname = document.get("fullname").toString()
                        email = document.get("email").toString()
                        role = document.get("role").toString()
                        userid = document.get("userid") as Long
                    }
                    if(userid == 0L) run {saveNewLogin(mlist)}
                    else{

                        val slist = ArrayList<SharedPrefsObject>()
                        slist.add(SharedPrefsObject(SharedKeys.UserEmail,email))
                        slist.add(SharedPrefsObject(SharedKeys.UserId,userid.toString()))
                        slist.add(SharedPrefsObject(SharedKeys.MyDocumentID,documentId))
                        slist.add(SharedPrefsObject(SharedKeys.FullName,fullname))
                        sharedPrefsHelper.setSharedPreferences(activity, slist)
                        updateFCMToken(role,documentId)
                    }
                }
                else{
                    view.ShowError("Login failed please try again")
                }
            }
    }

    private fun updateFCMToken(role: String, documentId: String) {
        val update_fcm_token = HashMap<String, Any>()
        update_fcm_token.put("fcmtoken",fcmToken)
        db = FirebaseFirestore.getInstance()
        db.collection("users")
            .document(documentId)
            .update(update_fcm_token)
            .addOnCompleteListener{
                task ->
                sharedPrefsHelper.setSharedPreferences(activity,SharedKeys.MyFCMTOken,fcmToken)
                if(role.equals("admin")){
                    view.loginAdmin()
                }
                else{
                    view.loginUser()
                }
            }
            .addOnFailureListener{
                e -> view.ShowError("Login failed please try again")
            }
    }

    private fun saveNewLogin(mlist: ArrayList<SharedPrefsObject>) {
        var db = FirebaseFirestore.getInstance()
        val new_userid = System.currentTimeMillis()
        mlist.add(SharedPrefsObject(SharedKeys.UserId,new_userid.toString()))
        mlist.add(SharedPrefsObject(SharedKeys.MyFCMTOken,fcmToken))
        mlist.add(SharedPrefsObject(SharedKeys.UserEmail,"0"))
        mlist.add(SharedPrefsObject(SharedKeys.FullName,"0"))
        Log.d("Size",mlist.size.toString())
        sharedPrefsHelper.setSharedPreferences(this.activity,mlist)
        if(fcmToken.length == 0) run {initializeFCMToken()}

        var new_login_details = HashMap<String,Any>()
        new_login_details.put("userid",new_userid)
        new_login_details.put("fcmtoken",fcmToken)
        new_login_details.put("email",mlist.get(0).value)
        new_login_details.put("photo",mlist.get(2).value)
        new_login_details.put("role","normal")
        new_login_details.put("fullname",mlist.get(1).value)
        db.collection("users")
            .add(new_login_details)
            .addOnSuccessListener { saveNewUserDetail(new_userid) }
            .addOnFailureListener { view.ShowError("Login failed, Please try again.") }

    }

    private fun saveNewUserDetail(newUserid: Long) {
        var new_user_detail_id = System.currentTimeMillis()
        var new_login_details = HashMap<String,Any>()
        new_login_details.put("userid",newUserid)
        new_login_details.put("userdetailid",new_user_detail_id)
        Log.d("newdata",newUserid.toString())
        db.collection("userdetail")
            .add(new_login_details)
            .addOnCompleteListener { view.loginUser() }
            .addOnFailureListener{ view.ShowError("Login failed please try again.")}
    }

}