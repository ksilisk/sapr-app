package com.ksilisk.sapr.utils;

import com.ksilisk.sapr.dto.BarDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.ksilisk.sapr.utils.CollectionsUtils.equalLists;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CollectionsUtilsTest {
    @Test
    void equalListsTest() {
        assertTrue(equalLists(asList("hello", "world"), asList("world", "hello")));
    }

    @Test
    void equalListsTest1() {
        assertFalse(equalLists(asList("hello", "world"), asList("worlddd", "hello")));
    }

    @Test
    void equalListsTest2() {
        assertTrue(equalLists(null, null));
    }

    @Test
    void equalListsTest3() {
        assertFalse(equalLists(emptyList(), null));
    }

    @Test
    void equalListsTest4() {
        assertFalse(equalLists(emptyList(), List.of("hello")));
    }

    @Test
    void equalListsTest5() {
        List<BarDTO> barDTOS = asList(new BarDTO(1, 1, 1), new BarDTO(1, 2, 1));
        List<BarDTO> barDTOS1 = asList(new BarDTO(1, 2, 3), new BarDTO(1, 4, 5));
        assertFalse(equalLists(barDTOS1, barDTOS));
    }

    @Test
    void equalListsTest6() {
        BarDTO barDTO = new BarDTO(1, 1, 1);
        BarDTO barDTO1 = new BarDTO(1, 1, 1);
        Assertions.assertEquals(barDTO1.hashCode(), barDTO.hashCode());
    }
}