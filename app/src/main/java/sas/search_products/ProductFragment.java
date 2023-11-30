package sas.search_products;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sas.search_products.databinding.FragmentProductBinding;

public class ProductFragment extends Fragment {
    private FragmentProductBinding binding;
    private String itemId = "";

    public ProductFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.binding = FragmentProductBinding.inflate(inflater, container, false);
        this.binding.productId.setText(this.itemId);
        //this.setProductDetails();
        this.getProductDetails();
        return this.binding.getRoot();
    }

    private void setProductDetails() {
        //this.getProductDetails();

    }

    private void getProductDetails() {
        Call<ProductDetail> call = ProductApiService.ProductsApi.getInstance().getProductDetail(this.itemId);
        call.enqueue(new Callback<ProductDetail>() {
            @Override
            public void onResponse(Call<ProductDetail> call, Response<ProductDetail> response) {
                if (response.isSuccessful()) {
                    ProductDetail productDetail = response.body();
                    //List<Product> products = productproductDetail != null ? productproductDetail.getProducts() : null;
                    if (productDetail != null) {
                        binding.productId.setText(productDetail.getId());
                        binding.productTitle.setText(productDetail.getTitle());
                        binding.productPrice.setText(productDetail.getPrice());
                    }
                }
            }

            @Override
            public void onFailure(Call<ProductDetail> call, Throwable t) {
                System.out.println("Fallo la conexion");
            }
        });
    }

    public void setProductId(String itemId){
        this.itemId = itemId;
    }
}