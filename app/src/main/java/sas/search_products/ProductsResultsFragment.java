package sas.search_products;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sas.search_products.databinding.FragmentProductsResultsBinding;

public class ProductsResultsFragment extends Fragment {
    private FragmentProductsResultsBinding binding;
    private ProductsRecyclerAdapter productsRecyclerAdapter;
    private final ProductsRecyclerAdapter.OnItemClickListener itemClickListener;


    public ProductsResultsFragment(ProductsRecyclerAdapter.OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.binding = FragmentProductsResultsBinding.inflate(inflater, container, false);
        this.productsRecyclerAdapter = new ProductsRecyclerAdapter();
        this.productsRecyclerAdapter.setItemClickListener(this.itemClickListener);
        this.binding.productsResView.setLayoutManager(new LinearLayoutManager(getContext()));
        this.binding.productsResView.setAdapter(this.productsRecyclerAdapter);
        return this.binding.getRoot();
    }

    public void searchProduct(String product) {
        Call<ProductsResults> call = ProductApiService.ProductsApi.getInstance().getProducts(product, 10);
        call.enqueue(new Callback<ProductsResults>() {
            @Override
            public void onResponse(Call<ProductsResults> call, Response<ProductsResults> response) {
                if (response.isSuccessful()) {
                    ProductsResults productsResults = response.body();
                    List<Product> products = productsResults != null ? productsResults.getProducts() : null;
                    if (products != null) {
                        productsRecyclerAdapter.setProducts((ArrayList<Product>) products);
                        productsRecyclerAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onFailure(Call<ProductsResults> call, Throwable t) {
                System.out.println("Fallo la conexion");
            }
        });
    }

    public void setTestText(String text) {
        this.binding.testFragment.setText(text);
    }
}