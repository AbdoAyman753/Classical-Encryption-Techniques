package sample;

public class VigenereCipher {
    public VigenereCipher() {
    }

    public String generateKey(String str, String key, boolean mode) {
        int x = str.length();
        int i;
        if (mode && key.length() < str.length()) {
            i = 0;

            while(true) {
                if (x == i) {
                    i = 0;
                }

                if (key.length() == str.length()) {
                    break;
                }

                key = key + str.charAt(i);
                ++i;
            }
        } else {
            i = 0;

            while(true) {
                if (x == i) {
                    i = 0;
                }

                if (key.length() == str.length()) {
                    break;
                }

                key = key + key.charAt(i);
                ++i;
            }
        }

        return key;
    }

    public String Encrypt(String str, String key, boolean mode) {
        str = str.toLowerCase();
        key = key.toLowerCase();
        String cipher_text = "";
        key = this.generateKey(str, key, mode);

        for(int i = 0; i < str.length(); ++i) {
            int x = (str.charAt(i) + key.charAt(i) - 194) % 26;
            x += 65;
            cipher_text = cipher_text + (char)x;
        }

        return cipher_text;
    }
}