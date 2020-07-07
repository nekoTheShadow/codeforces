package b;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UncheckedIOException;
import java.lang.reflect.Array;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.Objects;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Main {
    public void exec() {
        int t = stdin.nextInt();
        for (int i = 0; i < t; i++) {
            String s = stdin.nextString();

            int n = s.length();
            int l = 0;
            int r = 0;
            int a = 0;
            int b = 0;
            int c = 0;
            int ans = Integer.MAX_VALUE;
            while (l < n && r <= n) {
                if (a == 0 || b == 0 || c == 0) {
                    if (r == n) break;

                    if (s.charAt(r) == '1') a++;
                    if (s.charAt(r) == '2') b++;
                    if (s.charAt(r) == '3') c++;
                    r++;
                } else {
                    ans = Math.min(ans, r-l);
                    if (s.charAt(l) == '1') a--;
                    if (s.charAt(l) == '2') b--;
                    if (s.charAt(l) == '3') c--;
                    l++;
                }
            }

            stdout.println(ans == Integer.MAX_VALUE ? 0 : ans);
        }
    }

    private static final Stdin stdin = new Stdin();
    private static final Stdout stdout = new Stdout();

    public static void main(String[] args) {
        new Main().exec();
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

        public long nextLong() {
            return Long.parseLong(nextString());
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
