package a;

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
    public void exec(Stdin stdin, Stdout stdout) {
        int t = stdin.nextInt();
        for (int i = 0; i < t; i++) {
            int a = stdin.nextInt();
            int b = stdin.nextInt();
            int c = Math.min(a, Math.min(b, (a+b)/3));
            stdout.println(c);
        }
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