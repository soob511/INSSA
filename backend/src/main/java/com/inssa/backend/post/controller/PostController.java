package com.inssa.backend.post.controller;

import com.inssa.backend.post.controller.dto.PostRequest;
import com.inssa.backend.post.controller.dto.PostResponse;
import com.inssa.backend.post.controller.dto.PostsResponse;
import com.inssa.backend.post.service.PostService;
import com.inssa.backend.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    @GetMapping
    public ResponseEntity<List<PostsResponse>> getPosts(Pageable pageable) {
        return ResponseEntity.ok().body(postService.getPosts(pageable));
    }

    @GetMapping("/search")
    public ResponseEntity<List<PostsResponse>> searchPost(@RequestParam String keyword) {
        return ResponseEntity.ok().body(postService.searchPost(keyword));
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostResponse> getPost(@RequestHeader("Authorization") String token, @PathVariable Long postId) {
        return ResponseEntity.ok().body(postService.getPost(JwtUtil.getMemberId(token), postId));
    }

    @PostMapping
    public ResponseEntity<Void> createPost(@RequestHeader("Authorization") String token, @RequestPart PostRequest postRequest, @RequestPart("files") List<MultipartFile> files) {
        postService.createPost(JwtUtil.getMemberId(token), postRequest, files);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/update/{postId}")
    public ResponseEntity<Void> updatePost(@RequestHeader("Authorization") String token, @PathVariable Long postId, @RequestPart PostRequest postRequest, @RequestPart("files") List<MultipartFile> files) {
        postService.updatePost(JwtUtil.getMemberId(token), postId, postRequest, files);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deletePost(@RequestHeader("Authorization") String token, @PathVariable Long postId) {
        postService.deletePost(JwtUtil.getMemberId(token), postId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/like/{postId}")
    public ResponseEntity<Void> createPostLike(@RequestHeader("Authorization") String token, @PathVariable Long postId) {
        postService.createPostLike(JwtUtil.getMemberId(token), postId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/like/{postId}")
    public ResponseEntity<Void> deletePostLike(@RequestHeader("Authorization") String token, @PathVariable Long postId) {
        postService.deletePostLike(JwtUtil.getMemberId(token), postId);
        return ResponseEntity.ok().build();
    }
}
