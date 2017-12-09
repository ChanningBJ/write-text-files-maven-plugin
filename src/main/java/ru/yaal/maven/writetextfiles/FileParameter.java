package ru.yaal.maven.writetextfiles;

import java.io.File;

/**
 * @author Yablokov Aleksey
 */
public class FileParameter {
    @SuppressWarnings("unused")
    private File path;

    @SuppressWarnings("unused")
    private String[] lines;

    @SuppressWarnings("unused")
    private String template;

    @SuppressWarnings("unused")
    private boolean overwrite;

    public File getPath() {
        return path;
    }

    public String[] getLines() {
        return lines;
    }

    public boolean getOverwrite(){
        return overwrite;
    }

    public String getTemplate() {
        return template;
    }
}
