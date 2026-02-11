package com.example.conspect.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.conspect.models.Conspect;
import com.example.conspect.network.ApiService;
import com.example.conspect.network.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<List<Conspect>> conspectLiveData = new MutableLiveData<>();
    private MutableLiveData<Boolean> isLoadingLiveData = new MutableLiveData<>();
    private MutableLiveData<String> errorLiveData = new MutableLiveData<>();

    private ApiService apiService = RetrofitClient.getApiService();

    public HomeViewModel() {
        loadConpects();
    }

    public void refresh() {
        isLoadingLiveData.setValue(true);

        new android.os.Handler().postDelayed(() -> {
            List<Conspect> currentData = conspectLiveData.getValue();
            if (currentData != null) {
                conspectLiveData.setValue(currentData);
            }
            isLoadingLiveData.setValue(false);
        }, 1000);
    }

    public void loadConpects() {
        isLoadingLiveData.setValue(true);

        apiService.getAllConspects().enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<List<Conspect>> call, Response<List<Conspect>> response) {
                isLoadingLiveData.setValue(false);

                if (response.isSuccessful() && response.body() != null) {
                    conspectLiveData.setValue(response.body());
                } else {
                    errorLiveData.setValue("Ошибка сервера: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Conspect>> call, Throwable t) {
                isLoadingLiveData.setValue(false);
                errorLiveData.setValue("Ошибка сети: " + t.getMessage());
            }
        });
    }

    public LiveData<List<Conspect>> getConspects() {
        return conspectLiveData;
    }

    public LiveData<Boolean> getIsLoading() {
        return isLoadingLiveData;
    }

    public LiveData<String> getError() {
        return errorLiveData;
    }
}