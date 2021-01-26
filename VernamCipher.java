package sample;

public class VernamCipher {
    public VernamCipher() {
    }

    public String Encrypt(String plainText, String key) {
        key = key.toLowerCase();
        plainText = plainText.toLowerCase();
        String cipher_text = "";

        for(int i = 0; i < plainText.length(); ++i) {
            int x = (plainText.charAt(i) + key.charAt(i) - 194) % 26;
            x += 65;
            cipher_text = cipher_text + (char)x;
        }

        return cipher_text;
    }
}