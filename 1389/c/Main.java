package c;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UncheckedIOException;
import java.lang.reflect.Array;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Main {
    public void exec(Stdin stdin, Stdout stdout) {
        int t = stdin.nextInt();
        for (int i = 0; i < t; i++) {
            String s = stdin.nextString();
            stdout.println(exec(s));
        }
    }

    private int exec(String s) {
        Map<Character, Integer> d = new HashMap<>();
        for (char ch : s.toCharArray()) {
            d.put(ch, d.getOrDefault(ch, 0)+1);
        }
        int ans = d.values().stream().max(Comparator.naturalOrder()).get();

        for (char x = '0'; x <= '9'; x++) {
            for (char y = '0'; y <= '9'; y++) {
                if (x == y) {
                    continue;
                }

                char current = x;
                int c = 0;
                for (char ch : s.toCharArray()) {
                    if (current == ch) {
                        c++;
                        if (current == x) {
                            current = y;
                        } else {
                            current = x;
                        }
                    }
                }

                if (c % 2 == 0) {
                    ans = Math.max(ans, c);
                } else {
                    ans = Math.max(ans, c - 1);
                }
            }
        }
        return s.length() - ans;
    }

    private int count(String s, char x, char y) {
        int n = s.length();
        int[][] dp = new int[n+1][2];
        for (int i = 0; i < n; i++) {
            if (s.charAt(i) == x) {
                dp[i+1][1] = Math.max(dp[i+1][1], dp[i][0]+1);
            }
            if (s.charAt(i) == y) {
                dp[i+1][0] = Math.max(dp[i+1][0], dp[i][1]+1);
            }
            dp[i+1][0] = Math.max(dp[i+1][0], dp[i][0]);
            dp[i+1][1] = Math.max(dp[i+1][1], dp[i][1]);
        }
        return Math.max(dp[n][0], dp[n][1]);
    }

    public static void main(String[] args) {
        Stdin stdin = new Stdin();
        Stdout stdout = new Stdout();
        new Main().exec(stdin, stdout);
        stdout.flush();
    }


    public static class Stdin {
        private BufferedReader stdin;
        private Deque<String> tokens;
        private Pattern delim;

        public Stdin() {
            stdin = new BufferedReader(new InputStreamReader(System.in));
            tokens = new ArrayDeque<>();
            delim = Pattern.compile(" ");
        }

        public String nextString() {
            try {
                if (tokens.isEmpty()) {
                    String line = stdin.readLine();
                    delim.splitAsStream(line).forEach(tokens::addLast);
                }
                return tokens.pollFirst();
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
        }

        public int nextInt() {
            return Integer.parseInt(nextString());
        }

        public double nextDouble() {
            return Double.parseDouble(nextString());
        }

        public int[] nextIntArray(int n) {
            int[] a = new int[n];
            for (int i = 0; i < n; i++) {
                a[i] = nextInt();
            }
            return a;
        }

        public double[] nextDoubleArray(int n) {
            double[] a = new double[n];
            for (int i = 0; i < n; i++) {
                a[i] = nextDouble();
            }
            return a;
        }
    }

    public static class Stdout {
        private PrintWriter stdout;

        public Stdout() {
            stdout =  new PrintWriter(System.out, false);
        }

        public void printf(String format, Object ... args) {
            stdout.printf(format, args);
        }

        public void println(Object ... objs) {
            String line = Arrays.stream(objs).map(this::deepToString).collect(Collectors.joining(" "));
            stdout.println(line);
        }

        private String deepToString(Object o) {
            if (o == null || !o.getClass().isArray()) {
                return Objects.toString(o);
            }

            int len = Array.getLength(o);
            String[] tokens = new String[len];
            for (int i = 0; i < len; i++) {
                tokens[i] = deepToString(Array.get(o, i));
            }
            return "{" + String.join(",", tokens) + "}";
        }

        public void flush() {
            stdout.flush();
        }
    }
}