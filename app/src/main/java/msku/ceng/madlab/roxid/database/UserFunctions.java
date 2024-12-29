package msku.ceng.madlab.roxid.database;

import com.google.firebase.firestore.FirebaseFirestore;

public class UserFunctions {

    //! .set() Overwrite diğer verilere yapar ona SetOptions.merge() yapmak gerekir
    //! ama .update() overwrite yapmaz

    public void changeUsername(String oldUsername, String newUsername){
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("users")
                .document(oldUsername)
                .update(
                        "username",newUsername
                );
    }

    public void changeFirstName(String oldFName, String newFName){
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("users")
                .document(oldFName)
                .update(
                        "firstName",newFName
                );
    }

    public void changeLastName(String oldLName, String newLName){
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("users")
                .document(oldLName)
                .update(
                        "lastName",newLName
                );
    }

    //TODO: Edit profile picture
}
