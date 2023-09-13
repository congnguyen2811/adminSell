package com.manager.appbanhang.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.manager.appbanhang.R;
import com.manager.appbanhang.Utils.Utils;
import com.manager.appbanhang.adapter.ChatAdapter;
import com.manager.appbanhang.model.ChatMessage;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class ChatActivity extends AppCompatActivity {
     int iduser;
     String iduser_str;
    RecyclerView rcvChat;
    ImageView imgChat;
    EditText edtChat;
    FirebaseFirestore db;
    ChatAdapter adapter;
    Toolbar toolbarChat;
    List<ChatMessage> chatMessageList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        iduser = getIntent().getIntExtra("id",0); // id nguoi nhan
        iduser_str = String.valueOf(iduser);
        initView();
        initControl();
        getactionbar();
        listenMess();
    }

    private void getactionbar() {
        setSupportActionBar(toolbarChat);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarChat.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initControl() {
        imgChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMesstoFire();
            }
        });
    }

    private void sendMesstoFire() {
        String strMess = edtChat.getText().toString().trim();
        if(TextUtils.isEmpty(strMess)){
            Toast.makeText(this, "Vui lòng nhập nội dung chat", Toast.LENGTH_SHORT).show();
        }
        else {
            HashMap<String,Object> message = new HashMap<>();
            message.put(Utils.SENDID,String.valueOf(Utils.user.getId()));
            message.put(Utils.RECEIVEDID,iduser_str);
            message.put(Utils.MESS,strMess);
            message.put(Utils.DATETIME,new Date());
            db.collection(Utils.PATH_CHAT).add(message);
            edtChat.setText("");
        }
    }
    private void listenMess(){
        db.collection(Utils.PATH_CHAT)
                .whereEqualTo(Utils.SENDID,String.valueOf(Utils.user.getId()))
                .whereEqualTo(Utils.RECEIVEDID,iduser_str)
                .addSnapshotListener(eventListener);
        db.collection(Utils.PATH_CHAT)
                .whereEqualTo(Utils.SENDID,iduser_str)
                .whereEqualTo(Utils.RECEIVEDID,String.valueOf(Utils.user.getId()))
                .addSnapshotListener(eventListener);
    }
    private final EventListener<QuerySnapshot> eventListener  =(value, error) -> {
        if(error != null){
            return;
        }
        if(value != null){
            int count = chatMessageList.size();
            for(DocumentChange documentChange : value.getDocumentChanges()){
                if(documentChange.getType() == DocumentChange.Type.ADDED){
                    ChatMessage chatMessage = new ChatMessage();
                    chatMessage.sendid = documentChange.getDocument().getString(Utils.SENDID);
                    chatMessage.receivedid = documentChange.getDocument().getString(Utils.RECEIVEDID);
                    chatMessage.mess = documentChange.getDocument().getString(Utils.MESS);
                    chatMessage.dateObj = documentChange.getDocument().getDate(Utils.DATETIME);
                    chatMessage.datetime = format_date(documentChange.getDocument().getDate(Utils.DATETIME));
                    chatMessageList.add(chatMessage);
                }
            }
            Collections.sort(chatMessageList,(obj1, obj2) -> obj1.dateObj.compareTo(obj2.dateObj) );
            if(count == 0){
                adapter.notifyDataSetChanged();
            }
            else {
                adapter.notifyItemRangeInserted(chatMessageList.size(),chatMessageList.size());
                rcvChat.smoothScrollToPosition(chatMessageList.size()-1);
            }
        }
    };
    private String format_date(Date date){
        return new SimpleDateFormat("MMMM dd, yyyy- hh:mm a", Locale.getDefault()).format(date);
    }
    private void initView() {
        chatMessageList = new ArrayList<>();
        db = FirebaseFirestore.getInstance();
        toolbarChat = findViewById(R.id.toolbarChat);
        rcvChat = findViewById(R.id.rcvChat);
        imgChat = findViewById(R.id.imgChat);
        edtChat = findViewById(R.id.edtChat);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rcvChat.setLayoutManager(layoutManager);
        rcvChat.setHasFixedSize(true);
        adapter = new ChatAdapter(getApplicationContext(),chatMessageList,String.valueOf(Utils.user.getId()));
        rcvChat.setAdapter(adapter);
    }
}