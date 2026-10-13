package com.dtn2603.buoi2.repository;

import com.dtn2603.buoi2.entity.Account;
import com.dtn2603.buoi2.entity.Group;
import com.dtn2603.buoi2.entity.GroupAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IGroupAccountRepository extends JpaRepository<GroupAccount, Integer> {

    @Query("""
        SELECT ga.group
        FROM GroupAccount ga
        WHERE ga.account.accountId = :accountId
    """)
    List<Group> findGroupsByAccountId( @Param("accountId") Integer accountId);


    @Query("""
        SELECT ga.account
        FROM GroupAccount ga
        WHERE ga.group.groupId = :groupId
    """)
    List<Account> findAccountsByGroupId(@Param("groupId") Integer groupId);

    boolean existsByGroupGroupIdAndAccountAccountId(Integer groupId, Integer accountId);
}
