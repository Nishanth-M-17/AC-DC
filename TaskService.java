package ACDC.hack_backend.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;   // for unique IDs

import ACDC.hack_backend.entity.LostEntity;
import ACDC.hack_backend.entity.claimedEntity;
import ACDC.hack_backend.entity.entity;
import ACDC.hack_backend.entity.foundEntity;
import org.springframework.stereotype.Service;

@Service
public class TaskService {
    private Map<String, LostEntity> lostItems = new HashMap<>();
    private Map<String, foundEntity> foundItems = new HashMap<>();

    // Post a lost item (auto-generate ID)
    public LostEntity postLostItem(LostEntity item) {
        String generatedId = UUID.randomUUID().toString();
        item.setId(generatedId);
        lostItems.put(generatedId, item);
        return item; // return with generated ID
    }

    // Post a found item (auto-generate ID)
    public foundEntity postFoundItem(foundEntity item) {
        String generatedId = UUID.randomUUID().toString();
        item.setId(generatedId);
        foundItems.put(generatedId, item);
        return item; // return with generated ID
    }

    // Search items by keyword
    public List<entity> searchItems(String keyword) {
        List<entity> results = new ArrayList<>();
        lostItems.values().stream()
                .filter(i -> i.getName().toLowerCase().contains(keyword.toLowerCase()))
                .forEach(results::add);
        foundItems.values().stream()
                .filter(i -> i.getName().toLowerCase().contains(keyword.toLowerCase()))
                .forEach(results::add);
        return results;
    }

    public void claimItem(String id, claimedEntity claimRequest) {
        foundEntity item = foundItems.get(id);
        if (item != null) {
            item.setClaimed(true);
            // Optionally: log claimant info somewhere (e.g., a separate claimedItems list)
            foundItems.remove(id);
            // You could store claimant details in a separate map if needed
            System.out.println("Claimed by: " + claimRequest.getClaimantName()
                    + " (" + claimRequest.getClaimantDepartment() + ")");
        }
    }


    // Optional: Get found item by ID
    public foundEntity getFoundItemById(String id) {
        return foundItems.get(id);
    }

    public List<LostEntity> getAllLostItems() {
        return new ArrayList<>(lostItems.values());
    }

    public List<foundEntity> getAllFoundItems() {
        return new ArrayList<>(foundItems.values());
    }

}
