package sas.search_products;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public class ProductApiService {
    private static final String BASE_URL = "https://api.mercadolibre.com/";
    private static final String PRODUCT_URL = "https://api.mercadolibre.com/";
    private static final Retrofit retrofit = new Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create())
            .baseUrl(BASE_URL)
            .build();

    public interface ApiServiceInterface {
        @GET("sites/MLM/search")
        Call<ProductsResults> getProducts(@Query("q") String productToSearch, @Query("limit") int resultsSize);

        @GET("items/{itemId}")
        Call<ProductDetail> getProductDetail(@Path("itemId") String itemId);

        @GET("items/{itemId}/description")
        Call<ProductDetail.Description> getProductDescription(@Path("itemId") String itemId);
    }

    public static class ProductsApi {
        private static ApiServiceInterface retrofitService;

        public static ApiServiceInterface getInstance() {
            if (retrofitService == null) {
                retrofitService = retrofit.create(ApiServiceInterface.class);
            }
            return retrofitService;
        }
    }
}
