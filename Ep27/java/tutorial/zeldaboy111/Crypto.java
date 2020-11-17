package tutorial.zeldaboy111;

public class Crypto {
    public static byte[] encrypt(String message) { return encrypt(message.getBytes()); }
    public static byte[] decrypt(String message) { return decrypt(message.getBytes()); }
    
    public static byte[] encrypt(byte[] message) {
        if(message == null || message.length < 1) return null;
        int length = message.length;
        byte[] encrypted = encryption1(length, message);
        encrypted = reverse(length, encrypted);

        return encrypted;
    }
    public static byte[] decrypt(byte[] message) {
        if(message == null || message.length < 1) return null;
        int length = message.length;
        byte[] decrypted = reverse(length, message);
        decrypted = decryption1(length, decrypted);

        return decrypted;
    }
    private static byte[] encryption1(int length, byte[] encrypted) {
        for(int i = 0; i < length; i++) {
            if(i % 3 == 0) {
                encrypted[i] = (byte)(encrypted[i]+1);
                if(i % 6 == 0) encrypted[i] = (byte)(encrypted[i]-2);
                else encrypted[i] = (byte)(encrypted[i]+1);
            }
            else if(i % 4 == 0) encrypted[i] = (byte)(encrypted[i]+2);
            else if(i % 5 == 0) encrypted[i] = (byte)(encrypted[i]-3);
        }
        return encrypted;
    }
    private static byte[] reverse(int length, byte[] encrypted) {
        byte[] temp = new byte[length];
        for(int i = 0; i < length; i++) { temp[length-i - 1] = encrypted[i]; }
        return temp;
    }

    private static byte[] decryption1(int length, byte[] encrypted) {
        for(int i = 0; i < length; i++) {
            if(i % 3 == 0) {
                encrypted[i] = (byte)(encrypted[i]-1);
                if(i % 6 == 0) encrypted[i] = (byte)(encrypted[i]+2);
                else encrypted[i] = (byte)(encrypted[i]-1);
            } else if(i % 4 == 0) encrypted[i] = (byte)(encrypted[i]-2);
            else if(i % 5 == 0) encrypted[i] = (byte)(encrypted[i]+3);
        }
        return encrypted;
    }

}
