package com.example.Datenow.service;

import com.example.Datenow.DTO.PostRequestDto;
import com.example.Datenow.DTO.PostResponseDto;
import com.example.Datenow.domain.Category;
import com.example.Datenow.domain.Comment;
import com.example.Datenow.domain.Post.Post;
import com.example.Datenow.domain.Post.PostLike;
import com.example.Datenow.domain.User;
import com.example.Datenow.repository.CommentRepository;
import com.example.Datenow.repository.PostLikeRepository;
import com.example.Datenow.repository.PostRepository;
import com.example.Datenow.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static com.example.Datenow.domain.Category.실내데이트;
import static com.example.Datenow.domain.Category.실외데이트;


@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional // 이 어노테이션이 테스트에 있으면 데이터를 롤백 함 -> 테스트시 DB에 영향이 없게 한다.
class PostServiceTest {

    @Autowired private PostService postService;
    @Autowired private PostRepository postRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private PostLikeRepository postLikeRepository;

    @Autowired private CommentRepository commentRepository;

    @Autowired private CommentService commentService;


    @Test
    void save() {
        User user = new User();
        user.setId(1L);
        user.setNickname("봉봉");
        userRepository.save(user);

        // Map 생성
        HashMap<Double, Double> map = new HashMap<>();
        map.put(12.312312, 12.123123);

        List<HashMap<Double, Double>> mapArray = new ArrayList<>();
        mapArray.add(map);

        PostRequestDto newpost = PostRequestDto.builder()
                .title("hihi")
                .content("네네네네반가워요")
                .category(실내데이트)
                .lat(2.123123)
                .lng(1.23232)
                .imageUrl("dasdasdasdsa")
                .build();

        // Post 등록
        Post post = postService.save(newpost, 1L);


        System.out.println("answer" +  PostResponseDto.fromUpdatePost(post));

    }

    @Test
    void updatePost() {
        // User, Post 생성
        User user = new User();
        user.setId(1L);
        user.setNickname("봉봉");
        userRepository.save(user);


        // Map 생성
        HashMap<Double, Double> map = new HashMap<>();
        map.put(12.312312, 12.123123);

        List<HashMap<Double, Double>> mapArray = new ArrayList<>();
        mapArray.add(map);

        List<Comment> commentList = new ArrayList<>();

        PostRequestDto oldPost = PostRequestDto.builder()
                .title("hihi")
                .content("네네네네반가워요")
                .writer(user)
                .category(실내데이트)
                .map(mapArray)
                .viewCnt(0)
                .scrapCnt(0)
                .recommendCnt(0)
                .imageUrl("dasdasdasdsa")
                .build();

        Post post = oldPost.toEntity();
        postRepository.save(post);

        PostRequestDto newPost = PostRequestDto.builder()
                .title("업데이트 했어용")
                .content("업데이트 되었습니다.")
                .writer(user)
                .category(실내데이트)
                .map(mapArray)
                .viewCnt(0)
                .scrapCnt(0)
                .recommendCnt(0)
                .imageUrl("dasdasdasdsa")
                .build();


        Post entityPost = postRepository.save(newPost.toEntity());
        PostResponseDto postResponseDto = PostResponseDto.fromUpdatePost(entityPost);

        System.out.println("answer : " + postResponseDto.getTitle() + " " + postResponseDto.getContent());



    }

    @Test
    void delete() {
        // User, Post 생성
        User user = new User();
        user.setId(1L);
        user.setNickname("봉봉");
        userRepository.save(user);


        // Map 생성
        HashMap<Double, Double> map = new HashMap<>();
        map.put(12.312312, 12.123123);

        List<HashMap<Double, Double>> mapArray = new ArrayList<>();
        mapArray.add(map);

        PostRequestDto newpost = PostRequestDto.builder()
                .title("hihi")
                .content("네네네네반가워요")
                .writer(user)
                .category(실내데이트)
                .map(mapArray)
                .viewCnt(0)
                .scrapCnt(0)
                .recommendCnt(0)
                .imageUrl("dasdasdasdsa")
                .build();


        // DTO에 값을 담으면 ID는 어케 되는겨?? Id가 Null인데?? -> toEntity()로 엔티티로 변경하고,DB에 Save되면 pk값이 생긴다.
        Post post = newpost.toEntity();
        Post newPost = postRepository.save(post);


        // 삭제 후 Post가 사라져야 함
        postService.delete(newPost.getId(), 1L);

        Optional<Post> checkPost = postRepository.findById(newPost.getId());

        System.out.println("answer : " + checkPost);


    }

