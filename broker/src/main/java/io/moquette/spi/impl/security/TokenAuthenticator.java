package io.moquette.spi.impl.security;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import io.moquette.spi.security.IAuthenticator;

public class TokenAuthenticator implements IAuthenticator, ITokenGenerator {
	private static final String KEY = "testim";
	
    public static void main(String[] args) throws IOException {
    	TokenAuthenticator authenticator = new TokenAuthenticator();
    	String strToken = authenticator.generateToken("hello");
    	if (authenticator.checkValid(null, null, strToken.getBytes())) {
			System.out.println("pass" + strToken);
		} else {
			System.out.println("fail" + strToken);
		}
    }
	
	@Override
	public boolean checkValid(String clientId, String username, byte[] password) {
		// TODO Auto-generated method stub
		byte[] decryResult;
		try {
			password = Base64.getDecoder().decode(password);
			decryResult = DES.decrypt(password);
			String signKey = new String(decryResult);
			System.out.println("The signkey is " + signKey);
        
			if (signKey.startsWith(KEY + "|")) {
				signKey = signKey.substring(KEY.length() + 1);
				long timestamp = Long.parseLong(signKey.substring(0, signKey.indexOf('|')));
				if (System.currentTimeMillis() - timestamp > 7 * 24 * 60 *60 *1000) {
					//return false;
				}
				String id = signKey.substring(signKey.indexOf('|') + 1);
				if (id.equals(username)) {
					return true;
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return false;
	}

	@Override
	public String generateToken(String username) {
		String signKey = KEY + "|" + (System.currentTimeMillis()) + "|" + username;
		try {
			return Base64.getEncoder().encodeToString(DES.encrypt(signKey.getBytes()));
		} catch (InvalidKeyException | NoSuchAlgorithmException | InvalidKeySpecException | NoSuchPaddingException
				| IllegalBlockSizeException | BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
