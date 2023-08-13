package com.semillero.crakruk.model;

import com.semillero.crakruk.auth.model.UserModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "comments")
@SQLDelete(sql = "UPDATE comments SET deleted = true WHERE id = ?")
@Where(clause = "deleted = false")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    @ManyToOne
    @JoinColumn(name="users")
    private UserModel user;

    @Lob
    private String body;

    @OneToMany
    private List<Reply> reply = new ArrayList<>();

    @ManyToMany
    private List<UserModel> like;



    @Column(name = "deleted", nullable = false, columnDefinition = "boolean default false")
    private Boolean deleted = Boolean.FALSE;

    @CreationTimestamp
    private LocalDateTime created;

    @UpdateTimestamp
    private LocalDateTime updated;


    public void addReply(Reply reply){
        this.reply.add(reply);
    }

    public void addLike(UserModel user){this.like.add(user);}








}