    @Test
    void postLike() {
        // User1 생성
        User user = new User();
        user.setId(1L);
        user.setNickname("봉봉");
        userRepository.save(user);

        // User2 생성
        User user1 = new User();
        user1.setId(2L);
        user1.setNickname("봉2");
        userRepository.save(user1);

        // User3 생성
        User user2 = new User();
        user2.setId(3L);
        user2.setNickname("봉3");
        userRepository.save(user2);

        // Map 생성
        HashMap<Double, Double> map = new HashMap<>();
        map.put(12.312312, 12.123123);

        List<HashMap<Double, Double>> mapArray = new ArrayList<>();
        mapArray.add(map);

        PostRequestDto newpost = PostRequestDto.builder()
                .title("hihi")
                .content("네네네네반가워요")
                .writer(user)
                .category(실내데이트)
                .map(mapArray)
                .viewCnt(0)
                .scrapCnt(0)
                .recommendCnt(0)
                .imageUrl("dasdasdasdsa")
                .build();


        Post post = newpost.toEntity();
        Post newPost = postRepository.save(post);

        
        // postLike 등록
        postService.postLike(newPost.getId(), 1L);

        Optional<PostLike> postLike1 = postLikeRepository.findByPostAndUser(newPost, user);

        //System.out.println("answer : " + postLike1);

        // postLike 삭제
        postService.postLike(newPost.getId(), 1L);

        Optional<PostLike> postLike2 = postLikeRepository.findByPostAndUser(newPost, user);

        //System.out.println("answer : " + postLike2);

        // 여러 사람이 postLike를 눌렀을 때 좋아요 수 변동 확인하기
        postService.postLike(newPost.getId(), 1L);
        postService.postLike(newPost.getId(), 2L);
        postService.postLike(newPost.getId(), 3L);

        System.out.println("answer : " + post.getRecommendCnt());
    }

    @Test
    void findById() {
        // User, Post 생성
        User user = new User();
        user.setId(1L);
        user.setNickname("봉봉");
        userRepository.save(user);


        // Map 생성
        HashMap<Double, Double> map = new HashMap<>();
        map.put(12.312312, 12.123123);

        List<HashMap<Double, Double>> mapArray = new ArrayList<>();
        mapArray.add(map);

        List<Comment> commentList = new ArrayList<>();

        Post post = Post.builder()
                .title("hihi")
                .content("네네네네반가워요")
                .user(user)
                .category(실내데이트)
                .postMapList(mapArray)
                .commentList(commentList)
                .viewCnt(0)
                .scrapCnt(0)
                .recommendCnt(0)
                .imageUrl("dasdasdasdsa")
                .build();


        Post newPost = postRepository.save(post);


        // 첫번째 조회했을 때 조회수는 1이 나와야한다.
        PostResponseDto postResponseDto = postService.findById(newPost.getId());

        System.out.println("answer : " + postResponseDto + "  " + postResponseDto.getViewCnt());

        // 네번째 조회했을 때 조회수는 4가 나와야한다.
        postService.findById(newPost.getId());
        postService.findById(newPost.getId());
        PostResponseDto newPostResponseDto = postService.findById(newPost.getId());
        System.out.println("answer : " + newPostResponseDto.getViewCnt());


    }

