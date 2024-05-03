package efub.assignment.community.board.service;

import efub.assignment.community.board.domain.Board;
import efub.assignment.community.board.dto.board.BoardRequestDto;
import efub.assignment.community.board.repository.BoardRepository;
import efub.assignment.community.exception.CustomDeleteException;
import efub.assignment.community.exception.ErrorCode;
import efub.assignment.community.member.domain.Member;
import efub.assignment.community.member.service.MemberService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
@Transactional
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final MemberService memberService;

    public Board createBoard(BoardRequestDto dto) {
        Member member = memberService.findMemberById(Long.parseLong(dto.getMemberId()));
        Board board = dto.toEntity(member);
        Board savedBoard = boardRepository.save(board);
        return savedBoard;
    }

    @Transactional(readOnly=true)
    public Board findBoardById(Long boardId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new EntityNotFoundException("해당 id를 가진 게시판을 찾을 수 없습니다. id =" + boardId));
        return board;
    }

    public Long updateBoard(Long boardId, BoardRequestDto dto) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new EntityNotFoundException("해당 id를 가진 게시판을 찾을 수 없습니다. id =" + boardId));
        Member member = memberService.findMemberById(Long.parseLong(dto.getMemberId()));
        board.update(member);
        return board.getBoardId();
    }

    public void deleteBoard(Long boardId, Long memberId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new EntityNotFoundException("해당 id를 가진 게시판을 찾을 수 없습니다. id =" + boardId));
        if (memberId!=board.getMember().getMemberId()){
            throw new CustomDeleteException(ErrorCode.PERMISSION_REJECTED_USER);
        }
        boardRepository.delete(board);
    }
}
