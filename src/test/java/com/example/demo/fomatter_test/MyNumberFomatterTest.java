package com.example.demo.fomatter_test;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.shadow.com.univocity.parsers.annotations.Parsed;
import org.springframework.format.support.DefaultFormattingConversionService;

import javax.print.PrintException;
import java.text.ParseException;
import java.util.Locale;

import static org.assertj.core.api.Assertions.*;

import static org.junit.jupiter.api.Assertions.*;

class MyNumberFomatterTest {
    MyNumberFomatter formatter = new MyNumberFomatter();

    @Test
    void parse() throws ParseException{
        Number result = formatter.parse("1,000", Locale.KOREA);
        assertThat(result).isEqualTo(1000L);
    }

    @Test
    void print() throws PrintException{
        String result = formatter.print(1000, Locale.KOREA);
        assertThat(result).isEqualTo("1,000");
    }

    @Test
    void formatterConversionService(){
        DefaultFormattingConversionService conversionService = new DefaultFormattingConversionService();

        //포맷터 등록
        conversionService.addFormatter(new MyNumberFomatter());

        //포맷터 사용
        assertThat(conversionService.convert(1000, String.class)).isEqualTo("1,000");
        assertThat(conversionService.convert("1,000", Long.class)).isEqualTo(1000L);
    }


}