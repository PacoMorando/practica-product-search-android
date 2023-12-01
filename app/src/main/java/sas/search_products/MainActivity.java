package sas.search_products;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.Menu;
import android.widget.SearchView;

import sas.search_products.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements ProductsRecyclerAdapter.OnItemClickListener {
    private ActivityMainBinding binding;
    private FragmentTransaction fragmentTransaction;
    private ProductsResultsFragment productsResultsFragment;
    private final ProductFragment productFragment = new ProductFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setDataBinding();
        setContentView(this.binding.getRoot());
        setSupportActionBar(this.binding.mainToolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.productsResultsFragment = new ProductsResultsFragment(this);
        this.fragmentTransaction = getSupportFragmentManager().beginTransaction();
        this.fragmentTransaction.setReorderingAllowed(true);
        this.fragmentTransaction.replace(R.id.products_results_fragment, this.productsResultsFragment, null);
        this.fragmentTransaction.commit();


        /*getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                //.add(R.id.products_results_fragment, ProductsResultsFragment.class, null) Probar como funciona este
                .replace(R.id.products_results_fragment, this.productsResultsFragment, null)
                .commit();*/

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
            if (!getSupportFragmentManager().getFragments().contains(this.productsResultsFragment)) {
               /* getSupportFragmentManager().beginTransaction()
                        .setReorderingAllowed(true)
                        .replace(R.id.products_results_fragment, this.productsResultsFragment, null)
                        .commit();*/
                System.out.println("popBackStack()");
                getSupportFragmentManager().popBackStack();
            }
        });

        assert searchView != null;
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                if (!getSupportFragmentManager().getFragments().contains(productsResultsFragment)) {
                    getSupportFragmentManager().popBackStack();
                }
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

    @Override
    public void onClick(String itemId) {
        System.out.println();
        this.productFragment.setProductId(itemId);
        this.fragmentTransaction = getSupportFragmentManager().beginTransaction();
        this.fragmentTransaction.replace(R.id.products_results_fragment, this.productFragment, null);
        this.fragmentTransaction.addToBackStack(null);// AL remplazar el fragment se agrega al que vamos a regresar
        this.fragmentTransaction.commit();

        /*getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.products_results_fragment, this.productFragment, null)
                .commit();*/

    }
}