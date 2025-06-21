package org.example.design.chain;

public class UserNameAndPasswordNotBlank extends Hande{

    @Override
    protected void process(String username, String password) {
        if( username.isBlank() && password.isBlank() ){
            throw new RuntimeException("");
        }
        next.handle(username,password);
    }
}
