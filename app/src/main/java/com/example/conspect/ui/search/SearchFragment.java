package com.example.conspect.ui.search;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.conspect.R;
import com.example.conspect.databinding.FragmentSearchBinding;

public class SearchFragment extends Fragment {
    private FragmentSearchBinding binding;

    @SuppressLint("SetTextI18n")
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSearchBinding.inflate(inflater, container, false);
        TextView textView = binding.textSearch;
        textView.setText(R.string.searchFragment);

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
            Toast.makeText(getContext(), "Обновление поиска...", Toast.LENGTH_SHORT).show();

            new android.os.Handler().postDelayed(() -> {
                binding.swipeRefreshLayout.setRefreshing(false);
                Toast.makeText(getContext(), "Поиск обновлён", Toast.LENGTH_SHORT).show();
            }, 1000);
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
