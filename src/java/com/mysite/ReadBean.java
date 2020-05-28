/* ReadBean.java */
package com.mysite;

import java.io.Serializable;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.sql.DataSource;

@Named(value = "readBean")
@RequestScoped

public class ReadBean implements Serializable{
    
    @Resource(lookup = "java:jboss/datasources/falconnight")
    private DataSource dp;
    
    public List<Person> performRead() throws SQLException{
        if (dp == null){
            throw new SQLException("Cannot access data pool");
        }
        List<Person> list;
        try (Connection con = dp.getConnection()){
            if (con == null){
                throw new SQLException("Cannot establish connection to database");
            }
            PreparedStatement ps = con.prepareStatement("select person_id, name, nickname, created_date from Person");
            ResultSet result = ps.executeQuery();
            list = new ArrayList<>();
            while (result.next()){
                Person per = new Person();
                per.setPersonID(result.getInt("person_id"));
                per.setName(result.getString("name"));
                per.setNickname(result.getString("nickname"));
                per.setCreated_date(result.getDate("created_date"));
                list.add(per);
            }
        }
        return list;
    }
    
}
