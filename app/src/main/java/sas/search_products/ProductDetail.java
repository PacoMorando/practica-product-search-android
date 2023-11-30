package sas.search_products;

import com.squareup.moshi.Json;

import java.util.List;

public class ProductDetail {
    @Json(name = "id")
    private String id;
    @Json(name = "title")
    private String title;

    @Json(name = "price")
    private String price;
    @Json(name = "pictures")
    private List<Picture> pictures;

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getPrice() {
        return price;
    }

    public List<Picture> getPictures() {
        return pictures;
    }

    static class Picture {
        @Json(name = "url")
        private String url;
        @Json(name = "secure_url")
        private String size;

    }

    static class Description {
        @Json(name = "plain_text")
        private String plain_text;
        @Json(name = "secure_url")
        private String size;

    }
}
