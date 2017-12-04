package com.database;

final public class Connection {
    private static Connection instance = new Connection();
    static{

    }

    private Connection(){

    }

    public static Connection getDBConnection(){
        return instance;
    }
}
