package efub.assignment.community.board.controller;

import efub.assignment.community.board.domain.Board;
import efub.assignment.community.board.dto.board.BoardRequestDto;
import efub.assignment.community.board.dto.board.BoardResponseDto;
import efub.assignment.community.board.service.BoardService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/boards")
public class BoardController {
    private final BoardService boardService;
    //게시판 생성
    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public BoardResponseDto createBoard(@RequestBody @Valid final BoardRequestDto dto){
        Board savedBoard = boardService.createBoard(dto);
        return BoardResponseDto.from(savedBoard);
    }
    //게시판 조회
    @GetMapping("/{boardId}")
    @ResponseStatus(value = HttpStatus.OK)
    public BoardResponseDto getBoard(@PathVariable final Long boardId){
        Board findBoard = boardService.findBoardById(boardId);
        return BoardResponseDto.from(findBoard);
    }
    //게시판 수정
    @PutMapping("/{boardId}")
    @ResponseStatus(value = HttpStatus.OK)
    public BoardResponseDto updateBoard(@PathVariable final Long boardId, @RequestBody @Valid final BoardRequestDto dto){
        Long id = boardService.updateBoard(boardId, dto);
        Board board = boardService.findBoardById(id);
        return BoardResponseDto.from(board);
    }

    //게시판 삭제
    @DeleteMapping("/{boardId}")
    @ResponseStatus(value = HttpStatus.OK)
    public String deleteBoard(@PathVariable final Long boardId, @RequestParam(name = "memberId") Long memberId){
        boardService.deleteBoard(boardId, memberId);
        return "성공적으로 게시판이 삭제되었습니다.";
    }




}
