package org.weebook.api.service.crawler;

import lombok.Getter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;
import org.weebook.api.entity.Book;
import org.weebook.api.entity.Genre;
import org.weebook.api.entity.Inventory;
import org.weebook.api.entity.embed.BookDescribe;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.weebook.api.service.crawler.Constants.*;

@Getter
@Component
public class BookCrawler {

    private final Set<Book> books;
    private final Random random = new Random(System.currentTimeMillis());
    private Book book;
    private String link;
    private Integer page;

    public BookCrawler() {
        this.books = new LinkedHashSet<>();
    }

    public static BookCrawlerBuilder builder() {
        return new BookCrawlerBuilder();
    }


    public void fetch() throws Exception {
        int[] pages = IntStream.rangeClosed(1, page).toArray();

        for (int p : pages) {
            Document document = Jsoup.connect(link + p).get();

            System.out.println("Fetching page: " + p);

            Elements cardElements = document.select(CARD_CLASS);

            for (var cardElement : cardElements) {
                if (cardElement != null) {
                    fetchBook(cardElement);
                }
            }
        }
    }

    private void fetchBook(Element element) throws Exception {
        if (element == null) return;

        this.book = new Book();

        if (element.selectFirst(SERIES_CLASS) != null) return;

        //get Episode
        Element chapter = element.selectFirst(CHAPTER_CLASS);
        if (chapter != null) {
            book.setEpisode(chapter.text());
        }

        var detailLink = Objects.requireNonNull(element.selectFirst(DETAIL_LINK_CLASS)).attr("href");
        fetchDetail(detailLink);
    }

    private void fetchDetail(String detailLink) throws Exception {
        Document document = Jsoup.connect(detailLink).get();

        String name = getValueFrom(document, BOOK_NAME_CLASS);
        BigDecimal price = currencyToNumber(getValueFrom(document, BOOK_PRICE_CLASS));
        Integer discount = percentToInt(getValueFrom(document, BOOK_DISCOUNT_CLASS));
        String[] gallery = getImages(document);

        book.setName(name);
        book.setPrice(price);
        book.setDiscount(discount);
        book.setGallery(Arrays.asList(gallery));

        fetchDescribe(document);
    }

    private void fetchDescribe(Document document)
            throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        BookDescribe describe = new BookDescribe();

        var maps = describeMap();

        for (var entry : maps.entrySet()) {
            String key = entry.getKey();
            String className = entry.getValue();
            String value = getValueFrom(document, className);

            BookDescribe.class.getMethod(key, String.class).invoke(describe, value);
        }
        Inventory inventory = getSkuCode();
        Set<Genre> genres = getGenres(document);

        this.book.setSkuCode(inventory);
        this.book.setBookDescribe(describe);
        this.book.setGenres(genres);
        this.books.add(this.book);
    }

    private Set<Genre> getGenres(Document document) {
        String elementText = getValueFrom(document, ".data_genres");
        if (elementText == null) return Collections.emptySet();

        String[] values = elementText.split(", ");

        return Arrays.stream(values)
                .map(value -> Genre.builder().name(value).book(this.book).build())
                .collect(Collectors.toSet());
    }

    private Inventory getSkuCode() {
        int min = -2;
        int max = 50;
        int randomQuantity = random.nextInt(max - min + 1) + min;
        var status = Inventory.Status.getStatus(Math.min(randomQuantity, 1));

        return Inventory.builder()
                .skuCode(UUID.randomUUID().toString().replace("-", ""))
                .quantity(randomQuantity)
                .status(status)
                .build();
    }

    private String[] getImages(Document document) {
        Elements elements = document.select(BOOK_GALLERY_CLASS);
        return elements.stream().map(ele -> ele.attr("src")).toArray(String[]::new);
    }

    private String getValueFrom(Document document, String className) {
        Element element = document.selectFirst(className);

        if (element == null) return null;

        return element.text();
    }

    private Integer percentToInt(String percentValue) {
        if (percentValue == null || percentValue.isEmpty()) return null;

        return Integer.valueOf(percentValue.substring(1, percentValue.length() - 1));
    }

    private BigDecimal currencyToNumber(String currency) {
        if (currency == null || currency.isEmpty()) return null;

        String value = currency.substring(0, currency.length() - 2).replace(".", "");

        return new BigDecimal(value);
    }


    public static class BookCrawlerBuilder {
        private final BookCrawler bookCrawler = new BookCrawler();

        public BookCrawlerBuilder link(String link) {
            bookCrawler.link = link;
            return this;
        }

        public BookCrawlerBuilder page(Integer page) {
            bookCrawler.page = page;
            return this;
        }

        public BookCrawler build() {
            return bookCrawler;
        }
    }
}
