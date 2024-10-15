package hongik.cartoonforblindbackend.domain.book.repository;

import hongik.cartoonforblindbackend.domain.book.entity.Book;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book,Long> {

  Optional<Book> findByBookId(Long bookId);

}
