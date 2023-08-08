package com.utkarsh.Testing.controller;

import com.utkarsh.Testing.entity.CatalogItem;
import com.utkarsh.Testing.service.CatalogItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/catalogItem")
public class CatalogItemController {
    private CatalogItemService catalogItemService;

    @Autowired
    public CatalogItemController(CatalogItemService catalogItemService){
        this.catalogItemService = catalogItemService;
    }

    @PostMapping("/")
    public ResponseEntity<HashMap<String, Object>> addCatalogItem(@RequestBody CatalogItem catalogItem){
        return catalogItemService.addCatalogItem(catalogItem);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HashMap<String, Object>> getCatalogItemByName(@PathVariable("id")Long id){
        return catalogItemService.getCatalogItemById(id);
    }

    @PutMapping("/")
    public ResponseEntity<HashMap<String, Object>> updateCatalogItem(@RequestBody CatalogItem catalogItem){
        return catalogItemService.updateCatalogItem(catalogItem);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HashMap<String, Object>> deleteCatalogItem(@PathVariable("id")Long id){
        return catalogItemService.deleteCatalogItemWithId(id);
    }
}
