package sas.search_products;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.Menu;
import android.widget.SearchView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sas.search_products.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private final ProductsResultsFragment productsResultsFragment = new ProductsResultsFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setDataBinding();
        setContentView(this.binding.getRoot());
        setSupportActionBar(this.binding.mainToolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                //.add(R.id.products_results_fragment, ProductsResultsFragment.class, null) Probar como funciona este
                .replace(R.id.products_results_fragment, productsResultsFragment, null)
                .commit();
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
        this.binding.productsResViewMain.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        this.binding.productsResViewMain.setAdapter(productsRecyclerAdapter);
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
            this.productsResultsFragment.setTestText("Se ha cambiado el texto desde el main");
            System.out.println("Picaste la flechita");
        });

        assert searchView != null;
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                System.out.println("onQueryTextSubmit METHOD Y ESCRIBISTE: " + s);
                //searchProduct(s);
                productsResultsFragment.searchProduct(s);
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