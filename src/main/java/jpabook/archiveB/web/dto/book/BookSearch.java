package jpabook.archiveB.web.dto.book;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class BookSearch {
    //private String title;
    //private String author;
    private String searchWord; //통합검색
}
