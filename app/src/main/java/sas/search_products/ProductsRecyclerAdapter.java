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
    private  OnItemClickListener itemClickListener;

    public void setItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    private ArrayList<Product> products = new ArrayList<>();

  /*  public ProductsRecyclerAdapter( OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }*/

    @NonNull
    @Override
    public ProductsRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductsRecyclerAdapter.ViewHolder holder, int position) {
        holder.setItemProduct(this.products.get(position));
        holder.itemView.setOnClickListener(view -> {
            System.out.println("Picaste: " + holder.id);
            this.itemClickListener.onClick(holder.id);
        });
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {

        private String id;
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
            this.id =  product.getId();
            Picasso.get().load(product.getThumbnail()).into(this.thumbnail, new Callback() {
                @Override
                public void onSuccess() {
                    System.out.println("loaded image");
                }
                @Override
                public void onError(Exception e) {
                    e.printStackTrace();
                }
            });
            this.price.setText(product.getPrice());
            this.title.setText(product.getTitle());
            this.stateName.setText(product.getAddress().getStateName());
        }
    }
    public interface OnItemClickListener{
        void onClick(String itemId);
    }
}