package ACDC.hack_backend.controller;

import ACDC.hack_backend.entity.LostEntity;
import ACDC.hack_backend.entity.claimedEntity;
import ACDC.hack_backend.entity.foundEntity;
import ACDC.hack_backend.service.TaskService;
import ACDC.hack_backend.entity.entity;

import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/CampusCompass")
public class main_taskcontrol {
    private final TaskService service;

    public main_taskcontrol(TaskService service) {
        this.service = service;
    }

    // ✅ Post a lost item
    @PostMapping("/lost")
    public ResponseEntity<LostEntity> postLost(@RequestBody LostEntity item) {
        LostEntity saved = service.postLostItem(item);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved); // 201 Created
    }

    // ✅ Post a found item
    @PostMapping("/found")
    public ResponseEntity<foundEntity> postFound(@RequestBody foundEntity item) {
        foundEntity saved = service.postFoundItem(item);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved); // 201 Created
    }

    // ✅ Search items by keyword
    @GetMapping("/search")
    public ResponseEntity<List<entity>> search(@RequestParam String keyword) {
        List<entity> results = service.searchItems(keyword);
        if (results.isEmpty()) {
            return ResponseEntity.noContent().build(); // 204 No Content
        }
        return ResponseEntity.ok(results); // 200 OK
    }

    @GetMapping("/lost")
    public ResponseEntity<List<LostEntity>> getAllLost() {
        List<LostEntity> results = service.getAllLostItems();
        if (results.isEmpty()) {
            return ResponseEntity.noContent().build(); // 204 No Content
        }
        return ResponseEntity.ok(results); // 200 OK
    }

    // ✅ Get all found items
    @GetMapping("/found")
    public ResponseEntity<List<foundEntity>> getAllFound() {
        List<foundEntity> results = service.getAllFoundItems();
        if (results.isEmpty()) {
            return ResponseEntity.noContent().build(); // 204 No Content
        }
        return ResponseEntity.ok(results); // 200 OK
    }
    @PostMapping("/claim/{id}")
    public ResponseEntity<String> claim(
            @PathVariable String id,
            @RequestBody claimedEntity claimRequest) {

        foundEntity item = service.getFoundItemById(id);
        if (item == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Item not found");
        }

        service.claimItem(id, claimRequest);
        return ResponseEntity.ok("Item claimed successfully by "
                + claimRequest.getClaimantName() + " (" + claimRequest.getClaimantDepartment() + ")");
    }







}
