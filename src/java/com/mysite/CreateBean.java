/* CreateBean.java */
package com.mysite;

import java.io.Serializable;
import java.sql.*;
import javax.annotation.Resource;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.sql.DataSource;

@Named(value = "createBean")
@RequestScoped

public class CreateBean implements Serializable{
    
    @Resource(lookup = "java:jboss/datasources/falconnight")
    private DataSource dp;
    
    Person person = new Person();
    
    public Person getPerson(){
        return person;
    }
    
    public void setPerson(Person person){
        this.person = person;
    }
    
    public void performCreate() throws SQLException{
        if (dp == null){
            throw new SQLException("Cannot access data pool");
        }
        try (Connection con = dp.getConnection()){
            if(con == null){
                throw new SQLException("Cannot establish connection to database");
            }
            
            java.util.Date date_now = new java.util.Date();
            java.sql.Date sqlDate = new java.sql.Date(date_now.getTime());
            String sql = "insert into person values (NULL, ?, ?, ?)";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, person.getName());
            stmt.setString(2, person.getNickname());
            stmt.setDate(3, sqlDate);
            
            int result = stmt.executeUpdate();
            if(result > 0){
                System.out.println(" Record Inserted");
            } else {
                System.out.println(" Record not Inserted");
            }
        }
    }
}
