package org.example.expert.domain.todo.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.example.expert.domain.todo.entity.Todo;
import org.springframework.stereotype.Repository;

import java.util.Optional;

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

}
