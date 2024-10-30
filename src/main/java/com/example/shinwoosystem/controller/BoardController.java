package com.example.shinwoosystem.controller;

import com.example.shinwoosystem.models.entity.Board;
import com.example.shinwoosystem.dao.repository.BoardRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class BoardController {

//    private String saveTitle = "";
//    private String saveWriter = "";
//    private String saveContent = "";
//
//    private List<String> saveTitleList = new ArrayList<>();
//    private List<String> saveWriterList = new ArrayList<>();
//    private List<String> saveContentList = new ArrayList<>();
    private final BoardRepository boardRepository;
    private int page;

    public BoardController(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    private List<Board> saveBoardList = new ArrayList<>();

    @GetMapping("/board-list")
    public String boardList(Model model) {
//    public String boardList(@RequestParam(defaultValue = "0") int page, Model model) {
//        Pageable pageable = PageRequest.of(page, 10); // 페이지 번호와 페이지 크기 설정
//        Page<Board> boardPage = boardRepository.findAll(pageable); // 게시글을 페이지네이션하여 가져옴
//        model.addAttribute("totalPages", boardPage.getTotalPages());
//        model.addAttribute( "title", saveTitle);
//        model.addAttribute( "writer", saveWriter);
//        model.addAttribute( "content", saveContent);
//        model.addAttribute( "boardList", saveBoardList);

        List<Board> boardList = boardRepository.findAllOrderByIdDesc();
        model.addAttribute( "boardList", boardList);

        return "board-list";
    }

//    @GetMapping("/board/1")
//    public String boardOne() {
//        return "board-detail.css";
//    }
//
//    @GetMapping("/board/2")
//    public String boardTwo() {
//        return "board-detail.css";
//    }

//    @PostMapping("/board/{id}")
//    public String boardDetailpath(@PathVariable int id, Model model) {
//        model.addAttribute("id", id);
//        return "board-detail";
//    }

    @GetMapping("/board-detail/{id}")
    public String boardDetailView(@PathVariable int id, Model model) {
        // 데이터베이스에서 게시글 찾기
        Board board = boardRepository.findById(id).orElse(null);
        
        model.addAttribute("board", board);

        return "board-detail";
    }

    @PostMapping("/board-detail/{id}")
    public String boardDetail(@RequestParam int id, Model model) {
        // 데이터베이스에서 게시글 찾기
        Board board = boardRepository.findById(id).orElse(null);

        // 게시글이 존재하면 모델에 추가
        if (board != null) {
//            model.addAttribute("id", board.getid());
            model.addAttribute("title", board.getTitle());
            model.addAttribute("writer", board.getWriter());
            model.addAttribute("content", board.getContent());
        } else {
            // 게시글이 없을 경우 처리 (예: 에러 페이지로 리다이렉트)
            return "error"; // 또는 적절한 에러 페이지로
        }

        return "board-detail"; // board-detail.html을 반환
    }

    @GetMapping("/board-insert")
    public String boardInsert() {
        return "board-insert";
    }

    @PostMapping("/board/insert")
    private String boardInsertData(String title, String writer, String content) {
//        saveTitle = title;
//        saveWriter = writer;
//        saveContent = content;
//
//        saveTitleList.add(title);

        Board board = new Board();
        board.setTitle(title);
        board.setWriter(writer);
        board.setContent(content);

//        saveBoardList.add(board);
        boardRepository.save(board);

        return "redirect:/board-list";
    }

//    @GetMapping("/board-update")
//    public String boardUpdate() {
//        return "board-update";
//    }

    @GetMapping("/board-update/{id}")
    public String boardUpdateView(@PathVariable int id, Model model) {
        // 데이터베이스에서 게시글 찾기
        Board board = boardRepository.findById(id).orElse(null);

        if (board != null) {
            // 게시글이 존재하면 모델에 추가
            model.addAttribute("board", board);
        } else {
            // 게시글이 없을 경우 처리 (예: 에러 페이지로 리다이렉트)
            return "error"; // 또는 적절한 에러 페이지로
        }
//        model.addAttribute("id", id);
//        model.addAttribute("title", saveTitleList.get(id));
//        model.addAttribute("writer", saveWriterList.get(id));
//        model.addAttribute("content", saveContentList.get(id));
        return "board-update";
    }

    @PostMapping("/board-update")
    public String boardUpdate(int id, String title, String writer, String content) {
        // 데이터베이스에서 게시글 찾기
        Board board = boardRepository.findById(id).orElse(null);

        if (board != null) {
            // 게시글이 존재하면 수정
            board.setTitle(title);
            board.setWriter(writer);
            board.setContent(content);

            // 변경 사항 저장
            boardRepository.save(board);
        }
//        saveTitleList.set(id, title);
//        saveWriterList.set(id, writer);
//        saveContentList.set(id, content);
        return "redirect:/board-list";
    }

    @GetMapping("/board-delete/{id}")
    public String boardDeleteView(@PathVariable int id, Model model) {
        // 데이터베이스에서 게시글 찾기
        Board board = boardRepository.findById(id).orElse(null);

        if (board != null) {
            // 게시글이 존재하면 모델에 추가
            model.addAttribute("board", board);
        } else {
            // 게시글이 없을 경우 처리 (예: 에러 페이지로 리다이렉트)
            return "error"; // 또는 적절한 에러 페이지로
        }

        return "board-delete";
    }

    // 게시물 삭제
    @PostMapping("/board-delete/{id}")
    public String boardDelete(@PathVariable int id) {
        // 데이터베이스에서 게시글 삭제
        boardRepository.deleteById(id);
//        saveTitleList.remove(id);
//        saveWriterList.remove(id);
//        saveContentList.remove(id);
        return "redirect:/board-list"; // 목록으로 리다이렉트
    }
}