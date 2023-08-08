package com.utkarsh.Testing.service;

import com.utkarsh.Testing.entity.CatalogItem;
import com.utkarsh.Testing.repository.CatalogItemRepository;

import org.apache.coyote.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.xml.catalog.Catalog;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@Service
public class CatalogItemService {
    private final Logger LOGGER = LoggerFactory.getLogger(CatalogItemService.class);
    private final CatalogItemRepository catalogItemRepository;

    @Autowired
    public CatalogItemService(CatalogItemRepository catalogItemRepository){
        this.catalogItemRepository = catalogItemRepository;
    }

    public ResponseEntity<HashMap<String, Object>> addCatalogItem(CatalogItem catalogItem){
        HashMap<String, Object> responseMessage = new HashMap<>();
        try{
            LOGGER.info("making entry for the catalog item: {}", catalogItem.toString());
            catalogItemRepository.save(catalogItem);
            LOGGER.info("successfully made entry for catalog item, generate id: {}", catalogItem.getId());
            responseMessage.put("message", "successfully made entry with id : " + catalogItem.getId());
            responseMessage.put("catalogItem", catalogItem);
            return new ResponseEntity<>(responseMessage, HttpStatus.CREATED);
        }catch(Exception exception){
            LOGGER.error("An error occurred while adding object {} to db, error: {}", catalogItem, exception.getMessage());
            responseMessage.put("message", "an internal server error occurred while making the entry");
            return new ResponseEntity<>(responseMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<HashMap<String, Object>> getCatalogItemById(Long catalogItemId){
        HashMap<String, Object> response = new HashMap<>();
        try{
            LOGGER.info("fetching the catalog item with id {}", catalogItemId);
            CatalogItem catalogItem = catalogItemRepository.getReferenceById(catalogItemId);
            LOGGER.info("fetched object: {}", catalogItem.toString());
            LOGGER.info("fetched object, generating response");
            response.put("message", "successfully fetched the catalog item");
            response.put("catalogItem", catalogItem);
            LOGGER.info("response generated, returning the response: {}", response);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch(Exception e){
            LOGGER.error("error occurred while getting catalog item with id {}, error : {}",
                    catalogItemId , e.getMessage());
            response.put("message", "internal server error");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<HashMap<String, Object>> updateCatalogItem(CatalogItem catalogItem){
        HashMap<String, Object> response = new HashMap<>();
        if(Objects.isNull(catalogItem.getId())){
            LOGGER.error("bad request for updating, no id provided");
            response.put("message", "bad request, no id provided in the request");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        try{
            CatalogItem existingCatalogItem = catalogItemRepository.getReferenceById(catalogItem.getId());
            if(!Objects.isNull(catalogItem.getName())){
                LOGGER.info("updating the name of the catalog item with id: {}", catalogItem.getId());
                existingCatalogItem.setName(catalogItem.getName());
            }
            if(!Objects.isNull(catalogItem.getDescription())){
                LOGGER.info("updating the description of the catalog item with id: {}", catalogItem.getId());
                existingCatalogItem.setDescription(catalogItem.getDescription());
            }
            LOGGER.info("saving the updates in the catalog item with id : {}", catalogItem.getId());
            catalogItemRepository.save(catalogItem);
            response.put("message", "successfully updated the catalog item");
            response.put("catalogItem", existingCatalogItem);
            return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
        }catch(Exception e){ //Handling any unexpected error
            LOGGER.error("error occurred while updating catalog item with id {}, error : {}",
                    catalogItem.getId(), e.getMessage());
            response.put("message", "internal server error");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<HashMap<String, Object>> deleteCatalogItemWithId(Long catalogItemId){
        HashMap<String , Object> response = new HashMap<>();
        try{
            catalogItemRepository.deleteById(catalogItemId);
            LOGGER.info("deleted item with id {}", catalogItemId);
            response.put("message", "successfully delete catalog item with id : " + catalogItemId);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception e){
            LOGGER.error("Error occurred while deleting the catalog item with id {}, error : {}",
                    catalogItemId, e.getMessage());
            response.put("message", "internal server error");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



}
