package org.geekbang.time.commonmistakes.nosqluse.esvsmyql;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NewsMySQLRepository extends JpaRepository<News, Long> {
    long countByCateidAndContentContainingAndContentContaining(int cateid, String keyword1, String keyword2);
}
