package org.weebook.api.entity.embed;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Embeddable
public class BookDescribe {

    @Column(name = "\"availableAge\"", length = Integer.MAX_VALUE)
    private String availableAge;

    @Column(name = "supplier", length = Integer.MAX_VALUE)
    private String supplier;

    @Column(name = "authors")
    private List<String> authors;

    @Column(name = "translator", length = Integer.MAX_VALUE)
    private String translator;

    @Column(name = "publisher", length = Integer.MAX_VALUE)
    private String publisher;

    @Column(name = "\"publishYear\"", length = Integer.MAX_VALUE)
    private String publishYear;

    @Column(name = "language", length = Integer.MAX_VALUE)
    private String language;

    @Column(name = "size", length = Integer.MAX_VALUE)
    private String size;

    @Column(name = "\"numsPage\"")
    private Integer numsPage;

    @Column(name = "content", length = Integer.MAX_VALUE)
    private String content;

    @Column(name = "weight", length = Integer.MAX_VALUE)
    private String weight;
    @Column(name = "\"bookLayout\"", length = Integer.MAX_VALUE)
    private String bookLayout;

    public void setAuthors(String authors) {
        if (authors == null) return;

        String[] values = authors.split(", ");
        this.authors = Arrays.asList(values);
    }

    public void setNumsPage(String numsPage) {
        if (numsPage == null) return;

        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(numsPage);

        if (matcher.find()) {
            String number = matcher.group();
            this.numsPage = Integer.valueOf(number);
        }
    }
}