package sas.search_products;

import com.squareup.moshi.Json;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

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
        NumberFormat formatPrice = NumberFormat.getCurrencyInstance(new Locale("es", "MX"));
        return "MX" + formatPrice.format(Double.parseDouble(this.price));
    }

    public List<Picture> getPictures() {
        return pictures;
    }

    static class Picture {
        @Json(name = "url")
        private String url;
        @Json(name = "secure_url")
        private String secureUrl;

        public String getUrl() {
            if (this.url != null && this.url.startsWith("http://")) {
                this.url = this.url.replace("http://", "https://");
            }
            return url;
        }

        public String getSecureUrl() {
            return secureUrl;
        }
    }

    static class Description {
        @Json(name = "plain_text")
        private String plain_text;

        public String getPlain_text() {
            return plain_text;
        }
    }
}
