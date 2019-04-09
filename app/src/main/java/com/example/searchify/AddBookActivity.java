package com.example.searchify;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AddBookActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private EditText mBookName, mWriter, mCategory, mAvailability;
    private Button mSubmit;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);
        toolbar = findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }

        mBookName = findViewById(R.id.input_book);
        mWriter = findViewById(R.id.input_writer);
        mCategory = findViewById(R.id.input_category);
        mSubmit = findViewById(R.id.btn_submit);
        mAvailability = findViewById(R.id.input_availability);

        mAuth = FirebaseAuth.getInstance();

        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String book_name = mBookName.getText().toString();
                final String writer_name = mWriter.getText().toString();
                final String category = mCategory.getText().toString();
                final String availability = mAvailability.getText().toString();

                final String user_uid = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();

                if(book_name.isEmpty()) {
                    mBookName.setError("enter a book name");
                    return;
                } else {
                    mBookName.setError(null);
                }

                if(writer_name.isEmpty()) {
                    mWriter.setError("enter a writer name");
                    return;
                } else {
                    mWriter.setError(null);
                }

                if(category.isEmpty()) {
                    mCategory.setError("enter a edition");
                    return;
                } else {
                    mCategory.setError(null);
                }

                if(availability.isEmpty()) {
                    mAvailability.setError("enter yes/no");
                    return;
                } else {
                    mAvailability.setError(null);
                }



                final DatabaseReference book_ref = FirebaseDatabase.getInstance().getReference().child("Users").child("Owners").
                        child("UID").child(user_uid).child("books").child(book_name);

                book_ref.child("name").setValue(book_name);
                book_ref.child("writer").setValue(writer_name);
                book_ref.child("category").setValue(category);
                book_ref.child("availability").setValue(availability);

                final DatabaseReference uid_ref = FirebaseDatabase.getInstance().getReference().child("Users").child("Owners").
                        child("UID").child(user_uid).child("username");

                uid_ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        final String user_name = (String) dataSnapshot.getValue();
                        assert user_name != null;
                        final DatabaseReference user_name_ref = FirebaseDatabase.getInstance().getReference().child("Users").child("Owners").
                                child("username").child(user_name).child("books").child(book_name);

                        user_name_ref.child("name").setValue(book_name);
                        user_name_ref.child("writer").setValue(writer_name);
                        user_name_ref.child("category").setValue(category);
                        user_name_ref.child("availability").setValue(availability);

                        final DatabaseReference publc_book_ref = FirebaseDatabase.getInstance().getReference().child("Books").
                                child(book_name);
                        publc_book_ref.child("name").setValue(book_name);
                        publc_book_ref.child("writer").setValue(writer_name);
                        publc_book_ref.child("category").setValue(category);
                        publc_book_ref.child("availability").setValue(availability);
                        publc_book_ref.child("owner").setValue(user_name);

                        Intent intent = new Intent(AddBookActivity.this, HomeActivity.class);
                        startActivity(intent);
                        finish();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
    }
}