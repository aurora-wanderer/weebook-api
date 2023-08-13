/*
 * *
 *  * @author: To Hoang Tuan, Date : "8/13/23, 8:05 PM"
 *
 */
package org.weebook.api.service.crawler;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class Constants {

    private Constants() {
    }

    //COMMON
    public static final String CARD_CLASS = ".ma-box-content";
    public static final String CHAPTER_CLASS = ".episode-label";
    public static final String DETAIL_LINK_CLASS = "a.product-image";
    public static final String SERIES_CLASS = ".fhs-series";

    public static final String VAN_HOC_BASE_URL
            = "https://www.fahasa.com/sach-trong-nuoc/van-hoc-trong-nuoc/%s.html?order=created_at&limit=48&p=";
    public static final String NGOAI_NGU_BASE_URL
            = "https://www.fahasa.com/sach-trong-nuoc/sach-hoc-ngoai-ngu/%s.html?order=created_at&limit=48&p=";

    public static Map<String, String> describeMap() {
        var dataFields = new HashMap<>(Map.of(
                "setAvailableAge", ".data_age",
                "setSupplier", ".data_supplier",
                "setAuthors", ".data_author",
                "setTranslator", ".data_translator",
                "setPublisher", ".data_publisher",
                "setPublishYear", ".data_publish_year",
                "setLanguage", ".data_languages",
                "setWeight", ".data_weight",
                "setSize", ".data_size",
                "setBookLayout", ".data_book_layout"
        ));
        dataFields.put("setNumsPage", ".data_qty_of_page");
        dataFields.put("setContent", ".std");

        return dataFields;
    }

    // MAPS
    public static final Map<String, Integer> VAN_HOC_BOOK_MAP = new LinkedHashMap<>(
            Map.of(
                    "ngon-tinh", 8,
                    "light-novel", 15,
                    "truyen-ngan-tan-van", 22,
                    "tieu-thuyet", 43
            )
    );

    public static final Map<String, Integer> VAN_HOC_SERIES_MAP = new LinkedHashMap<>(
            Map.of(
                    "ngon-tinh", 2,
                    "light-novel", 2,
                    "truyen-ngan-tan-van", 2,
                    "tieu-thuyet", 1
            )
    );

    public static final Map<String, Integer> NGOAI_NGU_BOOK_MAP = new LinkedHashMap<>(
            Map.of(
                    "tieng-han", 4,
                    "tieng-nhat", 7,
                    "tieng-hoa", 8,
                    "tieng-anh", 37
            )
    );

    public static final Map<String, Integer> NGOAI_NGU_SERIES_MAP = new LinkedHashMap<>(
            Map.of(
                    "tieng-han", 1,
                    "tieng-nhat", 2,
                    "tieng-hoa", 3,
                    "tieng-anh", 8
            )
    );


    //BOOK
    public static final String BOOK_NAME_CLASS = ".kasitoo h1";
    public static final String BOOK_PRICE_CLASS = ".special-price .price";
    public static final String BOOK_DISCOUNT_CLASS = ".price-box > .old-price > .discount-percent";
    public static final String BOOK_GALLERY_CLASS = ".lightgallery a > img";


    //SERIES
    public static final String SERIES_NAME_CLASS = ".product-name-no-ellipsis.fhs-series a";
    public static final String SERIES_SUBSCRIBES_CLASS = ".fhs-series-subscribes";
    public static final String SERIES_NEWEST_EP_CLASS = ".fhs-series-episode-label";
}
