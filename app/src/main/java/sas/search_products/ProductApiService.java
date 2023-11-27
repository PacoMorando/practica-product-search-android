package sas.search_products;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class ProductApiService {
    private static final String BASE_URL = "https://api.mercadolibre.com/sites/MLM/";
    //private static final String BASE_URL = "https://api.mercadolibre.com/sites/";
    private static final Retrofit retrofit = new Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create())
            .baseUrl(BASE_URL)
            .build();

    public interface ApiServiceInterface {
        //@GET("search?q=iphone&limit=4")
        @GET("search")
       Call<ProductsResults> getProducts(@Query("q") String productToSearch, @Query("limit") int resultsSize);
        //Call<ProductsResults> getProducts();
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
