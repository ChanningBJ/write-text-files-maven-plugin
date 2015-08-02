package ru.yaal.maven.writetextfiles;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.File;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.Arrays;

/**
 * todo Escape combination for ${}
 *
 * @author Aleksey Yablokov.
 */
@Mojo(name = WriteTextFilesMojo.MOJO_NAME, requiresProject = true)
public class WriteTextFilesMojo extends AbstractMojo {
    public static final String MOJO_NAME = "write-text-files";

    @Parameter(required = true)
    @SuppressWarnings({"unused", "MismatchedReadAndWriteOfArray"})
    private FileParameter[] files;

    @Parameter(defaultValue = "")
    @SuppressWarnings("unused")
    private String nullValue;

    @Parameter(defaultValue = "false")
    @SuppressWarnings("unused")
    private Boolean throwExceptionOnNull;

    @Parameter(defaultValue = "UTF-8")
    @SuppressWarnings("unused")
    private String charset;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        try {
            NullReplacer replacer = new NullReplacer(nullValue, throwExceptionOnNull);
            for (FileParameter file : files) {
                File path = file.getPath();
                if (path == null) {
                    throw new MojoExecutionException("Path is empty");
                }
                replacer.replace(file);
                path.getParentFile().mkdirs();
                if (!path.createNewFile()) {
                    getLog().info("Overwrite file: " + path.getAbsolutePath());
                } else {
                    getLog().info("Write to new file: " + path.getAbsolutePath());
                }
                Files.write(path.toPath(), Arrays.asList(file.getLines()), Charset.forName(charset));
            }
        } catch (MojoExecutionException e) {
            throw e;
        } catch (Exception e) {
            throw new MojoExecutionException(e.getMessage(), e);
        }
    }

}

