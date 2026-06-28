package com.grocerystore.service;

import com.grocerystore.model.Item;
import com.grocerystore.repository.ItemRepository;
import com.grocerystore.exception.ItemNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CatalogServiceTest {

    @Mock
    private ItemRepository itemRepository;

    @InjectMocks
    private CatalogService catalogService;

    private Item testItem;

    @BeforeEach
    void setUp() {
        testItem = new Item("Apple", "123", "Fruits", new BigDecimal("10.00"));
    }

    @Test
    void testGetItem_Success() {
        when(itemRepository.findByBarcode("123")).thenReturn(Optional.of(testItem));
        
        Optional<Item> result = catalogService.getItem("123");
        
        assertTrue(result.isPresent());
        assertEquals("Apple", result.get().getName());
    }

    @Test
    void testUpdateItem_ItemNotFound() {
        when(itemRepository.findByBarcode("123")).thenReturn(Optional.empty());
        
        assertThrows(ItemNotFoundException.class, () -> catalogService.updateItem(testItem));
        verify(itemRepository, never()).save(any());
    }

    @Test
    void testUpdateItem_Success() {
        when(itemRepository.findByBarcode("123")).thenReturn(Optional.of(testItem));
        
        catalogService.updateItem(testItem);
        
        verify(itemRepository).save(testItem);
    }
}
