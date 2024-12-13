package com.example.mychattingapp.FireBaseLogics

import android.annotation.SuppressLint
import android.util.Log
import com.example.mychattingapp.FireBaseLogics.FireBseSetings.FirestoreHelper
import com.example.mychattingapp.LocaldbLogics.DAO.Entities.User
import com.google.firebase.firestore.FirebaseFirestore

@SuppressLint("StaticFieldLeak")
val db = FirestoreHelper.instance


fun addUserToFirestore(user: User) {
    Log.d("Firebase", "addUserToFirestore:Fuckoff ")
    db.collection("users") // "users" is the Firestore collection name
        .add(user)
        .addOnSuccessListener {
            Log.d("Firebase", "User added successfully!")
        }
        .addOnFailureListener { e ->
            Log.e("Firebase", "Error adding user", e)
        }
}
