package ro.zvlad.donate.repo;

import org.springframework.data.util.Pair;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import ro.zvlad.donate.exceptions.GeneralException;
import ro.zvlad.donate.model.Donation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

@Repository
public class DonationRepo {
    private final JdbcTemplate jdbcTemplate;

    public DonationRepo(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Donation insert(Donation donation){
        GeneratedKeyHolder holder = new GeneratedKeyHolder();

        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement statement = con.prepareStatement("INSERT INTO `donation` VALUES (NULL,?,?,?,?,?,?,?,?,NOW())", Statement.RETURN_GENERATED_KEYS);
                statement.setInt(1, donation.getCause_id());
                statement.setInt(2, donation.getAmount());
                statement.setString   (3, donation.getCurrency());
                statement.setString(4, donation.getName());
                statement.setString  (5, donation.getEmail());
                statement.setInt  (6, donation.getStatus());
                statement.setString  (7, donation.getPayment_url());
                statement.setString  (8, donation.getPayment_id());
                return statement;
            }
        }, holder);

        int primaryKey = holder.getKey().intValue();
        donation.setId(primaryKey);
        return donation;
    }

    public boolean delete(int id){
        int result=jdbcTemplate.update("DELETE FROM  `donation` WHERE id = ?", id);
        return result>0?true:false;
    }

    public Donation getById(int id){
        return jdbcTemplate.query("SELECT * FROM `donation` WHERE id = ?", (rs,rowNum) -> {
            final var cause=new Donation(
                    rs.getInt("id"),
                    rs.getInt("cause_id"),
                    rs.getInt("amount"),
                    rs.getString("currency"),
                    rs.getString("name"),
                    rs.getString("email"),
                    rs.getInt("status"),
                    rs.getString("payment_url"),
                    rs.getString("payment_id"),
                    rs.getString("timestamp")
            );
            return cause;
        },id).get(0);
    }

    public List<Donation> getDonations(int causeId,int page){
        int pageSize=100;
        int offset=pageSize*(page-1);
        return jdbcTemplate.query("SELECT * FROM `donation` WHERE cause_id=? order by id desc LIMIT ?,?", (rs, rowNum) -> {
            final var donation=new Donation(
                    rs.getInt("id"),
                    rs.getInt("cause_id"),
                    rs.getInt("amount"),
                    rs.getString("currency"),
                    rs.getString("name"),
                    rs.getString("email"),
                    rs.getInt("status"),
                    rs.getString("payment_url"),
                    rs.getString("payment_id"),
                    rs.getString("timestamp")
            );
            System.out.println(donation.toString());
            return donation;
        },causeId,offset,pageSize);
    }

    public int getDonationsCount(int id){
        return jdbcTemplate.query("SELECT COUNT(*) AS total FROM `donation` WHERE cause_id=? and status=1", (rs, rowNum) -> {
            return rs.getInt(1);
        },id).get(0);
    }

    public Pair<Integer,Integer> getDonationsReport(){
        return jdbcTemplate.query("SELECT COUNT(*) AS total_n, SUM(amount) AS total_s FROM `donation` WHERE status=1", (rs, rowNum) -> {
            return Pair.of(rs.getInt(1), rs.getInt(2));
        }).get(0);
    }

    public Donation save(Donation donation){
        int result=jdbcTemplate.update("UPDATE `donation` SET cause_id=?, amount=?,currency=?,name=?,email=?,status=?,payment_url=?,payment_id=? WHERE id = ?",
                donation.getCause_id(),
                donation.getAmount(),
                donation.getCurrency(),
                donation.getName(),
                donation.getEmail(),
                donation.getStatus(),
                donation.getPayment_url(),
                donation.getPayment_id(),
                donation.getId()
        );
        if(result>0) {
            return donation;
        }else{
            throw new GeneralException("Updating error",500);
        }
    }

}
