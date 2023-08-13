package org.weebook.api.service.crawler;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.weebook.api.entity.Book;
import org.weebook.api.entity.Category;
import org.weebook.api.repository.BookRepository;

import java.util.Set;

import static org.weebook.api.service.crawler.Constants.*;

@Component
@RequiredArgsConstructor
public class CrawlerRunner implements CommandLineRunner {

    private final BookRepository bookRepository;
    private final SeriesCrawler seriesCrawler;

    @Override
    public void run(String... args) throws Exception {
//        System.out.println("---Starting crawl data--- ");
//        insertsBook();
//        insertsSeriesAndUpdateBook();
//        System.out.println("Finished");
    }

    private void insertsSeriesAndUpdateBook() throws Exception {
        for (var entry : NGOAI_NGU_SERIES_MAP.entrySet()) {
            final String link = entry.getKey();
            final Integer totalPageNums = entry.getValue();

            seriesCrawler.setLink(NGOAI_NGU_BASE_URL.formatted(link));
            seriesCrawler.setPage(totalPageNums);

            System.out.println("Fetching from: " + link);
            seriesCrawler.fetch();
            System.out.println("Fetching successfully!!!");
        }
    }

    private void insertsBook() throws Exception {
        for (var entry : NGOAI_NGU_BOOK_MAP.entrySet()) {
            final String link = entry.getKey();
            final Integer totalPageNums = entry.getValue();

            BookCrawler bookCrawler = BookCrawler.builder()
                    .link(NGOAI_NGU_BASE_URL.formatted(link))
                    .page(totalPageNums)
                    .build();

            System.out.println("Fetching from: " + link);
            bookCrawler.fetch();
            System.out.println("Fetching successfully!!!");

            Set<Book> books = bookCrawler.getBooks();

            Category category = Category.builder()
                    .name("Ngoại ngữ")
                    .subCategory(linkToSubCategory(link))
                    .build();

            books.forEach(book -> book.setCategory(category));
            bookRepository.saveAllAndFlush(books);
        }
    }

    private String linkToSubCategory(String link) {
        switch (link) {
            //van hoc
            case "ngon-tinh" -> {
                return "Ngôn Tình";
            }
            case "light-novel" -> {
                return "Light Novel";
            }

            case "truyen-ngan-tan-van" -> {
                return "Truyện ngắn - tản văn";
            }
            case "tieu-thuyet" -> {
                return "Tiểu Thuyết";
            }

            //Ngoai ngu
            case "tieng-anh" -> {
                return "Tiếng Anh";
            }
            case "tieng-nhat" -> {
                return "Tiếng Nhật";
            }
            case "tieng-han" -> {
                return "Tiếng Hàn";
            }

            case "tieng-hoa" -> {
                return "Tiếng Hoa";
            }

            default -> {
                return null;
            }
        }
    }
}
