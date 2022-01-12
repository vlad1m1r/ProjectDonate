package ro.zvlad.donate.repo;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import ro.zvlad.donate.exceptions.GeneralException;
import ro.zvlad.donate.model.Cause;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CauseRepo {
    private final JdbcTemplate jdbcTemplate;

    public CauseRepo(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Cause insert(Cause cause){
        GeneratedKeyHolder holder = new GeneratedKeyHolder();

        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement statement = con.prepareStatement("INSERT INTO `cause` VALUES (NULL,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
                statement.setString(1, cause.getName());
                statement.setString(2, cause.getDescription());
                statement.setInt   (3, cause.getTarget_amount());
                statement.setString(4, cause.getCurrency());
                statement.setString  (5, cause.getCreated_on());
                statement.setString  (6, cause.getEnding_on());
                return statement;
            }
        }, holder);

        int primaryKey = holder.getKey().intValue();
        cause.setId(primaryKey);
        return cause;
    }

    public boolean delete(int id){
        int result=jdbcTemplate.update("DELETE FROM  `cause` WHERE id = ?", id);
        return result>0?true:false;
    }

    public Cause getById(int id){
        return jdbcTemplate.query("SELECT * FROM `cause` WHERE id = ?", (rs,rowNum) -> {
            final var cause=new Cause(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("description"),
                    rs.getInt("target_amount"),
                    rs.getString("currency"),
                    rs.getString("created_on"),
                    rs.getString("ending_on")
            );
            return cause;
        },id).get(0);
    }

    public List<Cause> getCauses(int page){
        int pageSize=18;
        int offset=pageSize*(page-1);
        return jdbcTemplate.query("SELECT * FROM `cause` WHERE created_on <= NOW() and ending_on > NOW() LIMIT ?,?", (rs, rowNum) -> {
            final var cause=new Cause(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("description"),
                    rs.getInt("target_amount"),
                    rs.getString("currency"),
                    rs.getString("created_on"),
                    rs.getString("ending_on")
            );
            System.out.println(cause.toString());
            return cause;
        },offset,pageSize);
    }

    public int getCausesCount(){
        return jdbcTemplate.query("SELECT COUNT(*) AS total FROM `cause` WHERE created_on <= NOW() and ending_on > NOW()", (rs, rowNum) -> {
            return rs.getInt(1);
        }).get(0);
    }

    public int getCausesTotalAmount(int id){
        return jdbcTemplate.query("SELECT SUM(amount) AS total FROM `donation` WHERE status=1 AND cause_id=?",(rs, rowNum) -> {
            return rs.getInt(1);
        },id).get(0);
    }

    public Cause save(Cause cause){
        int result=jdbcTemplate.update("UPDATE `cause` SET name=?, description=?,target_amount=?,currency=?,created_on=?,ending_on=? WHERE id = ?",
                cause.getName(),
                cause.getDescription(),
                cause.getTarget_amount(),
                cause.getCurrency(),
                cause.getCreated_on(),
                cause.getEnding_on(),
                cause.getId()
        );
        if(result>0) {
            return cause;
        }else{
            throw new GeneralException("Updating error",500);
        }
    }

}
