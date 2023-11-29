package sas.search_products;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ProductsRecyclerAdapter extends RecyclerView.Adapter<ProductsRecyclerAdapter.ViewHolder> {
    private final ArrayList<Product> products;

    public ProductsRecyclerAdapter(ArrayList<Product> products) {
        this.products = products;
    }

    @NonNull
    @Override
    public ProductsRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductsRecyclerAdapter.ViewHolder holder, int position) {
        holder.setItemProduct(this.products.get(position));
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView thumbnail;
        private TextView price;
        private TextView title;
        private TextView stateName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.setViews(itemView);
        }

        private void setViews(View itemView) {
            this.thumbnail = itemView.findViewById(R.id.thumbnail);
            this.price = itemView.findViewById(R.id.price);
            this.title = itemView.findViewById(R.id.title);
            this.stateName = itemView.findViewById(R.id.state_name);
        }

        public void setItemProduct(Product product) {
            Picasso.get().load(product.getThumbnail()).into(this.thumbnail, new Callback() {
                @Override
                public void onSuccess() {
                    System.out.println("SE CARGO CORRECTAMENTE LA IMAGEN");
                }

                @Override
                public void onError(Exception e) {
                    System.out.println("NO CARGO");
                    e.printStackTrace();
                }
            });
           System.out.println(product.getThumbnail());
            this.price.setText(product.getPrice());
            this.title.setText(product.getTitle());
            this.stateName.setText(product.getAddress().getStateName());
        }
    }
}