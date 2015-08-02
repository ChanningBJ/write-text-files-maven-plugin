package ru.yaal.maven.writetextfiles;

import org.apache.maven.plugin.MojoExecutionException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Replace maven properties ("${project.version}", etc} with them values.
 *
 * @author Yablokov Aleksey
 */
class NullReplacer {
    private static final Pattern pattern = Pattern.compile("\\$\\{[\\w\\.]*\\}");
    private final String globalNullValue;
    private final Boolean throwExceptionOnNull;

    public NullReplacer(String globalNullValue, Boolean throwExceptionOnNull) {
        this.globalNullValue = globalNullValue;
        this.throwExceptionOnNull = throwExceptionOnNull;
    }

    public void replace(FileParameter file) throws MojoExecutionException {
        String[] lines = file.getLines();
        for (int i = 0; i < lines.length; i++) {
            String line = lines[i];
            if (line != null) {
                StringBuffer sb = new StringBuffer(line.length());
                Matcher matcher = pattern.matcher(line);
                while (matcher.find()) {
                    String property = matcher.group();
                    if (!throwExceptionOnNull) {
                        String value = file.getNullValue() != null ? file.getNullValue() : globalNullValue;
                        matcher.appendReplacement(sb, Matcher.quoteReplacement(value));
                    } else {
                        throw new MojoExecutionException("Value of property was not found: " + property);
                    }
                }
                matcher.appendTail(sb);
                lines[i] = sb.toString();
            } else {
                lines[i] = "";
            }
        }
    }
}
