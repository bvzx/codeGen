package io.bvzx.gene.type;

import io.bvzx.gene.Generator;

import java.io.File;
import java.util.List;
import java.util.Objects;

/**
 * Created by lss on 16-7-29.
 */
public class DirGenrator implements Generator {

    private String dirName;

    private String dirPath;

    private List<Generator> generatorList;

    public static DirGenrator newIntance(){
        return new DirGenrator();
    }

    private DirGenrator() {
    }

    public DirGenrator(String dirName, String dir, List<Generator> generatorList) {
        this.dirName = dirName;
        this.dirPath = dir;
        this.generatorList = generatorList;
    }

    public DirGenrator(String dirName, String dir) {
        this(dirName, dir, null);
    }

    public boolean checkParameters(){
        Objects.requireNonNull(dirName);
        Objects.requireNonNull(dirPath);
        return true;
    }

    @Override
    public void generate() {
        checkParameters();

        File f = new File(dirPath + File.separator + dirName);
        if (!f.exists()) {
            f.mkdirs();
        }

        generatorList.forEach((val)->{
            if (val.getPath()==null&&val.getPath().equals("")){
                val.setPath(dirPath+dirName);
            }
            val.generate();
        });
    }

    @Override
    public String getName() {
        return dirName;
    }

    @Override
    public String getPath() {
        return dirPath;
    }

    @Override
    public void setPath(String path) {
         this.dirPath=path;
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
