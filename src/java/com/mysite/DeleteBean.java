/* DeleteBean.java */
package com.mysite;

import java.io.Serializable;
import java.sql.*;
import javax.annotation.Resource;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.sql.DataSource;

@Named(value = "deleteBean")
@RequestScoped

public class DeleteBean implements Serializable{
    
    @Resource(lookup = "java:jboss/datasources/falconnight")
    private DataSource dp;
    
    Person person = new Person();
    
    public Person getPerson(){
        return person;
    }
    
    public void setPerson(Person person){
        this.person = person;
    }
    
    public void performDelete() throws SQLException{
        if (dp == null){
            throw new SQLException("Cannot access data pool");
        }
        try (Connection con = dp.getConnection()){
            if(con == null){
                throw new SQLException("Cannot establish conncection to database");
            }
            
            String sql = "delete from person where person_id = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, person.getPersonID());
            
            int result = stmt.executeUpdate();
            if (result > 0){
                System.out.println("Record Deleted");
            } else {
                System.out.println("Record not Deleted");
            }
        }
    }
}
