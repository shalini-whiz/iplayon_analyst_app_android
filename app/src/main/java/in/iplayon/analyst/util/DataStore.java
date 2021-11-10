package in.iplayon.analyst.util;

import java.io.Serializable;

/**
 * Created by shalinibr on 7/27/17.
 */

public class DataStore implements Serializable {

    private static DataStore instance;

    // Global variable
    private String data;

    // Restrict the constructor from being instantiated
    private DataStore(){

    }


    public void setData(String data){
        this.data=data;
    }
    public String getData(){
        return this.data;
    }

    public static synchronized DataStore getInstance(){
        if(instance==null){
            instance=new DataStore();
        }
        return instance;
    }
}
