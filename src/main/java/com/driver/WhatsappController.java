package com.driver;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("whatsapp")
public class WhatsappController {

    private final WhatsappService whatsappService;

    public WhatsappController(WhatsappService whatsappService) {
        this.whatsappService = whatsappService;
    }

    @PostMapping("/add-user")
    public ResponseEntity<String> createUser(@RequestParam String name, @RequestParam String mobile) {
        try {
            whatsappService.createUser(name, mobile);
            return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/add-group")
    public ResponseEntity<Group> createGroup(@RequestBody List<User> users) {
        try {
            Group group = whatsappService.createGroup(users);
            return new ResponseEntity<>(group, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/add-message")
    public ResponseEntity<Integer> createMessage(@RequestParam String content) {
        int messageId = whatsappService.createMessage(content);
        return new ResponseEntity<>(messageId, HttpStatus.OK);
    }

    @PutMapping("/send-message")
    public ResponseEntity<Integer> sendMessage(@RequestBody Message message, @RequestBody User sender, @RequestBody Group group) {
        try {
            int finalMessageCount = whatsappService.sendMessage(message, sender, group);
            return new ResponseEntity<>(finalMessageCount, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/change-admin")
    public ResponseEntity<String> changeAdmin(@RequestBody User approver, @RequestBody User user, @RequestBody Group group) {
        try {
            String result = whatsappService.changeAdmin(approver, user, group);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/remove-user")
    public ResponseEntity<Integer> removeUser(@RequestBody User user) {
        try {
            int result = whatsappService.removeUser(user);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/find-messages")
    public ResponseEntity<String> findMessage(@RequestParam Date start, @RequestParam Date end, @RequestParam int K) {
        try {
            String result = whatsappService.findMessage(start, end, K);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
