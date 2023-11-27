package sas.search_products;

import com.squareup.moshi.Json;

public class Product {
    @Json(name = "id")
    private String id;
    @Json(name = "title")
    private String title;
    @Json(name = "thumbnail")
    private String thumbnail;
    @Json(name = "price")
    private String price;
    @Json(name = "address")
    private Address address;

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public String getPrice() {
        return price;
    }

    public Address getAddress() {
        return address;
    }

    static class Address{
        @Json(name = "state_name")
        private String stateName;

        public String getStateName() {
            return stateName;
        }
    }
}
