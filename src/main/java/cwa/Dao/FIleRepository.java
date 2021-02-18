package cwa.Dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class FIleRepository {
    @Autowired
    private JdbcTemplate filetemplate;
    private FileRowMapper fileRowMapper=new FileRowMapper();


}
