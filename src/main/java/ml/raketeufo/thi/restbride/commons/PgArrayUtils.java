package ml.raketeufo.thi.restbride.commons;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;

public final class PgArrayUtils {
    private PgArrayUtils() {
    }

    public static List<Long> parseLongArray(String text) {
        checkArgument(text.startsWith("{") && text.endsWith("}"),
                "text.startsWith(\"{\") && text.endsWith(\"}\")");
        String[] items = text.substring(1, text.length() - 1).split(",");
        ArrayList<Long> result = new ArrayList<>(items.length);
        for (String item : items) {
            try {
                result.add(Long.parseLong(item, 10));
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("not a long array: " + text, e);
            }
        }
        return result;
    }

    public static List<Double> parseDoubleArray(String text) {
        checkArgument(text.startsWith("{") && text.endsWith("}"),
                "text.startsWith(\"{\") && text.endsWith(\"}\")");
        String[] items = text.substring(1, text.length() - 1).split(",");
        ArrayList<Double> result = new ArrayList<>(items.length);
        for (String item : items) {
            try {
                result.add(Double.parseDouble(item));
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("not a long array: " + text, e);
            }
        }
        return result;
    }

    public static List<String> parseStringArray(String text) {
        return new StringParser(text).parse();
    }

    public static void main(String[] args) {
        List<String> x = PgArrayUtils.parseStringArray("{\"\\\"{a}',b,c\",\"d,e\",|,\"\\\"\\\\\u0001\"}");
        x.forEach(System.out::println);
        System.out.println(PgArrayUtils.parseLongArray("{123,123}"));
        System.out.println(PgArrayUtils.parseDoubleArray("{123.4,123.03}"));

    }

    private static class StringParser {

        private final String source;
        private int i = 0;

        public StringParser(String source) {
            this.source = source;
        }

        private void expect(char c) {
            char got = peek();
            if (got != c) {
                throw new IllegalArgumentException(String.format("expect %c at %s, got %c",
                        c, formatPosition(), got
                ));
            }
        }

        private String formatPosition(int adjust) {
            int position = this.i + adjust;
            return String.format("%d ('%s'^'%s')", position, source.substring(0, position), source.substring(position));
        }

        private String formatPosition() {
            return formatPosition(0);
        }

        private char peek() {
            if (i >= source.length()) {
                throw new IllegalArgumentException("expect a char, got eof");
            }
            return source.charAt(i);
        }

        private char read() {
            char c = peek();
            i++;
            return c;
        }

        private String read(int length) {
            String result = peek(length);
            i += length;
            return result;
        }

        private String peek(int length) {
            checkArgument(length > 0 && length + i < source.length(), "expect %d chars, got eof, at %s",
                    length, formatPosition());
            return source.substring(i, i + length);
        }

        private void eat(char c) {
            expect(c);
            i++;
        }

        private void eof() {
            if (i != source.length()) {
                throw new IllegalArgumentException(String.format("expect eof at %s",
                        formatPosition()
                ));
            }
        }

        private boolean is(char c) {
            return peek() == c;
        }

        private void eat() {
            i++;
        }


        public List<String> parse() {
            eat('{');
            List<String> result = new ArrayList<>();
            while (!is('}')) {
                result.add(parseElement());
                if (!is('}')) {
                    eat(',');
                }
            }
            eat('}');
            eof();
            return result;
        }

        private String parseElement() {
            if (is('"')) {
                return parseQuotedElement();
            } else {
                return parseUnquotedElement();
            }
        }

        private String parseUnquotedElement() {
            char c = peek();
            StringBuilder sb = new StringBuilder();
            while (c != ',' && c != '{' && c != '}' && c != '"') {
                eat();
                sb.append(c);
                c = peek();
            }
            return sb.toString();
        }

        private String parseQuotedElement() {
            eat('"');
            StringBuilder sb = new StringBuilder();
            char c;
            while ((c = read()) != '"') {
                if (c == '\\') {
                    c = read();
                    switch (c) {
                        case 'b':
                            sb.append('\b');
                            break;
                        case 'f':
                            sb.append('\f');
                            break;
                        case 'n':
                            sb.append('\n');
                            break;
                        case 'r':
                            sb.append('\r');
                            break;
                        case 't':
                            sb.append('\t');
                            break;
                        case 'u':
                            String hex = read(4);
                            try {
                                int hexInt = Integer.parseInt(hex, 16);
                                sb.append((char) hexInt);
                            } catch (NumberFormatException e) {
                                throw new IllegalArgumentException(String.format("expect a 4 chars hex number at %s", formatPosition(-4)));
                            }
                            break;
                        case '"':
                            sb.append('"');
                            break;
                        case '\\':
                            sb.append('\\');
                            break;
                        default:
                            throw new IllegalArgumentException(String.format("unknown escaped char: %c at %s",
                                    c, formatPosition(-1)));

                    }
                } else {
                    sb.append(c);
                }
            }
            return sb.toString();

        }
    }
}
