package com.example.conspect;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.navigation.fragment.NavHostFragment;

import com.example.conspect.databinding.ActivityMainBinding;
import com.example.conspect.network.ApiService;
import com.example.conspect.network.RetrofitClient;
import com.example.conspect.ui.create.CreateConspectActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private NavController navController;
    private AppBarConfiguration appBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        try {
            NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.nav_host_fragment);
            if (navHostFragment != null) {
                navController = navHostFragment.getNavController();

                appBarConfiguration = new AppBarConfiguration.Builder(
                        R.id.homeFragment,
                        R.id.searchFragment,
                        R.id.profileFragment
                ).build();

                NavigationUI.setupWithNavController(binding.bottomNavigation, navController);

                Log.d("Main Activity", "Navigation успешно настроен");
            } else {
                Log.d("Main Activity", "NavHostFragment не найден!");
                showErrorAndContinue();
            }
        } catch (Exception e) {
            Log.e("Main Activity", "Ошибка настройки Navigation: " + e.getMessage());
            showErrorAndContinue();
        }

        setupFAB();

        testServerConnection();
    }

    private void showErrorAndContinue() {
        binding.bottomNavigation.setVisibility(View.GONE);
        Toast.makeText(this, "Навигация временно недоступна", Toast.LENGTH_SHORT).show();
    }

    private void setupFAB() {
        binding.fab.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, CreateConspectActivity.class);
            startActivity(intent);
        });
    }

    private void testServerConnection() {
        ApiService api = RetrofitClient.getApiService();
        api.testConnection().enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                if (response.isSuccessful()) {
                    Log.d("SERVER_TEST", "Сервер доступен: " + response.body());
                } else {
                    Log.e("SERVER_TEST", "Ошибка сервера" + response.code());
                    Toast.makeText(MainActivity.this,
                            "Сервер недоступен (код: " + response.code() + ")", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                Log.e("SERVER_TEST", "Нет подключения к серверу: " + t.getMessage());
                Toast.makeText(MainActivity.this,
                        "Проверьте подключение к серверу. Запущен ли Spring Boot?",
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        return NavigationUI.navigateUp(navController, appBarConfiguration) || super.onSupportNavigateUp();
    }
}