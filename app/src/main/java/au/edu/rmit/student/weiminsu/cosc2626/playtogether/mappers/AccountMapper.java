package au.edu.rmit.student.weiminsu.cosc2626.playtogether.mappers;

import au.edu.rmit.student.weiminsu.cosc2626.playtogether.model.Account;
import au.edu.rmit.student.weiminsu.cosc2626.playtogether.model.Child;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.UUID;

@Mapper
public interface AccountMapper {

    @Results(
        id = "accountResultMap", value = {
            @Result(property = "accountId", column = "accountId"),
            @Result(property = "description", column = "description"),
            @Result(property = "nickname", column = "nickname"),
            @Result(property = "email", column = "email"),
            @Result(property = "suburb", column = "suburb"),
            @Result(property = "profileImage", column = "profileImage"),
            @Result(property = "children", column = "accountId", many = @Many(select = "getChildrenForAccount"))
        }
    )
    @Select("SELECT accountId, description, nickname, email, suburb, profileImageUrl FROM ACCOUNTS WHERE accountId = #{accountId}")
    Account getAccountById(@Param("accountId") UUID accountId);

    @ResultMap("accountResultMap")
    @Select("""
            INSERT INTO ACCOUNTS (accountId, nickname, email, description, suburb) VALUES (
                #{account.accountId}, #{account.nickname}, #{account.email}, #{account.description}, #{account.suburb}
            )
            ON CONFLICT(accountId) DO UPDATE
            SET nickname = #{account.nickname}, email = #{account.email}, description = #{account.description}, suburb = #{account.suburb}
            RETURNING *
        """)
    Account upsertAccount(@Param("account") Account account);

    @Results(
            id = "childResultMap", value = {
            @Result(property = "accountId", column = "accountId"),
            @Result(property = "id", column = "id"),
            @Result(property = "name", column = "name"),
            @Result(property = "age", column = "age")
    }
    )
    @Select("select * from CHILDREN where accountId = #{accountId}")
    List<Child> getChildrenForAccount(@Param("accountId") UUID accountId);

    @ResultMap("childResultMap")
    @Select("""
            <script>
            insert into CHILDREN values
            <foreach collection="children" item="child" separator=",">
            (#{child.id}, #{child.accountId}, #{child.name}, #{child.age})
            </foreach>
            ON CONFLICT(id) DO UPDATE
            SET name = EXCLUDED.name, age = EXCLUDED.age
            RETURNING *
            </script>
        """
    )
    List<Child> upsertChildren(@Param("children") List<Child> children);
}
