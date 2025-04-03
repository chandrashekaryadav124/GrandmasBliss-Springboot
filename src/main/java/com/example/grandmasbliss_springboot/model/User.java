package com.example.grandmasbliss_springboot.model;




import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "users") // Ensure this matches your Atlas collection name
public class User {
    
    @Id
    private String id;
    private String username;
    private String email;
    private String password; // Ensure existing passwords are hashed
    private Set<String> roles; // Example: ["CUSTOMER", "ADMIN"]
}
