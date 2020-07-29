package c;

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
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Main {
    public void exec() {
        int n = stdin.nextInt();
        int m = stdin.nextInt();
        String[] s = stdin.nextStringArray(n);

        int[][] diffs = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};

        Tuple[][] parent = new Tuple[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (s[i].charAt(j) == '*') {
                    continue;
                }

                Tuple root = new Tuple(i, j);
                Deque<Tuple> stack = new ArrayDeque<>();
                stack.add(root);
                while (!stack.isEmpty()) {
                    Tuple current = stack.removeLast();
                    int x = current.x;
                    int y = current.y;
                    if (0 <= x && x < n && 0 <= y && y < m && s[x].charAt(y) == '.' && parent[x][y] == null) {
                        root.size++;
                        parent[x][y] = root;
                        for (int[] diff : diffs) {
                            stack.add(new Tuple(x + diff[0], y + diff[1]));
                        }
                    }
                }
            }
        }

        String[][] t = new String[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (s[i].charAt(j) == '.') {
                    t[i][j] = ".";
                } else {
                    Set<Tuple> used = new HashSet<>();
                    int count = 1;
                    for (int[] diff : diffs) {
                        int x = i + diff[0];
                        int y = j + diff[1];
                        if (0 <= x && x < n && 0 <= y && y < m && s[x].charAt(y) == '.') {
                            Tuple root = parent[x][y];
                            if (!used.contains(root)) {
                                count += root.size;
                                used.add(root);
                            }
                        }
                    }
                    t[i][j] = Integer.toString(count%10);
                }
            }
        }

        for (String[] row : t) {
            stdout.println(String.join("", row));
        }
    }

    public class Tuple {
        public int x;
        public int y;
        public int size;
        public Tuple(int x, int y) {
            this.x = x;
            this.y = y;
            this.size = 0;
        }
        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
        @Override
        public boolean equals(Object obj) {
            if (obj instanceof Tuple) {
                Tuple other = (Tuple) obj;
                return this.x == other.x && this.y == other.y;
            }
            return false;
        }
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

        public void println(Object o) {
            stdout.println(o);
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