package com.example.Datenow.domain;

import com.example.Datenow.domain.Comment;
import com.example.Datenow.domain.Post.Post;
import com.example.Datenow.domain.Post.PostLike;
import com.example.Datenow.domain.Post.PostMap;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
@Table(name = "UUserr")
public class User  {

    @Id
    private Long id;

    private String nickname;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<Post> postList = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<Comment> commentList = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<PostLike> postLikeList = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "post", cascade = CascadeType.REMOVE)
    private List<PostMap> postMapList = new ArrayList<>(); // Map 리스트
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

//    public void encryptPassword(String password) {
//
//        this.password = BCrypt.hashpw(password, BCrypt.gensalt());
//    }

    public void mappingPost(Post post) {
        postList.add(post);
    }

    public void mappingComment(Comment comment) {
        commentList.add(comment);
    }

    public void mappingPostLike(PostLike postLike) {
        this.postLikeList.add(postLike);
    }

    public void mappingPostMap(PostMap postMap) {
        this.postMapList.add(postMap);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        Set<GrantedAuthority> auth = new HashSet<>();
//        auth.add(new SimpleGrantedAuthority(authority));
//
//        return auth;
//    }

    public String getUsername() {
        return this.getNickname();
    }

}