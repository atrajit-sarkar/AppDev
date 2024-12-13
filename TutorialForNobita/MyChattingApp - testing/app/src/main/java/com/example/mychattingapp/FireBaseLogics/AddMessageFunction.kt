package com.example.mychattingapp.FireBaseLogics

import android.annotation.SuppressLint
import android.util.Log
import com.example.mychattingapp.FireBaseLogics.FireBseSetings.FirestoreHelper
import com.example.mychattingapp.LocaldbLogics.DAO.Entities.Message
import com.example.mychattingapp.LocaldbLogics.DAO.Entities.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


fun addMessageToFirestore(message: Message) {
    val db = FirestoreHelper.instance

    Log.d("Firebase", "addUserToFirestore:Fuckoff ")

    db.collection("messages") // "users" is the Firestore collection name
        .add(message)
        .addOnSuccessListener { documentReference ->

            // After the document is added, update the `id` field with the generated Firestore ID
            val updatedMessage = message.copy(messageId = documentReference.id)
            Log.d("Firebase", "User added successfully!")
            // Update the message with the Firestore generated ID
            documentReference.set(updatedMessage)
                .addOnSuccessListener {
                    Log.d("Firestore", "Message added with ID: ${documentReference.id}")
                }
                .addOnFailureListener { e ->
                    Log.w("Firestore", "Error adding document", e)
                }
        }
        .addOnFailureListener { e ->
            Log.e("Firebase", "Error adding user", e)
        }

}