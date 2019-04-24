package adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.searchify.BookObj;
import com.example.searchify.R;
import com.example.searchify.UserObj;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class ReceivedBookListAdapter extends BaseAdapter {
    public List<BookObj> bookObjList;
    String from;
    Context context;
    public String ss;

    public ReceivedBookListAdapter(List<BookObj> bookObjList, String from, Context

            context) {
        this.context = context;
        this.bookObjList = bookObjList;
        this.from = from;

    }

    @Override
    public int getCount() {
        return bookObjList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = null;
        final BookObj book = bookObjList.get(position);
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater)
                    context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.received_book_list_adapter_layout, parent,

                    false);
        } else {
            v = convertView;
        }

        Typeface mTfRegular = Typeface.createFromAsset(v.getContext().getAssets(), "OpenSans-Regular.ttf");

        ImageView bookImageView = v.findViewById(R.id.book_img);
        TextView senderNameText = v.findViewById(R.id.req_sender_name);
        TextView bookNameText = v.findViewById(R.id.book_name);
        TextView authorNameText = v.findViewById(R.id.author_name);
        final Button allowButton = v.findViewById(R.id.allow_req_btn);
        final Button rejectButton = v.findViewById(R.id.reject_req_btn);

        FirebaseAuth mAuth;

        mAuth = FirebaseAuth.getInstance();
        final String user_id = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();



        if (book.getAvailability().equals("no")) {
            allowButton.setClickable(false);
            allowButton.setText("Not Available");
        } else if (book.getAvailability().equals("yes")) {
            allowButton.setText("ALLOW");
            allowButton.setClickable(true);
        }

        allowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                allowButton.setClickable(false);
                allowButton.setText("Allowed");
                rejectButton.setVisibility(v.INVISIBLE);
                //send a request to book owner

                DatabaseReference uid_ref = FirebaseDatabase.getInstance().getReference().child("Users").child("Owners").
                        child("UID").child(user_id).child("username");

                uid_ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        final String user_name = (String) dataSnapshot.getValue();

                        final DatabaseReference allowed_ref = FirebaseDatabase.getInstance().getReference().child("Users").child("Owners").
                                child("UID").child(user_id).child("allowed").child(book.getBook_id());

                        allowed_ref.child("name").setValue(book.getName());
                        allowed_ref.child("category").setValue(book.getCategory());
                        allowed_ref.child("writer").setValue(book.getWriter());
                        allowed_ref.child("availability").setValue(book.getAvailability());
                        allowed_ref.child("bookid").setValue(book.getBook_id());
                        allowed_ref.child("owner").setValue(book.getOwner());


                        assert user_name != null;
                        final DatabaseReference user_name_ref = FirebaseDatabase.getInstance().getReference().child("Users").child("Owners").
                                child("username").child(user_name).child("allowed").child(book.getBook_id());

                        user_name_ref.child("name").setValue(book.getName());
                        user_name_ref.child("category").setValue(book.getCategory());
                        user_name_ref.child("writer").setValue(book.getWriter());
                        user_name_ref.child("availability").setValue(book.getAvailability());
                        user_name_ref.child("bookid").setValue(book.getBook_id());
                        user_name_ref.child("owner").setValue(book.getOwner());


                        final DatabaseReference from_ref = FirebaseDatabase.getInstance().getReference().child("Users").child("Owners").
                                child("username").child(from).child("granted").child(book.getBook_id());

                        from_ref.child("name").setValue(book.getName());
                        from_ref.child("category").setValue(book.getCategory());
                        from_ref.child("writer").setValue(book.getWriter());
                        from_ref.child("availability").setValue(book.getAvailability());
                        from_ref.child("bookid").setValue(book.getBook_id());
                        from_ref.child("owner").setValue(book.getOwner());

                        final DatabaseReference from_uid_ref = FirebaseDatabase.getInstance().getReference().child("Users").child("Owners").
                                child("username").child(from).child("UID");

                        from_uid_ref.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                final String from_uid = (String) dataSnapshot.getValue();

                                assert from_uid != null;
                                final DatabaseReference from_uid_ref = FirebaseDatabase.getInstance().getReference().child("Users").child("Owners").
                                        child("UID").child(from_uid).child("granted").child(book.getBook_id());

                                from_uid_ref.child("name").setValue(book.getName());
                                from_uid_ref.child("category").setValue(book.getCategory());
                                from_uid_ref.child("writer").setValue(book.getWriter());
                                from_uid_ref.child("availability").setValue(book.getAvailability());
                                from_uid_ref.child("bookid").setValue(book.getBook_id());
                                from_uid_ref.child("owner").setValue(book.getOwner());
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });

        rejectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //current user e book read list e thakle unread banate hobe else read
                final DatabaseReference update_req_ref = FirebaseDatabase.getInstance().getReference().child("Users").child("Owners").
                        child("UID").child(user_id).child("receiverequest");

                update_req_ref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {



                        HashMap<String, Object> received_req = (HashMap<String, Object>) dataSnapshot.getValue();

                        for (HashMap.Entry<String, Object> entry : received_req.entrySet()){
                            HashMap<String, Object> singleBook = (HashMap<String, Object>) entry.getValue();

//                            BookObj aBook;
//                            System.out.println("----------------jjjjjjjjjjjj-------------");
//                            System.out.println(book.getBook_id() );
//                            boolean b = (singleBook.get(book.getBook_id()) instanceof BookObj);
//                            System.out.println(b);
//                            System.out.println(singleBook.get(book.getBook_id()));
//                            if(singleBook.get(book.getBook_id()) instanceof BookObj){
//                                aBook = (BookObj) singleBook.get(book.getBook_id());
//                                System.out.println("PPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPP");
//                                System.out.println(entry);
//                                System.out.println("QQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQ");
//                                System.out.println(aBook);
//
//                                if(aBook != null) {
//                                    update_req_ref.child(entry.getKey()).setValue(null);
//                                    //received_req.remove(entry);
//                                }
//                            }

                            //System.out.println("oooooooooooooooooooooooooooooooooooooooooooo");
                            System.out.println(singleBook);
                            HashMap singleBook1 = new HashMap();

                            int i = 0;
//            for (Object ent : singleBook.entrySet()){
//                if(!ent.equals("from")) {
//                    singleBook1 = (HashMap) entry.getValue();
//                }
//            }

//                            System.out.println("SSSSSSSSSSSSSSSSSSiiiiiiiiiiiiiiiiiiiiinnnnnnnnnnnnnnggggggggggggggggggggggggggg");
//                            System.out.println(singleBook);
                            for (HashMap.Entry<String, Object> entry2 : singleBook.entrySet()) {
                                System.out.println("EEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE     " + i++);
                                System.out.println(entry2.getValue() + "        " + entry2.getKey());
                                System.out.println(book.getBook_id());
                                if (!entry2.getKey().equals("from")) {
                                    singleBook1 = (HashMap) entry2.getValue();
                                    if(singleBook1.get("bookid").equals(book.getBook_id())) {
                                        update_req_ref.child(entry.getKey()).setValue(null);
                                        System.out.println("dddddddddddd        " + entry2.getKey() + " " + entry.getKey());
                                    }
                                }
                                else
                                {
                                    from = (String) entry2.getValue();
                                }
                            }

                            System.out.println("SSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSS");
                            System.out.println(singleBook1);



                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

        //Book Image
        bookImageView.setImageResource(R.drawable.library);
        bookImageView.setVisibility(View.VISIBLE);

        //Book name
        bookNameText.setTypeface(mTfRegular);
        bookNameText.setText(book.getName());
        bookNameText.setVisibility(View.VISIBLE);

        //author name
        authorNameText.setTypeface(mTfRegular);
        authorNameText.setText(book.getWriter());
        authorNameText.setVisibility(View.VISIBLE);


        return v;
    }
}