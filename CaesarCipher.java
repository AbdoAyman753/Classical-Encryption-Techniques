package sample;

public class CaesarCipher {
    public CaesarCipher() {
    }

    public String Encrypt(String text, int s) {
        StringBuffer result = new StringBuffer();

        for(int i = 0; i < text.length(); ++i) {
            char ch;
            if (Character.isUpperCase(text.charAt(i))) {
                ch = (char)((text.charAt(i) + s - 65) % 26 + 65);
                result.append(ch);
            } else {
                ch = (char)((text.charAt(i) + s - 97) % 26 + 97);
                result.append(ch);
            }
        }

        String out = result.toString();
        return out;
    }
}