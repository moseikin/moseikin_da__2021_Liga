package entities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GoodsTest {

    @Test
    void getCatalog_NotNull() {
        assertNotNull(new Goods().getCatalog());

    }
}