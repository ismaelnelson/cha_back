package com.semillero.crakruk.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.io.IOException;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "images")
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String mime;
    private String name;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    private byte[] content;

    public Image(MultipartFile file) throws IOException {
        this.mime = file.getContentType();
        this.name = file.getName();
        this.content = file.getBytes();
    }
}
