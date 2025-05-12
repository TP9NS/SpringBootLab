### [실습2-4] Spring Boot와 JPA(Java Persistence API) 활용
* Entity,Repository,RepositoryTest
* Book과 BookDetail 1:1 OneToOne 연관관계
  * FetchType.Lazy vs FetchType.EAGER
  * @JoinColumn, mappedBy
  * 연관관계의 주인(owner) 와 종속(non-onwer)
  * Owner(BookDetail), Non-owner(Book)
  * FK(외래키) 가진쪽이 Owner
* Service
* DTO(Data Transfer Object)
* Controller