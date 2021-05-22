package ru.sorago.homeinvandroid.ui.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ru.sorago.homeinvandroid.R;
import ru.sorago.homeinvandroid.data.model.Item;

public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ViewHolder> {
    private final LayoutInflater inflater;
    private final List<Item> items;

    public ItemsAdapter(Context context, List<Item> items) {
        this.items = items;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public ItemsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemsAdapter.ViewHolder holder, int position) {
        Item item = items.get(position);
//        holder.imageView.setImageResource(item.getFlagResource());
        holder.nameView.setText(item.getName());
        holder.typeView.setText(item.getType().getName());
        if (item.getProps() != null) {
            holder.propsView.setText(item.getProps().toString());
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final ImageView imageView;
        final TextView nameView, propsView, typeView;

        ViewHolder(View view) {
            super(view);
            imageView = view.findViewById(R.id.item_image);
            nameView = view.findViewById(R.id.item_name);
            propsView = view.findViewById(R.id.item_props);
            typeView = view.findViewById(R.id.item_type);
        }
    }
}
