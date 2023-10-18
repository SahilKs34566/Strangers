package com.project.strangers.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.project.strangers.Adapters.ChatAdapter;
import com.project.strangers.GroupChatActivity;
import com.project.strangers.Models.MessageModel;
import com.project.strangers.R;
import com.project.strangers.databinding.FragmentWriteBinding;

import java.util.ArrayList;
import java.util.Date;


public class WriteFragment extends Fragment {
    public WriteFragment() {
        // Required empty public constructor
    }
    FragmentWriteBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding= FragmentWriteBinding.inflate(inflater,container,false);

        final FirebaseDatabase database= FirebaseDatabase.getInstance();
        final ArrayList<MessageModel> messageModels = new ArrayList<>();
        final String senderId= FirebaseAuth.getInstance().getUid();
        final ChatAdapter adapter = new ChatAdapter(messageModels,getContext());
        binding.chatRecyclerView.setAdapter(adapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        binding.chatRecyclerView.setLayoutManager(layoutManager);
        database.getReference().child("Write")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        messageModels.clear();
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                            MessageModel model = dataSnapshot.getValue(MessageModel.class);
                            messageModels.add(model);
                        }
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
        binding.send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String message= binding.enterMessage.getText().toString();
                final MessageModel model= new MessageModel(senderId,message);
                model.setTimestamp(new Date().getTime());

                binding.enterMessage.setText("");
                database.getReference().child("Write")
                        .push()
                        .setValue(model)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {


                            }
                        });

            }
        });

        return binding.getRoot();
    }
}