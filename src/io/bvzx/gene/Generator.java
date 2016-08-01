package io.bvzx.gene;

import java.util.concurrent.ExecutorService;

/**
 * Created by lss on 16-7-29.
 */
public interface Generator {

    void generate();

    String getName();

    String getPath();

    void setPath(String path);

}
