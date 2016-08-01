import io.bvzx.gene.Generator;
import io.bvzx.gene.type.DirGenrator;
import io.bvzx.gene.type.FileGenrator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lss on 16-7-29.
 */
public class Starup {



    public static void main(String [] args){
        DirGenrator dirGenrator=DirGenrator.newIntance();
        dirGenrator.setDirPath("/home/lss/Notes");
        dirGenrator.setDirName("Xdoc");
        Generator generator=new FileGenrator("code.txt", "/home/lss/Notes/code.java");
        List<Generator> generatorList=new ArrayList<>();
        generatorList.add(generator);
        dirGenrator.setGeneratorList(generatorList);
        dirGenrator.generate();
    }
}
