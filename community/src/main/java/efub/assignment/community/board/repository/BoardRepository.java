package efub.assignment.community.board.repository;

import efub.assignment.community.board.domain.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {
    Boolean existsByBoardIdAndMember_MemberId(Long board_id, Long member_id);
}
