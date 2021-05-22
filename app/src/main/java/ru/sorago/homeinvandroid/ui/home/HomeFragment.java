package ru.sorago.homeinvandroid.ui.home;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.sorago.homeinvandroid.R;
import ru.sorago.homeinvandroid.api.ApiClient;
import ru.sorago.homeinvandroid.api.SessionManager;
import ru.sorago.homeinvandroid.data.model.Item;
import ru.sorago.homeinvandroid.data.response.base.ListResponse;
import ru.sorago.homeinvandroid.data.response.type.ItemData;

public class HomeFragment extends Fragment {

    private View root;
    private HomeViewModel homeViewModel;
    private RecyclerView recyclerView;
    private ItemsAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        root = inflater.inflate(R.layout.fragment_home, container, false);
        final TextView textView = root.findViewById(R.id.text_home);
        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        pullItems();

        return root;
    }

    private void pullItems() {
        ApiClient.getApiService().pullItems(SessionManager.fetchAuthToken(getActivity().getApplicationContext()),null, null).enqueue(new Callback<ListResponse<ItemData>>() {
            @Override
            public void onResponse(Call<ListResponse<ItemData>> call, Response<ListResponse<ItemData>> response) {
                List<Item> items = new ArrayList<>();
                if (response.body() != null && response.body().getData() != null) {
                    response.body().getData().forEach(itemData -> {
                        Item item = new Item();
                        item.setName(itemData.getName());
                        item.setType(itemData.getType());
                        item.setProps(itemData.getProps());
                        items.add(item);
                    });
                }
                generateItemList(items);
            }

            @Override
            public void onFailure(Call<ListResponse<ItemData>> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(root.getContext(), "Nope, server ded", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void generateItemList(List<Item> items) {
        recyclerView = root.findViewById(R.id.home_item_list);
        adapter = new ItemsAdapter(getContext(), items);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }
}