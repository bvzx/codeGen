package io.bvzx.gene.type;

import io.bvzx.gene.Generator;

import java.io.File;
import java.util.List;

/**
 * Created by lss on 16-7-29.
 */
public class DirGenrator implements Generator {

    private String dirName;

    private String dirPath;

    private List<Generator> generatorList;

    public DirGenrator(String dirName, String dir, List<Generator> generatorList) {
        this.dirName = dirName;
        this.dirPath = dir;
        this.generatorList = generatorList;
    }

    public DirGenrator(String dirName, String dir) {
        this(dirName, dir, null);
    }

    @Override
    public void generate() {
        File f = new File(dirPath + File.separator + dirName);
        if (!f.exists()) {
            f.mkdirs();
        }
    }

    @Override
    public String getName() {
        return dirName;
    }

    @Override
    public String getPath() {
        return dirPath;
    }

    public String getDirName() {
        return dirName;
    }

    public void setDirName(String dirName) {
        this.dirName = dirName;
    }

    public String getDirPath() {
        return dirPath;
    }

    public void setDirPath(String dirPath) {
        this.dirPath = dirPath;
    }

    public List<Generator> getGeneratorList() {
        return generatorList;
    }

    public void setGeneratorList(List<Generator> generatorList) {
        this.generatorList = generatorList;
    }

    public static void main(String[] args) {

        Generator g = new DirGenrator("mycode", "/home/lss/Notes");
        g.generate();

    }
}
