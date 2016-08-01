package io.bvzx.gene.type;

import io.bvzx.gene.Generator;
import io.bvzx.util.Configure;
import io.bvzx.util.Logger;

import java.io.*;
import java.util.Arrays;
import java.util.Objects;
import java.util.StringTokenizer;

/**
 * Created by lss on 16-7-29.
 */
public class FileGenrator implements Generator {

    public static final char TOKEN_IDENTIFICATION_CHAR = '$';
    public static final char START_IDENTIFICATION_CHAR = '{';
    public static final char END_IDENTIFICATION_CHAR = '}';

    private String fileName;
    private String filePath;
    private String templateFilePath;

    private Configure configure=new Configure();
    private Logger logger=Logger.getLogger(this.getClass());

    public FileGenrator(String fileName, String filePath, String templateFilePath) {
        this.fileName = fileName;
        this.filePath = filePath;
        this.templateFilePath = templateFilePath;
    }

    public FileGenrator(String fileName, String templateFilePath) {
        this.fileName = fileName;
        this.templateFilePath = templateFilePath;
    }

    public String resolveStr(String orginStr, String replaceContent) {

        Objects.requireNonNull(orginStr);
        Objects.requireNonNull(replaceContent);

        if (!orginStr.contains(TOKEN_IDENTIFICATION_CHAR + "")
                || !orginStr.contains(START_IDENTIFICATION_CHAR + "")
                || !orginStr.contains(END_IDENTIFICATION_CHAR + "")) {
            return orginStr;
        }


        char[] chars = orginStr.toCharArray();

        return new String(replace(chars, replaceContent.toCharArray()));

    }

    public char[] replace(char[] origin, char[] replace){
        return replace(origin,replace,0);
    }

    public char[] replace(char[] chars, char[] replacechar,int startIndex) {
        boolean tokenMatch = false;
        boolean startMatch = false;
        boolean endMatch = false;

        int tokenpos = 0;
        int start = 0;
        int end = 0;

        for (int i = startIndex; i < chars.length; i++) {

            char c = chars[i];

            if (endMatch == true) {
                logger.log("[成功匹配一次，返回数据]");
                break;
            } else if (c == TOKEN_IDENTIFICATION_CHAR) {
                tokenMatch = true;
                tokenpos = i;
                logger.log("[当前char为"+c+",index为"+i+"匹配token]");
            } else if (c == START_IDENTIFICATION_CHAR && tokenMatch && tokenpos + 1 == i) {
                startMatch = true;
                start = i - 1;
                logger.log("[当前char为"+c+",index为"+i+"匹配start]");
            } else if (c == END_IDENTIFICATION_CHAR && startMatch == true) {
                endMatch = true;
                end = i + 1;
                logger.log("[当前char为"+c+",index为"+i+"匹配end]");
            } else {
                logger.log("[当前char为"+c+",index为"+i+"无匹配]");
            }
        }

        if (endMatch == false) {
            logger.log("[结束匹配]");
            return chars;
        }else if (endMatch ==true){
            //char[] matchStr=new char[end-start];
            //System.arraycopy(chars,start,matchStr,end-start);

            char[] matchStr=Arrays.copyOfRange(chars,start,end);

            String handleStr=new String(matchStr);
            logger.log("[匹配的字符串是]"+handleStr);

            StringTokenizer stringTokenizer=new StringTokenizer(handleStr,"${}");
            handleStr=stringTokenizer.nextToken();
            String replaceStr=configure.getProp(handleStr);
            logger.log("[替换的字符串是]"+replaceStr);
            replacechar=replaceStr.toCharArray();
        }

        char[] result = new char[chars.length - (end - start) + replacechar.length];

        int replaceindex = 0;
        boolean over = false;
        for (int i = 0; i < result.length; i++) {
            if (i >= tokenpos && replaceindex < replacechar.length) {
                result[i] = replacechar[replaceindex++];
                if (replaceindex == replacechar.length - 1) {
                    over = true;
                }
            } else if (over) {
                result[i] = chars[i - replacechar.length + (end - start)];
            } else {
                result[i] = chars[i];
            }
        }

        return replace(result, replacechar,tokenpos+replaceindex);
        //return result;
    }

    @Override
    public void generate() {
        try {
            File tempFile = new File(templateFilePath);
            if (!tempFile.exists()) {
                throw new FileNotFoundException("[没有找到文件]");
            }

            BufferedReader reader = new BufferedReader(new FileReader(tempFile));
            PrintWriter printWriter = new PrintWriter(new File(filePath + File.separator + fileName));

            String line = null;
            while ((line = reader.readLine()) != null) {
                printWriter.write(resolveStr(line, "success") + "\n");
            }

            reader.close();
            printWriter.close();

        } catch (FileNotFoundException e) {
            System.err.println("[没有找到模板文件]");
        } catch (IOException e) {
            System.err.println("[文件IO异常]");
        }


    }

    @Override
    public String getName() {
        return fileName;
    }

    @Override
    public String getPath() {
        return filePath;
    }

    @Override
    public void setPath(String path) {
        filePath=path;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getTemplateFilePath() {
        return templateFilePath;
    }

    public void setTemplateFilePath(String templateFilePath) {
        this.templateFilePath = templateFilePath;
    }

    public static void main(String[] args) {

        FileGenrator f = new FileGenrator("code.txt", "/home/lss/Notes", "/home/lss/Notes/code.java");
        f.generate();

    }
}
