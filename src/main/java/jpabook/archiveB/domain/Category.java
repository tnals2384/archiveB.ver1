package jpabook.archiveB.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Category {
    NOVEL("소설"),
    IT("IT/프로그래밍"),
    HEALTH("건강/의학"),
    QUOTE("시/에세이"),
    RELIGION("종교"),
    LANGUAGE("언어"),
    ECONOMY("경제"),
    LIFE("가정/생활"),
    SCIENCE("과학/공학");


    private final String value;

}
