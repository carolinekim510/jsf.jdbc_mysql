/* UpdateBean.java */
package com.mysite;

import java.io.Serializable;
import java.sql.*;
import javax.annotation.Resource;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.sql.DataSource;

@Named(value = "updateBean")
@RequestScoped

public class UpdateBean implements Serializable{
    
    @Resource(lookup = "java:jboss/datasources/falconnight")
    private DataSource dp;
    
    Person person = new Person();
    
    public Person getPerson(){
        return person;
    }
    
    public void setPerson(Person person){
        this.person = person;
    }
    
    public void performUpdate() throws SQLException{
        if (dp == null){
            throw new SQLException("Cannot access data pool");
        }
        try (Connection con = dp.getConnection()){
            if (con == null){
                throw new SQLException("Cannot establish connection to database");
            }
            java.util.Date date_now = new java.util.Date();
            java.sql.Date sqlDate = new java.sql.Date(date_now.getTime());
            String sql = "update person "
                    + "set name = ?,"
                    + "nickname = ?,"
                    + "created_date = ? "
                    + "where person_id = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, person.getName());
            stmt.setString(2, person.getNickname());
            stmt.setDate(3, sqlDate);
            stmt.setInt(4, person.getPersonID());
            
            int result = stmt.executeUpdate();
            if (result > 0 ){
                System.out.println("Record Updated");
            } else {
                System.out.println("Record not Updated");
            }
        }
        
    }
    
}
