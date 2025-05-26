package com.jb.todo_list_springboot.repo;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.jb.todo_list_springboot.model.Task;
@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByUsername(String username);
    List<Task> findByStatus(String status);
}