    @Test
    void findAll() {
        // User생성
        User user = new User();
        user.setId(1L);
        user.setNickname("봉봉");
        userRepository.save(user);


        // Map 생성
        HashMap<Double, Double> map = new HashMap<>();
        map.put(12.312312, 12.123123);

        List<HashMap<Double, Double>> mapArray = new ArrayList<>();
        mapArray.add(map);
        
        // 댓글 생성
        List<Comment> commentList = new ArrayList<>();
        
        // Post 생성
        Post post = Post.builder()
                .title("0000000000")
                .content("네네네네반가워요")
                .user(user)
                .category(실내데이트)
                .postMapList(mapArray)
                .commentList(commentList)
                .viewCnt(0)
                .scrapCnt(0)
                .recommendCnt(0)
                .imageUrl("dasdasdasdsa")
                .build();


        Post newPost = postRepository.save(post);

        Post post1 = Post.builder()
                .title("111111111111111")
                .content("네네네네반가워요")
                .user(user)
                .category(실내데이트)
                .postMapList(mapArray)
                .commentList(commentList)
                .viewCnt(0)
                .scrapCnt(0)
                .recommendCnt(0)
                .imageUrl("dasdasdasdsa")
                .build();


        Post newPost1 = postRepository.save(post1);

        Post post2 = Post.builder()
                .title("2222222222")
                .content("네네네네반가워요")
                .user(user)
                .category(실내데이트)
                .postMapList(mapArray)
                .commentList(commentList)
                .viewCnt(0)
                .scrapCnt(0)
                .recommendCnt(0)
                .imageUrl("dasdasdasdsa")
                .build();


        Post newPost2 = postRepository.save(post2);

        // List<PostResponseDto> 타입으로 반환
        
        List<PostResponseDto> postResponseDtoList = postService.findAll();

        for (int i=0; i <postResponseDtoList.size(); i++) {
            String title = postResponseDtoList.get(i).getTitle();
            System.out.println("answer : " + title);
        }
    }

    @Test
    void findByTitleContaining() {
        // User생성
        User user = new User();
        user.setId(1L);
        user.setNickname("봉봉");
        userRepository.save(user);


        // Map 생성
        HashMap<Double, Double> map = new HashMap<>();
        map.put(12.312312, 12.123123);

        List<HashMap<Double, Double>> mapArray = new ArrayList<>();
        mapArray.add(map);

        // 댓글 생성
        List<Comment> commentList = new ArrayList<>();

        // Post 생성
        Post post = Post.builder()
                .title("0000000000")
                .content("네네네네반가워요")
                .user(user)
                .category(실외데이트)
                .postMapList(mapArray)
                .commentList(commentList)
                .viewCnt(0)
                .scrapCnt(0)
                .recommendCnt(0)
                .imageUrl("dasdasdasdsa")
                .build();


        Post newPost = postRepository.save(post);

        Post post1 = Post.builder()
                .title("111111111111111")
                .content("네네네네반가워요")
                .user(user)
                .category(실내데이트)
                .postMapList(mapArray)
                .commentList(commentList)
                .viewCnt(0)
                .scrapCnt(0)
                .recommendCnt(0)
                .imageUrl("dasdasdasdsa")
                .build();


        Post newPost1 = postRepository.save(post1);

        Post post2 = Post.builder()
                .title("2222222222")
                .content("네네네네반가워요")
                .user(user)
                .category(실내데이트)
                .postMapList(mapArray)
                .commentList(commentList)
                .viewCnt(0)
                .scrapCnt(0)
                .recommendCnt(0)
                .imageUrl("dasdasdasdsa")
                .build();


        Post newPost2 = postRepository.save(post2);

        // List<PostResponseDto> 타입으로 반환 -> 검색한 제목에 맞는 게시물들 반환

        List<PostResponseDto> postResponseDtoList = postService.findByTitleContaining("2");

        for (int i=0; i <postResponseDtoList.size(); i++) {
            System.out.println("answer : " + postResponseDtoList.get(i).getTitle());
        }
    }

