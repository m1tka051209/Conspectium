package com.example.conspect.ui.create;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.example.conspect.models.Conspect;
import com.example.conspect.network.ApiService;
import com.example.conspect.network.RetrofitClient;

import com.example.conspect.databinding.ActivityCreateConspectBinding;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

public class CreateConspectActivity extends AppCompatActivity {

    private ActivityCreateConspectBinding binding;
    private ApiService apiService;
    private TextInputEditText titleEditText, subjectEditText, contentEditText;
    private TextInputLayout titleInputLayout, subjectInputLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreateConspectBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        apiService = RetrofitClient.getApiService();

        setupToolbar();
        setupViews();
        setupSaveButton();
    }

    private void setupToolbar() {
        setSupportActionBar(binding.toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        binding.toolbar.setNavigationOnClickListener(v -> finish());
    }

    private void setupViews() {
        titleEditText = binding.titleEdittext;
        subjectEditText = binding.subjectEdittext;
        contentEditText = binding.contentEdittext;
    }

    private void setupSaveButton() {
        binding.saveButton.setOnClickListener(v -> {
            if (validateInputs()) {
                saveConspect();
            }
        });
    }

    private boolean validateInputs() {
        boolean isValid = true;

        String title = Objects.requireNonNull(titleEditText.getText()).toString().trim();
        String subject = Objects.requireNonNull(subjectEditText.getText()).toString().trim();
        String content = Objects.requireNonNull(contentEditText.getText()).toString().trim();

        if (title.isEmpty()) {
            titleInputLayout.setError("Введите название конспекта");
            isValid = false;
        } else {
            titleInputLayout.setError(null);
        }

        if (subject.isEmpty()) {
            subjectInputLayout.setError("Введите предмет");
            isValid = false;
        } else {
            subjectInputLayout.setError(null);
        }

        if (content.isEmpty()) {
            Toast.makeText(this, "Введите текст конспекта", Toast.LENGTH_SHORT).show();
            isValid = false;
        }
        return isValid;
    }

    private void saveConspect() {
        String title = Objects.requireNonNull(titleEditText.getText()).toString().trim();
        String subject = Objects.requireNonNull(subjectEditText.getText()).toString().trim();
        String content = Objects.requireNonNull(contentEditText.getText()).toString().trim();

        Conspect newConspect = new Conspect();
        newConspect.setTitle(title);
        newConspect.setSubject(subject);
        newConspect.setContent(content);

        binding.saveButton.setEnabled(false);
        binding.saveButton.setText("Сохранение...");
        binding.progressBar.setVisibility(View.VISIBLE);

        apiService.createConspect(newConspect).enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<Conspect> call, @NonNull Response<Conspect> response) {
                binding.saveButton.setEnabled(true);
                binding.saveButton.setText("Сохранить конспект");
                binding.progressBar.setVisibility(View.GONE);

                if (response.isSuccessful()) {
                    Toast.makeText(CreateConspectActivity.this,
                            "Конспект успешно сохранен!", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    String errorMsg = "Ошибка сервера: " + response.code();
                    if (response.code() == 401) {
                        errorMsg = "Ошибка авторизации";
                    } else if (response.code() == 500) {
                        errorMsg = "Ошибка на сервере";
                    }
                    Toast.makeText(CreateConspectActivity.this, errorMsg, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<Conspect> call, @NonNull Throwable t) {
                binding.saveButton.setEnabled(true);
                binding.saveButton.setText("Сохранить конспект");
                binding.progressBar.setVisibility(View.GONE);

                Toast.makeText(CreateConspectActivity.this,
                        "Ошибка сети: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
