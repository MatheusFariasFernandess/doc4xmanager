package org.example.design.chain;

public class ConnectDataBase extends Hande{
    @Override
    protected void process(String username, String password) {
        if(username!=null){
            System.out.println("CONNECTED");
            return;
        }
        throw new RuntimeException("ASDA");
    }
}
