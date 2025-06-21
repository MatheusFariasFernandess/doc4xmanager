package org.example.design.chain;

public class RightUserName extends Hande{
    @Override
    protected void process(String username, String password) {
        if(!username.equals("MATHEUS")){
            throw new RuntimeException("");
        }
        next.handle(username,password);
    }
}
