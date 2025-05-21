package com.rookies3.myspringbootlab.repository;

import com.rookies3.myspringbootlab.entity.Publisher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class PublisherRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private PublisherRepository publisherRepository;

    private Publisher publisher;

    @BeforeEach
    void setUp() {
        publisher = Publisher.builder()
                .name("Penguin Random House")
                .establishedDate(LocalDate.of(2013, 7, 1))
                .address("1745 Broadway, New York, NY")
                .build();
        entityManager.persistAndFlush(publisher);
    }

    @Test
    void findByName_ShouldReturnPublisher() {
        // When
        Optional<Publisher> found = publisherRepository.findByName("Penguin Random House");

        // Then
        assertThat(found).isPresent();
        assertThat(found.get().getName()).isEqualTo("Penguin Random House");
        assertThat(found.get().getEstablishedDate()).isEqualTo(LocalDate.of(2013, 7, 1));
        assertThat(found.get().getAddress()).isEqualTo("1745 Broadway, New York, NY");
    }

    @Test
    void findByName_ShouldReturnEmpty_WhenNotFound() {
        // When
        Optional<Publisher> found = publisherRepository.findByName("Non-Existent Publisher");

        // Then
        assertThat(found).isEmpty();
    }

    @Test
    void existsByName_ShouldReturnTrue() {
        // When
        boolean exists = publisherRepository.existsByName("Penguin Random House");

        // Then
        assertThat(exists).isTrue();
    }

    @Test
    void existsByName_ShouldReturnFalse() {
        // When
        boolean exists = publisherRepository.existsByName("Non-Existent Publisher");

        // Then
        assertThat(exists).isFalse();
    }

    @Test
    void findByIdWithBooks_ShouldReturnPublisher() {
        // When
        Optional<Publisher> found = publisherRepository.findByIdWithBooks(publisher.getId());

        // Then
        assertThat(found).isPresent();
        assertThat(found.get().getName()).isEqualTo("Penguin Random House");
        // books 컬렉션이 초기화되었는지 확인
        assertThat(found.get().getBooks()).isNotNull();
    }
}