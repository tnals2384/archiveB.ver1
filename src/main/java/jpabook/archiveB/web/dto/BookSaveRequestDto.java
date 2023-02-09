package jpabook.archiveB.web.dto;


import jpabook.archiveB.domain.BookCategory;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
public class BookSaveRequestDto {
    private Long id;
    private String title;
    private String author;
    private String isbn;

    private String coverImg;
    private String plot;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date publicationDate;


}
