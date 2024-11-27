package hongik.cartoonforblindbackend.domain.page.repository;

import hongik.cartoonforblindbackend.domain.book.entity.Book;
import hongik.cartoonforblindbackend.domain.page.entity.Page;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PageRepository extends JpaRepository<Page,Long> {
  List<Page> findByBook_BookId(Long bookId);

  void findByBook_BookIdAndPageNumber(Long bookId, Long pageNumber);
  int countByBook(Book book);
  Optional<Page> findByPageId(Long pageId);
}
