package org.weebook.api.service.crawler;

import lombok.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.NumberUtils;
import org.weebook.api.entity.Book;
import org.weebook.api.entity.Series;
import org.weebook.api.repository.BookRepository;
import org.weebook.api.repository.SeriesRepository;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

import static java.text.MessageFormat.format;
import static org.weebook.api.service.crawler.Constants.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Component
public class SeriesCrawler {

    private String link;
    private Integer page;
    private BookRepository bookRepository;
    private SeriesRepository seriesRepository;

    @Autowired
    public SeriesCrawler(BookRepository bookRepository, SeriesRepository seriesRepository) {
        this.bookRepository = bookRepository;
        this.seriesRepository = seriesRepository;
    }

    public void fetch() throws Exception {
        int[] pages = IntStream.rangeClosed(1, page).toArray();

        for (int p : pages) {
            Document document = Jsoup.connect(link + p + "&series_type=1").get();

            System.out.println("Fetching page: " + p);

            Elements cardElements = document.select(CARD_CLASS);

            for (var cardElement : cardElements) {
                if (cardElement != null) {
                    fetchSeries(cardElement);
                }
            }
        }

    }

    private void fetchSeries(Element element) {
        String name = getValueFrom(element, SERIES_NAME_CLASS);
        String subscribes = getValueFrom(element, SERIES_SUBSCRIBES_CLASS);
        String newestEpisode = getValueFrom(element, SERIES_NEWEST_EP_CLASS);

        Series series = Series.builder()
                .name(name)
                .subscribes(parseNumber(subscribes, Long.class))
                .newestEpisode(parseNumber(newestEpisode, Integer.class))
                .build();
        Series saved = seriesRepository.saveAndFlush(series);

        List<Book> books = fetchBooks(name);
        books.forEach(book -> book.setSeries(saved));
        bookRepository.saveAllAndFlush(books);
    }

    private List<Book> fetchBooks(String name) {
        List<Book> books = bookRepository.findAll((root, query, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), format("%{0}%", name).toLowerCase()));
        return bookRepository.saveAll(books);
    }

    private @NonNull String getValueFrom(Element element, String className) {
        Element ele = element.selectFirst(className);

        assert ele != null;

        return ele.text();
    }

    private <T extends Number> T parseNumber(String text, Class<T> numberClass) {
        Matcher matcher = Pattern.compile("\\d+").matcher(text);

        if (matcher.find()) {
            return NumberUtils.parseNumber(matcher.group(), numberClass);
        }

        return null;
    }
}
