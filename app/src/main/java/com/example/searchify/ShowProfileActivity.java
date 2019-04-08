package com.example.searchify;

import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import adapter.BookListAdapter;
import de.hdodenhof.circleimageview.CircleImageView;

public class ShowProfileActivity extends AppCompatActivity {
    private ArrayList<BookObj> books;


    private ImageView coverImage;
    private CircleImageView profilePic;
    private TextView profileName;
    private Typeface mTfLight, mTfRegular, mTfBold;
    private String userName, fullName;

    //List Adapter Init
    private ListView bookListView;
    private BookListAdapter bookListAdapter;
    private List<String> new_user = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_profile);


        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        userName = bundle.getString("username");
        fullName = bundle.getString("fullname");

        books = new ArrayList<>();
        bookListView = findViewById(R.id.book_list_view);
        for(int i=0;i<3;i++)
        {
            new_user.add("blabla");
        }


        coverImage = findViewById(R.id.header_cover_image);
        profilePic = findViewById(R.id.profile_pic);
        profileName = findViewById(R.id.name);


        mTfRegular = Typeface.createFromAsset(getAssets(), "OpenSans-Regular.ttf");
        mTfLight = Typeface.createFromAsset(getAssets(), "OpenSans-Light.ttf");
        mTfBold = Typeface.createFromAsset(getAssets(), "OpenSans-Bold.ttf");

        profileName.setTypeface(mTfBold);
        profileName.setText(fullName);

        DatabaseReference private_book_ref = FirebaseDatabase.getInstance().getReference().child("Users").child("Owners").
                child("username").child(userName).child("books");

        private_book_ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Map<String, Object> all_private_books = (Map<String, Object>) dataSnapshot.getValue();
                assert all_private_books != null;
                collectBookData(all_private_books, userName);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

//        DatabaseReference public_book_ref = FirebaseDatabase.getInstance().getReference().child("Books");
//
//        public_book_ref.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                Map<String, Object> all_public_books = (Map<String, Object>) dataSnapshot.getValue();
//                assert all_public_books != null;
//                collectBookData(all_public_books);
//
//                //List Adapter
//                bookListAdapter = new BookListAdapter(books, getApplicationContext());
//
//                bookListView.setAdapter(bookListAdapter);
//                //bookListView.setClickable(true);
//
//                bookListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(AdapterView<?> adapterView, View view, int itemNumber, long l) {
//                        Object obj = bookListView.getAdapter().getItem(itemNumber);
//
//
//                    }
//                });
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });





    }

    private void collectBookData(Map<String, Object> users, String owner) {


        //iterate through each user, ignoring their UID
        for (Map.Entry<String, Object> entry : users.entrySet()){
            BookObj aBook = new BookObj();

            //Get user map
            Map singleBook = (Map) entry.getValue();

            //Get phone field and append to list
            aBook.setName((String) singleBook.get("name"));
            aBook.setAvailability((String) singleBook.get("availability"));
            aBook.setCategory((String) singleBook.get("category"));
            aBook.setOwner(owner);
            //aBook.setOwner((String) singleBook.get("owner"));
            aBook.setWriter((String) singleBook.get("writer"));

            //phoneNumbers.add((Long) singleUser.get("phone"));
            System.out.println("book      " + aBook.toString());
            books.add(aBook);
        }
        //System.out.println("qqqqqq   " + new_user.size());

        System.out.println("bookssssssss   " + books.toString());
    }


}
