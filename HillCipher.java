package sample;

public class HillCipher {
    public HillCipher() {
    }

    public int[][] stringToMatrix(String text) {
        int size = 0;
        int tempCount = 0;
        char[] var6 = text.toCharArray();
        int count1 = var6.length;

        for(int var8 = 0; var8 < count1; ++var8) {
            char bracket = var6[var8];
            if (bracket == '{') {
                ++size;
            }
        }

        --size;
        text = text.replace("{", "");
        text = text.replace("}", "");
        int[][] Matrix = new int[size][size];
        String[] temp = new String[size];
        temp = text.split(",");

        for(int count = 0; count < size; ++count) {
            for(count1 = 0; count1 < size; ++count1) {
                Matrix[count][count1] = Integer.parseInt(temp[tempCount++]);
            }
        }

        return Matrix;
    }

    public void MatrixMul(int[][] cipherMatrix, int[][] keyMatrix, int[][] messageVector) {
        for(int i = 0; i < keyMatrix.length; ++i) {
            for(int j = 0; j < 1; ++j) {
                cipherMatrix[i][j] = 0;

                for(int x = 0; x < keyMatrix.length; ++x) {
                    cipherMatrix[i][j] += keyMatrix[i][x] * messageVector[x][j];
                }

                cipherMatrix[i][j] %= 26;
            }
        }

    }

    public String Encrypt(String message, String key) {
        message = message.toLowerCase();
        key = key.toLowerCase();
        int[][] keyMatrix = this.stringToMatrix(key);
        int[][] messageVector = new int[keyMatrix.length][1];
        int[][] cipherMatrix = new int[keyMatrix.length][1];
        String CipherText = "";

        for(int count = 0; count < message.length() / keyMatrix.length; ++count) {
            int i;
            for(i = 0; i < keyMatrix.length; ++i) {
                messageVector[i][0] = message.charAt(2 * count + i) % 65;
            }

            this.MatrixMul(cipherMatrix, keyMatrix, messageVector);

            for(i = 0; i < keyMatrix.length; ++i) {
                CipherText = CipherText + (char)(cipherMatrix[i][0] + 65);
            }
        }

        return CipherText;
    }
}