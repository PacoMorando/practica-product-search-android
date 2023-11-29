package sas.search_products;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
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
        setSupportActionBar(this.binding.mainToolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void searchProduct(String product) {
        Call<ProductsResults> call = ProductApiService.ProductsApi.getInstance().getProducts(product, 10);
        call.enqueue(new Callback<ProductsResults>() {
            @Override
            public void onResponse(Call<ProductsResults> call, Response<ProductsResults> response) {
                if (response.isSuccessful()) {
                    ProductsResults productsResults = response.body();
                    List<Product> products = productsResults != null ? productsResults.getProducts() : null;
                    if (products != null) {
                        setUsersRecyclerView((ArrayList<Product>) products);
                    }
                }
            }

            @Override
            public void onFailure(Call<ProductsResults> call, Throwable t) {
                System.out.println("Fallo la conexion");
            }
        });
    }

    private void setUsersRecyclerView(ArrayList<Product> products) {
        ProductsRecyclerAdapter productsRecyclerAdapter = new ProductsRecyclerAdapter(products);
        this.binding.productsResView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        this.binding.productsResView.setAdapter(productsRecyclerAdapter);
    }

    private void setDataBinding() {
        this.binding = ActivityMainBinding.inflate(getLayoutInflater());
        this.binding.setMainActivity(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main_head_menu, menu);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();

        this.binding.mainToolBar.setNavigationOnClickListener(view -> {
            System.out.println("Picaste la flechita");
        });

        assert searchView != null;
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                System.out.println("onQueryTextSubmit METHOD Y ESCRIBISTE: " + s);
                searchProduct(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

        return true;
    }
}