package efub.assignment.community.post.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import efub.assignment.community.board.domain.QBoard;
import efub.assignment.community.member.domain.QMember;
import efub.assignment.community.post.domain.Post;
import efub.assignment.community.post.domain.QPost;

import java.util.List;

public class CustomPostRepositoryImpl implements CustomPostRepository {
    private final JPAQueryFactory queryFactory;

    public CustomPostRepositoryImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    @Override
    public List<Post> search(String content, String writerNickname, String boardName) {
        QPost qPost = QPost.post;
        QMember qMember = QMember.member;
        QBoard qBoard = QBoard.board;

        BooleanBuilder builder = new BooleanBuilder();

        // 작성자 조건 추가
        if (writerNickname != null && !writerNickname.isEmpty()) {
            builder.and(qPost.member.nickname.eq(writerNickname));
        }

        // 내용 조건 추가
        if (content != null && !content.isEmpty()) {
            builder.and(qPost.content.containsIgnoreCase(content));
        }

        // 게시판 이름 조건 추가
        if (boardName != null && !boardName.isEmpty()) {
            builder.and(qPost.board.boardName.eq(boardName));
        }

        return queryFactory
                .selectFrom(qPost)
                .join(qPost.member, qMember).fetchJoin()
                .join(qPost.board, qBoard).fetchJoin()
                .where(builder)
                .fetch();
    }


}
