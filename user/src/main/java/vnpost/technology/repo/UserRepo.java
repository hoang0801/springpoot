package vnpost.technology.repo;

import org.springframework.data.domain.Pageable;
import vnpost.technology.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User,Integer> {

    @Query("SELECT u FROM User u WHERE u.name LIKE :x ")
    Page<User> searchByName(@Param("x") String s, Pageable pageable);
    @Query("SELECT u FROM User u WHERE u.phoneNumber = :phoneNumber")
    Page<User> searchByPhoneNumber(@Param("phoneNumber") String phoneNumber, Pageable pageable);

//    @Query("SELECT u FROM User u " + "WHERE u.createdAt >= :start")
//    Page<User> searchByStartDate(@Param("start") Date start, Pageable pageable);
//
//    @Query("SELECT u FROM User u " + "WHERE u.createdAt <= :end")
//    Page<User> searchByEndDate(@Param("end") Date end, Pageable pageable);
//
//    @Query("SELECT u FROM User u WHERE u.name LIKE :x AND u.createdAt >= :start AND u.createdAt <= :end")
//    Page<User> searchByNameAndDate(@Param("x") String s, @Param("start") Date start, @Param("end") Date end,
//                                   Pageable pageable);
//
//    @Query("SELECT u FROM User u WHERE u.name LIKE :x AND u.createdAt >= :start")
//    Page<User> searchByNameAndStartDate(@Param("x") String s, @Param("start") Date start, Pageable pageable);
//
//    @Query("SELECT u FROM User u WHERE u.name LIKE :x AND u.createdAt <= :end")
//    Page<User> searchByNameAndEndDate(@Param("x") String s, @Param("end") Date end, Pageable pageable);
}
