package com.example.Datenow.repository;

import com.example.Datenow.domain.Category;
import com.example.Datenow.domain.Post.Post;
import com.example.Datenow.domain.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.Datenow.domain.Category.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class PostRepositoryTest {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;


    @Test
    void findAll() {
        User user = new User();
        user.setId(1L);
        User user1 = new User();
        user1.setId(2L);

        userRepository.save(user);
        userRepository.save(user1);

        List<User> users = userRepository.findAll();

        System.out.println("answer : " + users.size());
        assertNotNull(users);
    }


    @Test
    void findById() {
        User user = new User();
        user.setId(1L);
        userRepository.save(user);

        User testUser = userRepository.findById(user.getId()).get();
        assertNotNull(testUser);
    }


    @Test
    void save() {
        User user = new User();
        user.setId(1L);
        userRepository.save(user);

        Post post = Post.builder()
                .title("hihi")
                .content("네네네네반가워요")
                .user(user)
                .category(실내데이트)
                .viewCnt(0)
                .scrapCnt(0)
                .recommendCnt(0)
                .imageUrl("dasdasdasdsa")
                .build();

        postRepository.save(post);
        List<Post> posts = postRepository.findAll();
        System.out.println("answer : " + posts.size());
    }

    @Test
    void delete() {
        System.out.println("pass");
    }

    @Test
    void findAllOrderByRecommendCntDesc() {
        // User 생성
        User user = new User();
        user.setId(1L);
        userRepository.save(user);

        // Map 생성
        HashMap<Double, Double> map = new HashMap<>();
        map.put(12.312312, 12.123123);

        List<HashMap<Double, Double>> mapArray = new ArrayList<>();
        mapArray.add(map);

        // Post 객체를 recommendCnt를 다르게 생성
        Post post = Post.builder()
                .title("hihi")
                .content("네네네네반가워요")
                .user(user)
                .category(실내데이트)
                .postMapList(mapArray)
                .viewCnt(0)
                .scrapCnt(0)
                .recommendCnt(0)
                .imageUrl("dasdasdasdsa")
                .build();


        Post post1 = Post.builder()
                .title("hihi")
                .content("네네네네반가워요")
                .user(user)
                .category(실내데이트)
                .postMapList(mapArray)
                .viewCnt(0)
                .scrapCnt(0)
                .recommendCnt(20)
                .imageUrl("dasdasdasdsa")
                .build();

        Post post2 = Post.builder()
                .title("hihi")
                .content("네네네네반가워요")
                .user(user)
                .category(실내데이트)
                .postMapList(mapArray)
                .viewCnt(0)
                .scrapCnt(0)
                .recommendCnt(10)
                .imageUrl("dasdasdasdsa")
                .build();

        postRepository.save(post);
        postRepository.save(post1);
        postRepository.save(post2);

        // recommendCnt 순으로 정렬되었으면 성공
        List<Post> posts = postRepository.findAllByOrderByRecommendCntDesc();


        for (int i=0; i <posts.size(); i++) {
            Long id = posts.get(i).getId();
            System.out.println("answer" + id);
        }
    }



    @Test
    void findByCategory() {
        // User 생성
        User user = new User();
        user.setId(1L);
        userRepository.save(user);

        // Map 생성
        HashMap<Double, Double> map = new HashMap<>();
        map.put(12.312312, 12.123123);

        List<HashMap<Double, Double>> mapArray = new ArrayList<>();
        mapArray.add(map);

        // Post 객체를 recommendCnt를 다르게 생성
        Post post = Post.builder()
                .title("hihi")
                .content("네네네네반가워요")
                .user(user)
                .category(실내데이트)
                .postMapList(mapArray)
                .viewCnt(0)
                .scrapCnt(0)
                .recommendCnt(0)
                .imageUrl("dasdasdasdsa")
                .build();
        
        Post post1 = Post.builder()
                .title("hihi")
                .content("네네네네반가워요")
                .user(user)
                .category(실외데이트)
                .postMapList(mapArray)
                .viewCnt(0)
                .scrapCnt(0)
                .recommendCnt(20)
                .imageUrl("dasdasdasdsa")
                .build();

        Post post2 = Post.builder()
                .title("hihi")
                .content("네네네네반가워요")
                .user(user)
                .category(겨울데이트)
                .postMapList(mapArray)
                .viewCnt(0)
                .scrapCnt(0)
                .recommendCnt(10)
                .imageUrl("dasdasdasdsa")
                .build();

        postRepository.save(post);
        postRepository.save(post1);
        postRepository.save(post2);

        Category category = 실내데이트;
        // 실내 데이트 카테고리의 게시글들만 반환되면 성공
        List<Post> posts = postRepository.findByCategory(category);

        for (int i=0; i <posts.size(); i++) {
            Long id = posts.get(i).getId();
            System.out.println("answer" + id);
        }

    }

    @Test
    void findByTitleContaining() {
        // User 생성
        User user = new User();
        user.setId(1L);
        userRepository.save(user);

        // Map 생성
        HashMap<Double, Double> map = new HashMap<>();
        map.put(12.312312, 12.123123);

        List<HashMap<Double, Double>> mapArray = new ArrayList<>();
        mapArray.add(map);

        // Post 객체를 recommendCnt를 다르게 생성
        Post post = Post.builder()
                .title("홀리")
                .content("네네네네반가워요")
                .user(user)
                .category(실내데이트)
                .postMapList(mapArray)
                .viewCnt(0)
                .scrapCnt(0)
                .recommendCnt(0)
                .imageUrl("dasdasdasdsa")
                .build();

        Post post1 = Post.builder()
                .title("hihi")
                .content("네네네네반가워요")
                .user(user)
                .category(실외데이트)
                .postMapList(mapArray)
                .viewCnt(0)
                .scrapCnt(0)
                .recommendCnt(20)
                .imageUrl("dasdasdasdsa")
                .build();

        Post post2 = Post.builder()
                .title("hello")
                .content("네네네네반가워요")
                .user(user)
                .category(겨울데이트)
                .postMapList(mapArray)
                .viewCnt(0)
                .scrapCnt(0)
                .recommendCnt(10)
                .imageUrl("dasdasdasdsa")
                .build();

        postRepository.save(post);
        postRepository.save(post1);
        postRepository.save(post2);
        
        // 실내 데이트 카테고리의 게시글들만 반환되면 성공
        List<Post> posts = postRepository.findByTitleContaining("hello");

        for (int i=0; i <posts.size(); i++) {
            Long id = posts.get(i).getId();
            System.out.println("answer" + id);
        }

    }

    @Test
    void findByUsername() {

    }

    @Test
    void findRecommendByMe() {

    }
}