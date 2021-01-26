package sample;

public class PlayfairCipher {
    private String KeyWord = new String();
    private String Key = new String();
    private char[][] matrix_arr = new char[5][5];

    public PlayfairCipher() {
    }

    public void setKey(String k) {
        String K_adjust = new String();
        boolean flag = false;
        K_adjust = K_adjust + k.charAt(0);

        for(int i = 1; i < k.length(); ++i) {
            for(int j = 0; j < K_adjust.length(); ++j) {
                if (k.charAt(i) == K_adjust.charAt(j)) {
                    flag = true;
                }
            }

            if (!flag) {
                K_adjust = K_adjust + k.charAt(i);
            }

            flag = false;
        }

        this.KeyWord = K_adjust;
    }

    public void KeyGen() {
        boolean flag = true;
        this.Key = this.KeyWord;

        for(int i = 0; i < 26; ++i) {
            char current = (char)(i + 97);
            if (current != 'j') {
                for(int j = 0; j < this.KeyWord.length(); ++j) {
                    if (current == this.KeyWord.charAt(j)) {
                        flag = false;
                        break;
                    }
                }

                if (flag) {
                    this.Key = this.Key + current;
                }

                flag = true;
            }
        }

        System.out.println(this.Key);
        this.matrix();
    }

    private void matrix() {
        int counter = 0;

        for(int i = 0; i < 5; ++i) {
            for(int j = 0; j < 5; ++j) {
                this.matrix_arr[i][j] = this.Key.charAt(counter);
                System.out.print(this.matrix_arr[i][j] + " ");
                ++counter;
            }

            System.out.println();
        }

    }

    private String format(String old_text) {
        int i = false;
        int len = false;
        String text = new String();
        int len = old_text.length();

        for(int tmp = 0; tmp < len; ++tmp) {
            if (old_text.charAt(tmp) == 'j') {
                text = text + 'i';
            } else {
                text = text + old_text.charAt(tmp);
            }
        }

        len = text.length();

        for(int i = 0; i < len; i += 2) {
            if (text.charAt(i + 1) == text.charAt(i)) {
                text = text.substring(0, i + 1) + 'x' + text.substring(i + 1);
            }
        }

        return text;
    }

    private String[] Divid2Pairs(String new_string) {
        String Original = this.format(new_string);
        int size = Original.length();
        if (size % 2 != 0) {
            ++size;
            Original = Original + 'x';
        }

        String[] x = new String[size / 2];
        int counter = 0;

        for(int i = 0; i < size / 2; ++i) {
            x[i] = Original.substring(counter, counter + 2);
            counter += 2;
        }

        return x;
    }

    public int[] GetDiminsions(char letter) {
        int[] key = new int[2];
        if (letter == 'j') {
            letter = 'i';
        }

        for(int i = 0; i < 5; ++i) {
            for(int j = 0; j < 5; ++j) {
                if (this.matrix_arr[i][j] == letter) {
                    key[0] = i;
                    key[1] = j;
                    break;
                }
            }
        }

        return key;
    }

    public String encryptMessage(String Source) {
        String[] src_arr = this.Divid2Pairs(Source);
        String Code = new String();
        int[] part1 = new int[2];
        int[] part2 = new int[2];

        for(int i = 0; i < src_arr.length; ++i) {
            char one = src_arr[i].charAt(0);
            char two = src_arr[i].charAt(1);
            part1 = this.GetDiminsions(one);
            part2 = this.GetDiminsions(two);
            int var10002;
            if (part1[0] == part2[0]) {
                if (part1[1] < 4) {
                    var10002 = part1[1]++;
                } else {
                    part1[1] = 0;
                }

                if (part2[1] < 4) {
                    var10002 = part2[1]++;
                } else {
                    part2[1] = 0;
                }
            } else if (part1[1] == part2[1]) {
                if (part1[0] < 4) {
                    var10002 = part1[0]++;
                } else {
                    part1[0] = 0;
                }

                if (part2[0] < 4) {
                    var10002 = part2[0]++;
                } else {
                    part2[0] = 0;
                }
            } else {
                int temp = part1[1];
                part1[1] = part2[1];
                part2[1] = temp;
            }

            Code = Code + this.matrix_arr[part1[0]][part1[1]] + this.matrix_arr[part2[0]][part2[1]];
        }

        return Code;
    }

    public String Encrypt(String message, String key) {
        message = message.toLowerCase();
        key = key.toLowerCase();
        this.setKey(key);
        this.KeyGen();
        if (message.length() % 2 != 0) {
            message = message + "x";
        }

        return this.encryptMessage(message);
    }
}
