package sas.search_products;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sas.search_products.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setDataBinding();
        setContentView(this.binding.getRoot());

        this.binding.testFetch.setText("First Text data binding");

        Call<ProductsResults> call = ProductApiService.ProductsApi.getInstance().getProducts("nintendo", 5);
        //Call<ProductsResults> call = ProductApiService.ProductsApi.getInstance().getProducts();
        call.enqueue(new Callback<ProductsResults>() {
            @Override
            public void onResponse(Call<ProductsResults> call, Response<ProductsResults> response) {
                if (response.isSuccessful()) {
                    ProductsResults productsResults = response.body();
                    List<Product> products = productsResults != null ? productsResults.getProducts() : null;
                    if (products != null) {
                        /*setUsersRecyclerView((ArrayList<User>) users);*/
                        binding.testFetch.setText(productsResults.toString());
                    }
                }
            }

            @Override
            public void onFailure(Call<ProductsResults> call, Throwable t) {
                System.out.println("Fallo el pedo");
            }
        });
    }

    private void setDataBinding() {
        this.binding = ActivityMainBinding.inflate(getLayoutInflater());
        this.binding.setMainActivity(this);
    }
}