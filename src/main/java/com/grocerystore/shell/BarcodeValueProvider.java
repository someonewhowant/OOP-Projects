package com.grocerystore.shell;

import com.grocerystore.model.Item;
import com.grocerystore.service.CatalogService;
import org.springframework.shell.CompletionContext;
import org.springframework.shell.CompletionProposal;
import org.springframework.shell.standard.ValueProvider;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class BarcodeValueProvider implements ValueProvider {

    private final CatalogService catalogService;

    public BarcodeValueProvider(CatalogService catalogService) {
        this.catalogService = catalogService;
    }

    @Override
    public List<CompletionProposal> complete(CompletionContext completionContext) {
        String prefix = completionContext.currentWordUpToCursor();
        if (prefix == null) {
            prefix = "";
        }
        
        final String searchPrefix = prefix;

        return catalogService.getAllItems().stream()
                .map(Item::getBarcode)
                .filter(b -> b.startsWith(searchPrefix))
                .map(b -> {
                    String name = catalogService.getItem(b).map(Item::getName).orElse("");
                    return new CompletionProposal(b)
                            .displayText(b)
                            .description(name); // Shows item name next to barcode
                })
                .collect(Collectors.toList());
    }
}
