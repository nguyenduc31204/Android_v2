package com.example.newspaper;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.newspaper.database.repositories.UserRepository;
import com.example.newspaper.models.User;
import com.example.newspaper.ui.adapters.UserAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class ManageUsersActivity extends AppCompatActivity {

    private ListView listViewUsers;
    private UserRepository userRepository;
    private UserAdapter userAdapter;
    private List<User> userList;
    private ActivityResultLauncher<Intent> addUserLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_users);

        userRepository = new UserRepository(getApplication());
        listViewUsers = findViewById(R.id.listViewUsers);
        userList = new ArrayList<>();
        userAdapter = new UserAdapter(this, userList);
        listViewUsers.setAdapter(userAdapter);
        FloatingActionButton fabAddUser = findViewById(R.id.fabAddUser);
        fabAddUser.setOnClickListener(v -> {
            Intent intent = new Intent(ManageUsersActivity.this, AddUserActivity.class);
            addUserLauncher.launch(intent);
        });

        // Register activity result launcher for AddUserActivity
        addUserLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        loadUsers(); // Refresh the list
                        Toast.makeText(this, "Danh sách người dùng đã được cập nhật", Toast.LENGTH_SHORT).show();
                    }
                });

        loadUsers();

        //click vào người dùng
        listViewUsers.setOnItemClickListener((parent, view, position, id) -> {
            User user = userList.get(position);
            Log.d("UpdateInforActivity", "Received USER_ID: " + user.getId());
            Intent intent = new Intent(ManageUsersActivity.this, UserDetailActivity.class);
            intent.putExtra("USER_ID", user.getId());
            startActivity(intent);
            Log.d("UpdateInforActivity", "Received USER_ID: " + user.getId());
        });

        // long click để xóa
        listViewUsers.setOnItemLongClickListener((parent, view, position, id) -> {
            User user = userList.get(position);
            showDeleteConfirmationDialog(user, position);
            return true;
        });
    }



    private void loadUsers() {
        userRepository.getAllUsers().observe(this, users -> {
            if (users != null) {
                userList.clear();
                userList.addAll(users);
                userAdapter.notifyDataSetChanged();
            }
        });
    }

    private void showDeleteConfirmationDialog(User user, int position) {
        new AlertDialog.Builder(this)
                .setTitle("Xóa người dùng")
                .setMessage("Bạn có chắc chắn muốn xóa " + user.getUsername() + "?")
                .setPositiveButton("Xóa", (dialog, which) -> {
                    userRepository.deleteUser(user, new UserRepository.DeleteCallback() {
                        @Override
                        public void onSuccess() {
                            runOnUiThread(() -> {
                                userList.remove(position);
                                userAdapter.notifyDataSetChanged();
                                Toast.makeText(ManageUsersActivity.this, "Đã xóa người dùng", Toast.LENGTH_SHORT).show();
                            });
                        }

                        @Override
                        public void onError(String errorMessage) {
                            runOnUiThread(() -> {
                                Toast.makeText(ManageUsersActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                            });
                        }
                    });
                })
                .setNegativeButton("Hủy", null)
                .show();
    }
}