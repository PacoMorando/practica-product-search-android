package sas.search_products;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

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
        this.getProductDetails();
        return this.binding.getRoot();
    }

    private void getProductDetails() {
        Call<ProductDetail> call = ProductApiService.ProductsApi.getInstance().getProductDetail(this.itemId);
        call.enqueue(this.ProductDetailResponse());
        Call<ProductDetail.Description> callDescription = ProductApiService.ProductsApi.getInstance().getProductDescription(this.itemId);
        callDescription.enqueue(this.ProductDescription());
    }

    private Callback<ProductDetail> ProductDetailResponse(){
        return new Callback<ProductDetail>() {
            @Override
            public void onResponse(Call<ProductDetail> call, Response<ProductDetail> response) {
                if (response.isSuccessful()) {
                    ProductDetail productDetail = response.body();
                    if (productDetail != null) {
                        binding.productTitle.setText(productDetail.getTitle());
                        binding.productPrice.setText(productDetail.getPrice());
                        Picasso.get().load(productDetail.getPictures().get(0).getUrl()).into(binding.productPicture);
                    }
                }
            }

            @Override
            public void onFailure(Call<ProductDetail> call, Throwable t) {
                System.out.println("Fallo la conexion");
            }
        };
    }

    private Callback<ProductDetail.Description> ProductDescription(){
        return new Callback<ProductDetail.Description>() {
            @Override
            public void onResponse(Call<ProductDetail.Description> call, Response<ProductDetail.Description> response) {
                if (response.isSuccessful()) {
                    ProductDetail.Description productDescription = response.body();
                    if (productDescription != null) {
                        binding.productDescription.setText(productDescription.getPlain_text());
                    }
                }
            }

            @Override
            public void onFailure(Call<ProductDetail.Description> call, Throwable t) {
                System.out.println("Fallo la conexion");
            }
        };
    }

    public void setProductId(String itemId){
        this.itemId = itemId;
    }
}