package com.application.roxid.database;

import com.google.firebase.firestore.FirebaseFirestore;

public class UserFunctions {

    //! .set() Overwrite diğer verilere yapar ona SetOptions.merge() yapmak gerekir
    //! ama .update() overwrite yapmaz

    public static void changeUsername(String currentUserID, String newUsername) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("users")
                .document(currentUserID)
                .update(
                        "username", newUsername
                );
    }

    public static void changeLastName(String currentUserID, String newLName) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("users")
                .document(currentUserID)
                .update(
                        "lastName", newLName
                );
    }

    public static void changeFirstName(String currentUserID, String newFName) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("users")
                .document(currentUserID)
                .update(
                        "firstName", newFName
                );
    }

    // TODO: Edit profile picture
    // TODO: Firebase Storage ücretli diyor.


}
