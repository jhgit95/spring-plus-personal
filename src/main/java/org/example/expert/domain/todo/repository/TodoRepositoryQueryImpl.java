package org.example.expert.domain.todo.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.example.expert.domain.comment.entity.QComment;
import org.example.expert.domain.manager.entity.QManager;
import org.example.expert.domain.todo.dto.response.TodoSearchResponse;
import org.example.expert.domain.todo.entity.QTodo;
import org.example.expert.domain.todo.entity.Todo;
import org.example.expert.domain.user.entity.QUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.example.expert.domain.todo.entity.QTodo.todo;
import static org.example.expert.domain.user.entity.QUser.user;

@Repository
@RequiredArgsConstructor
public class TodoRepositoryQueryImpl implements TodoRepositoryQuery{
    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<Todo> findByIdWithUserDsl(Long todoId){
        return Optional.ofNullable(queryFactory
                .select(todo)
                .from(todo)
                .join(todo.user, user)
                .fetchJoin()
                .where(
                        todoIdEq(todoId)
                )
                .fetchOne());
    };

    private BooleanExpression todoIdEq(Long todoId) {
        return todoId != null ? todo.id.eq(todoId) : null;
    }

    @Override
    public Page<TodoSearchResponse> searchTodo(Pageable pageable, String title, LocalDateTime startDateTime, LocalDateTime endDateTime, String nickname){
        QTodo todo = QTodo.todo;
        QManager manager = QManager.manager;
        QComment comment = QComment.comment;
        QUser user = QUser.user;

        List<Todo> todos = queryFactory
                .select(todo)
                .from(todo)
                .leftJoin(todo.managers, manager)
                .leftJoin(todo.comments, comment)
                .leftJoin(todo.user, user)
                .offset(pageable.getOffset())
                .where(
                        titleContains(title),
                        userNicknameContains(nickname),
                        todoDateBetween(startDateTime, endDateTime)
                )
                .groupBy(todo.id)
                .orderBy(todo.createdAt.desc())
                .limit(pageable.getPageSize())
                .fetch();

        List<TodoSearchResponse> dtoList = todos.stream()
                .map(t -> new TodoSearchResponse(
                        t.getTitle(),
                        t.getManagers().size(),
                        t.getComments().size()))
                .collect(Collectors.toList());

        return new PageImpl<>(dtoList, pageable, dtoList.size());

    }

    private BooleanExpression titleContains(String titleKeyword) {
        return titleKeyword != null ? todo.title.contains(titleKeyword) : null;
    }

    private BooleanExpression userNicknameContains(String managerNickname) {
        return managerNickname != null ? user.nickname.contains(managerNickname) : null;
    }

    private BooleanExpression todoDateBetween(LocalDateTime startDate, LocalDateTime endDate) {
        return startDate != null && endDate != null ? todo.createdAt.between(startDate, endDate) : null;
    }

}
