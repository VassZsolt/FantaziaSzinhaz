package coder;

import org.jetbrains.annotations.NotNull;

/**
 * This is an implementation of Caesar cipher using different offsetNumber for every character.
 */

public class coder {
    private String password;
    private int offsetNumber; //This is the key for encoding or decoding.

    public coder(){

    }

    /**
     * Encode the password using Caesar cipher with different offsetNumber.
     * The key (offsetNumber) is the sum of (@param decodedPassword) length + (@code encodedPassword) length.
     *
     * @param decodedPassword the original decoded password.
     * @return the encoded version of the password.
     */

    public String encode(@NotNull String decodedPassword){
        this.password=decodedPassword;
        StringBuilder encodedPassword = new StringBuilder();
        char character;

        for (int i=0; i<password.length();i++){
            this.offsetNumber=password.length()-encodedPassword.length();
            character = (char) (password.charAt(i)+offsetNumber);
            encodedPassword.append(character);
        }
        return encodedPassword.toString();
    }


    /**
     * Decode the password using Caesar cipher with different offsetNumber.
     * The key (offsetNumber) is the difference of (@param encodedPassword) length - (@code decodedPassword) length.
     *
     * @param encodedPassword the encoded password.
     * @return the original version of the password.
     */

    public String decode(@NotNull String encodedPassword){
        this.password=encodedPassword;
        StringBuilder decodedPassword = new StringBuilder();
        char character;

        for (int i=0; i<password.length();i++){
            this.offsetNumber=password.length()-decodedPassword.length();
            character = (char) (password.charAt(i)-offsetNumber);
            decodedPassword.append(character);
        }
        return decodedPassword.toString();
    }
}
//end of code