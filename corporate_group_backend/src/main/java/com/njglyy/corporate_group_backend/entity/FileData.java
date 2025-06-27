package com.njglyy.corporate_group_backend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FileData {
    private String fileName;
    private String fileContent; // This will hold the Base64 encoded content

    // Constructors, getters, and setters
}