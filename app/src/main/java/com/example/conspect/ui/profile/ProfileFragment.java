package com.example.conspect.ui.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.conspect.databinding.FragmentProfileBinding;

public class ProfileFragment extends Fragment {
    private FragmentProfileBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        TextView textView = binding.textProfile;
        textView.setText("Профиль пользователя");

        setupSwipeRefresh();

        return binding.getRoot();
    }

    private void setupSwipeRefresh() {
        binding.swipeRefreshLayout.setColorSchemeColors(
                ContextCompat.getColor(requireContext(), android.R.color.holo_blue_bright),
                ContextCompat.getColor(requireContext(), android.R.color.holo_green_light),
                ContextCompat.getColor(requireContext(), android.R.color.holo_orange_light),
                ContextCompat.getColor(requireContext(), android.R.color.holo_red_light)
        );


        binding.swipeRefreshLayout.setOnRefreshListener(() -> {
            Toast.makeText(getContext(), "Обновление профиля...", Toast.LENGTH_SHORT).show();

            new android.os.Handler().postDelayed(() -> {
                binding.swipeRefreshLayout.setRefreshing(false);
                Toast.makeText(getContext(), "Профиль обновлён", Toast.LENGTH_SHORT).show();
            }, 1000);
        });
    }
}
