package com.manager.appbanhang.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.manager.appbanhang.R;
import com.manager.appbanhang.adapter.UserAdapter;
import com.manager.appbanhang.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserActivity extends AppCompatActivity {
    Toolbar toolbar;
    RecyclerView rcvUser;
    UserAdapter adapter;
//    List<User> userList;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_chat);

        intitView();
        actionBar();
        getUserFromFire();
    }

    private void getUserFromFire() {
        db = FirebaseFirestore.getInstance();
        db.collection("users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            List<User> userList = new ArrayList<>();
                            for(QueryDocumentSnapshot queryDocumentSnapshot : task.getResult()){
                                User user =new User();
                                user.setId(queryDocumentSnapshot.getLong("id").intValue());
                                user.setUsername(queryDocumentSnapshot.getString("username"));
                                userList.add(user);
                            }
                            if(userList.size() > 0 ){
                                adapter = new UserAdapter(getApplicationContext(),userList);
                                rcvUser.setAdapter(adapter);
                            }
                        }

                    }
                });
    }

    private void actionBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    private void intitView() {
        toolbar = findViewById(R.id.toolbarChat);
        rcvUser = findViewById(R.id.rcvUser);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rcvUser.setLayoutManager(layoutManager);
        rcvUser.setHasFixedSize(true);

    }
}