    @Test
    void findAllByOrderByRecommendCntDesc() {
        // User생성
        User user = new User();
        user.setId(1L);
        user.setNickname("봉봉");
        userRepository.save(user);


        // Map 생성
        HashMap<Double, Double> map = new HashMap<>();
        map.put(12.312312, 12.123123);

        List<HashMap<Double, Double>> mapArray = new ArrayList<>();
        mapArray.add(map);

        // 댓글 생성
        List<Comment> commentList = new ArrayList<>();

        // Post 생성
        Post post = Post.builder()
                .title("0000000000")
                .content("네네네네반가워요")
                .user(user)
                .category(실외데이트)
                .postMapList(mapArray)
                .commentList(commentList)
                .viewCnt(0)
                .scrapCnt(0)
                .recommendCnt(100)
                .imageUrl("dasdasdasdsa")
                .build();


        Post newPost = postRepository.save(post);

        Post post1 = Post.builder()
                .title("111111111111111")
                .content("네네네네반가워요")
                .user(user)
                .category(실내데이트)
                .postMapList(mapArray)
                .commentList(commentList)
                .viewCnt(0)
                .scrapCnt(0)
                .recommendCnt(200)
                .imageUrl("dasdasdasdsa")
                .build();


        Post newPost1 = postRepository.save(post1);

        Post post2 = Post.builder()
                .title("2222222222")
                .content("네네네네반가워요")
                .user(user)
                .category(실내데이트)
                .postMapList(mapArray)
                .commentList(commentList)
                .viewCnt(0)
                .scrapCnt(0)
                .recommendCnt(300)
                .imageUrl("dasdasdasdsa")
                .build();


        Post newPost2 = postRepository.save(post2);

        // 추천순에 따라서 정렬되어 List 반환
        List<PostResponseDto> postResponseDtoList = postService.findAllByOrderByRecommendCntDesc();
        for (int i=0; i <postResponseDtoList.size(); i++) {
            System.out.println("answer : "  + postResponseDtoList.get(i).getTitle());
        }
    }

    @Test
    void findByCategory() {
        // User생성
        User user = new User();
        user.setId(1L);
        user.setNickname("봉봉");
        userRepository.save(user);


        // Map 생성
        HashMap<Double, Double> map = new HashMap<>();
        map.put(12.312312, 12.123123);

        List<HashMap<Double, Double>> mapArray = new ArrayList<>();
        mapArray.add(map);

        // 댓글 생성
        List<Comment> commentList = new ArrayList<>();

        // Post 생성
        Post post = Post.builder()
                .title("0000000000")
                .content("네네네네반가워요")
                .user(user)
                .category(실외데이트)
                .postMapList(mapArray)
                .commentList(commentList)
                .viewCnt(0)
                .scrapCnt(0)
                .recommendCnt(0)
                .imageUrl("dasdasdasdsa")
                .build();


        Post newPost = postRepository.save(post);

        Post post1 = Post.builder()
                .title("111111111111111")
                .content("네네네네반가워요")
                .user(user)
                .category(실내데이트)
                .postMapList(mapArray)
                .commentList(commentList)
                .viewCnt(0)
                .scrapCnt(0)
                .recommendCnt(0)
                .imageUrl("dasdasdasdsa")
                .build();


        Post newPost1 = postRepository.save(post1);

        Post post2 = Post.builder()
                .title("2222222222")
                .content("네네네네반가워요")
                .user(user)
                .category(실내데이트)
                .postMapList(mapArray)
                .commentList(commentList)
                .viewCnt(0)
                .scrapCnt(0)
                .recommendCnt(0)
                .imageUrl("dasdasdasdsa")
                .build();


        Post newPost2 = postRepository.save(post2);

        // List<PostResponseDto> 타입으로 반환 -> 카테고리가 "실내 데이터"인 데이터들만 반환되어야 한다.

        List<PostResponseDto> postResponseDtoList = postService.findByCategory(실내데이트);

        for (int i=0; i <postResponseDtoList.size(); i++) {
            String category = postResponseDtoList.get(i).getCategory().toString(); // 카테고리 String으로 변환하기
            System.out.println("answer : " + category + "   " + postResponseDtoList.get(i).getTitle());
        }
    }
}