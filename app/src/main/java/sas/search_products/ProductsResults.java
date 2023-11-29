package sas.search_products;

import androidx.annotation.NonNull;

import com.squareup.moshi.Json;

import java.util.List;

public class ProductsResults {
    @Json(name = "results")
    private List<Product> products;

    public List<Product> getProducts() {
        return products;
    }

    @NonNull
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Product product : this.products) {
            stringBuilder.append(product.getThumbnail() + "\n");
        }
        return stringBuilder.toString();
    }
}
