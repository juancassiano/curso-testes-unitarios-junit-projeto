package com.algaworks.junit.utilidade;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;

public class BigDecimalUtilsTest {

    @ParameterizedTest
    @CsvSource({
      "10.00,10",
      "9.00,9.00"
    })
    void iguais(BigDecimal x, BigDecimal y) {
        assertTrue(BigDecimalUtils.iguais(x, y));
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/numeros.csv")
    void diferentesComCsvFile(BigDecimal x, BigDecimal y) {
        assertFalse(BigDecimalUtils.iguais(x, y));
    }
}
