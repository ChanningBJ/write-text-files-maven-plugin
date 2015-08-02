package ru.yaal.maven.writetextfiles;

import org.apache.maven.plugin.MojoExecutionException;
import org.junit.Test;

import static org.hamcrest.Matchers.arrayContaining;
import static org.junit.Assert.assertThat;

/**
 * @author Yablokov Aleksey
 */
public class NullReplacerTest {

    @Test
    public void defaultNullValue() throws MojoExecutionException {
        FileParameter fp = new FileParameter();
        fp.setLines(new String[]{
                "Description: ${project.description}",
                "Artifact path: ${project.groupId}:${project.artifactId}:${project.version}",
                "",
                null
        });
        NullReplacer r = new NullReplacer("", false);
        r.replace(fp);

        assertThat(fp.getLines(), arrayContaining(
                "Description: ",
                "Artifact path: ::",
                "",
                ""
        ));
    }

    @Test
    public void fileNullValue() throws MojoExecutionException {
        FileParameter fp = new FileParameter();
        fp.setNullValue("FILE-NULL-VALUE");
        fp.setLines(new String[]{
                "Description: ${project.description}",
                "Artifact path: ${project.groupId}:${project.artifactId}:${project.version}",
                "",
                null
        });
        NullReplacer r = new NullReplacer("GLOBAL-NULL-VALUE", false);
        r.replace(fp);

        assertThat(fp.getLines(), arrayContaining(
                "Description: FILE-NULL-VALUE",
                "Artifact path: FILE-NULL-VALUE:FILE-NULL-VALUE:FILE-NULL-VALUE",
                "",
                ""
        ));
    }

    @Test
    public void globalNullValue() throws MojoExecutionException {
        FileParameter fp = new FileParameter();
        fp.setLines(new String[]{
                "Description: ${project.description}",
                "Artifact path: ${project.groupId}:${project.artifactId}:${project.version}",
                "",
                null
        });
        NullReplacer r = new NullReplacer("GLOBAL-NULL-VALUE", false);
        r.replace(fp);

        assertThat(fp.getLines(), arrayContaining(
                "Description: GLOBAL-NULL-VALUE",
                "Artifact path: GLOBAL-NULL-VALUE:GLOBAL-NULL-VALUE:GLOBAL-NULL-VALUE",
                "",
                ""
        ));
    }

    @Test(expected = MojoExecutionException.class)
    public void exception() throws MojoExecutionException {
        FileParameter fp = new FileParameter();
        fp.setLines(new String[]{"Description: ${project.description}"});
        NullReplacer r = new NullReplacer("GLOBAL-EMPTY", true);
        r.replace(fp);
    }
}
