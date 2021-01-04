package com.example.videoviewapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.videoviewapp.Model.Users;
import com.example.videoviewapp.Model.Videos;
import com.example.videoviewapp.databinding.ActivityMainBinding;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    View image;
    Toolbar toolbar;
    ActivityMainBinding binding;
    FirebaseAuth auth;
    RecyclerView recview;
    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        image = findViewById(R.id.logIn);
        setSupportActionBar(toolbar);
        recview = findViewById(R.id.recview);
        auth = FirebaseAuth.getInstance();

        database = FirebaseDatabase.getInstance();

        Toast.makeText(this, "hello", Toast.LENGTH_SHORT).show();

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_toolbar);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.add:
                        Intent intent = new Intent(MainActivity.this, CreateVideoActivity.class);
                        startActivity(intent);
                        break;
                    

                    default:

                        break;
                }

                return true;
            }
        });

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                auth.signOut();
                Toast.makeText(MainActivity.this, "Log Out", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, SignInActivity.class);
                startActivity(intent);
                finish();
            }
        });


        recview=(RecyclerView)findViewById(R.id.recview);
        recview.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<Videos> options =
                new FirebaseRecyclerOptions.Builder<Videos>()
                        .setQuery(database.getReference().child("myvideos"), Videos.class)
                        .build();


        FirebaseRecyclerAdapter<Videos,myviewholder> firebaseRecyclerAdapter=new FirebaseRecyclerAdapter<Videos, myviewholder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull myviewholder holder, int position, @NonNull Videos model) {
                holder.prepareexoplayer(getApplication(),model.getTitle(),model.getVurl());
            }

            @NonNull
            @Override
            public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.singlerow,parent,false);
                return new myviewholder(view);
            }
        };

        firebaseRecyclerAdapter.startListening();
        recview.setAdapter(firebaseRecyclerAdapter);

    }

}