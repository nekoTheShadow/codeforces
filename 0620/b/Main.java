package b;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UncheckedIOException;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Main {
    public void exec() {
        int a = stdin.nextInt();
        int b = stdin.nextInt();

        long ans = 0;
        for (int i = a; i <= b; i++) {
            ans += f(i);
        }
        stdout.println(ans);
    }

    public long f(int x) {
        long sum = 0;
        while (x > 0) {
            int y = x % 10;
            if (y == 0) sum += 6;
            if (y == 1) sum += 2;
            if (y == 2) sum += 5;
            if (y == 3) sum += 5;
            if (y == 4) sum += 4;
            if (y == 5) sum += 5;
            if (y == 6) sum += 6;
            if (y == 7) sum += 3;
            if (y == 8) sum += 7;
            if (y == 9) sum += 6;
            x /= 10;
        }
        return sum;
    }

    private static final Stdin stdin = new Stdin();
    private static final Stdout stdout = new Stdout();

    public static void main(String[] args) {
        try {
            new Main().exec();
        } finally {
            stdout.flush();
        }
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
                    if (line == null) {
                        throw new UncheckedIOException(new EOFException());
                    }
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

        public String[] nextStringArray(int n) {
            String[] a = new String[n];
            for (int i = 0; i < n; i++) a[i] = nextString();
            return a;
        }

        public int[] nextIntArray(int n) {
            int[] a = new int[n];
            for (int i = 0; i < n; i++) a[i] = nextInt();
            return a;
        }

        public double[] nextDoubleArray(int n) {
            double[] a = new double[n];
            for (int i = 0; i < n; i++) a[i] = nextDouble();
            return a;
        }

        public long[] nextLongArray(int n) {
            long[] a = new long[n];
            for (int i = 0; i < n; i++) a[i] = nextLong();
            return a;
        }
    }

    public static class Stdout {
        private PrintWriter stdout;

        public Stdout() {
            stdout =  new PrintWriter(System.out, false);
        }

        public void printf(String format, Object ... args) {
            String line = String.format(format, args);
            if (line.endsWith(System.lineSeparator())) {
                stdout.print(line);
            } else {
                stdout.println(line);
            }
        }

        public void println(Object ... o) {
            String line = Arrays.stream(o).map(Objects::toString).collect(Collectors.joining(" "));
            stdout.println(line);
        }

        public void debug(Object ... objs) {
            String line = Arrays.stream(objs).map(this::deepToString).collect(Collectors.joining(" "));
            stdout.printf("DEBUG: %s%n", line);
        }

        private String deepToString(Object o) {
            if (o == null) {
                return "null";
            }

            Class<?> clazz = o.getClass();

            // 配列の場合
            if (clazz.isArray()) {
                int len = Array.getLength(o);
                String[] tokens = new String[len];
                for (int i = 0; i < len; i++) {
                    tokens[i] = deepToString(Array.get(o, i));
                }
                return "{" + String.join(",", tokens) + "}";
            }

            // toStringがOverrideされている場合
            if (Arrays.stream(clazz.getDeclaredMethods()).anyMatch(method -> method.getName().equals("toString") && method.getParameterCount() == 0)) {
                return Objects.toString(o);
            }

            // Tupleの場合 (フィールドがすべてpublicのJava Beans)
            try {
                List<String> tokens = new ArrayList<>();
                for (Field field : clazz.getFields()) {
                    String token = String.format("%s=%s", field.getName(), deepToString(field.get(o)));
                    tokens.add(token);
                }
                return String.format("%s:[%s]", clazz.getName(), String.join(",", tokens));
            } catch (IllegalAccessException e) {
                throw new IllegalArgumentException(e);
            }
        }

        public void flush() {
            stdout.flush();
        }
    }
}