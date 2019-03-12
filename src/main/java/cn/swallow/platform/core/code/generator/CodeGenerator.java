package cn.swallow.platform.core.code.generator;

import cn.swallow.platform.SwallowffApplication;
import cn.swallow.platform.core.code.config.CodeGenConfig;
import cn.swallow.platform.core.code.entity.ColumnClass;
import cn.swallow.platform.core.util.JdbcUtil;
import cn.swallow.platform.core.util.SpringContextHolder;
import cn.swallow.platform.core.util.StreamUtil;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;

import java.io.*;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import static com.alibaba.druid.sql.dialect.db2.ast.DB2Object.Constants.CURRENT_DATE;
import static javafx.css.StyleOrigin.AUTHOR;

/**
 * @author shenyu
 * @create 2019/3/12
 */
@Component
public class CodeGenerator {
    private String tableName;
    private String targetPackage;
    private String className;

    private CodeGenerator() {
    }

    public CodeGenerator(String tableName,String targetPackage){
        this.tableName = tableName;
        this.targetPackage = targetPackage;
        className = replaceUnderLineAndUpperCase(tableName);
    }

    private Connection getConnection() throws SQLException{
        return JdbcUtil.getConnection();
    }

    public void generate() {
        try {
            Connection connection = getConnection();
            DatabaseMetaData databaseMetaData = connection.getMetaData();
            ResultSet resultSet = databaseMetaData.getColumns(null,"%",tableName,"%");

            //生成Mapper文件
            generateMapperFile(resultSet);
            //生成Dao文件
            generateRepositoryFile(resultSet);
            //生成service文件
            generateServiceFile(resultSet);
            //生成controller文件
            generateControllerFile(resultSet);
            //生成entity文件
            generateEntityFile(resultSet);
            //生成dto文件
            generateDtoFile(resultSet);

            //TODO 生成前端文件

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void generateMapperFile(ResultSet resultSet){
    }

    private void generateRepositoryFile(ResultSet resultSet){
    }

    private void generateServiceFile(ResultSet resultSet){
    }

    private void generateControllerFile(ResultSet resultSet){
    }

    private void generateDtoFile(ResultSet resultSet){
    }

    private void generateEntityFile(ResultSet resultSet) throws Exception{
//        final String targetPath = projectPath + File.separator + "src" + File.separator + "main" + File.separator + "java" + File.separator + CommonUtil.packageToPath(basePackage + "." + targetPackage) + File.separator + className + CodeGenConfig.java_suffix;
        String targetPath = CodeGenConfig.getTargetPath2(targetPackage,CodeGenConfig.CodeGenType.Entity);
        File targetFile = new File(targetPath);
        List<ColumnClass> columnClassList = new ArrayList<>();
        ColumnClass columnClass = null;
        while(resultSet.next()){
            //id字段略过
            if(resultSet.getString("COLUMN_NAME").equals("id")) continue;
            columnClass = new ColumnClass();
            //获取字段名称
            columnClass.setColumnName(resultSet.getString("COLUMN_NAME"));
            //获取字段类型
            columnClass.setColumnType(resultSet.getString("TYPE_NAME"));
            //转换字段名称，如 sys_name 变成 SysName
            columnClass.setChangeColumnName(replaceUnderLineAndUpperCase(resultSet.getString("COLUMN_NAME")));
            //字段在数据库的注释
            columnClass.setColumnComment(resultSet.getString("REMARKS"));
            columnClassList.add(columnClass);
        }
        Map<String,Object> dataMap = new HashMap<>();
        dataMap.put("table_columns",columnClassList);
        generateFileByTemplate(CodeGenConfig.CodeGenType.Entity,targetFile,targetPackage,dataMap);
    }

    private void generateFileByTemplate(CodeGenConfig.CodeGenType entity,File dir,String targetPackage,Map<String,Object> dataMap){
        File targetFile = null;
        OutputStreamWriter osw = null;
        Writer out = null;
        FileOutputStream fos = null;
        try {
            Template template = FreeMarkerTemplateUtil.getTemplate(entity.getTplFileName());
            if (!dir.exists()){
    //            System.out.println("目录不存在");
                throw new RuntimeException("target file is not exist "+dir.getAbsolutePath());
            }else {
                //文件存在,检查是否是目录
                if (!dir.isDirectory()){
                    //不是目录,抛出异常
                    throw new RuntimeException("target file is not a directory");
                }else {
                    //是目录,则新建文件
                    targetFile = new File(dir.getAbsolutePath()+File.separator+className+CodeGenConfig.java_suffix);
                    if (targetFile.exists()){
                        //已有文件,先删除
                        targetFile.delete();
                    }
                    targetFile.createNewFile();
                }
            }
            if (targetFile != null){
                fos = new FileOutputStream(targetFile);
                dataMap.put("table_name_small",tableName);
                dataMap.put("class_name",className);
                dataMap.put("author",CodeGenConfig.author);
                dataMap.put("date",new Date());
                dataMap.put("package_name",targetPackage);
                dataMap.put("table_annotation","表的备注");
                osw = new OutputStreamWriter(fos ,"utf-8");
                out = new BufferedWriter(osw,10240);
                template.process(dataMap,out);

            }
        } catch (Exception e) {
            StreamUtil.close(fos,out,osw);
            targetFile.delete();
            e.printStackTrace();
        } finally {
            StreamUtil.close(fos,out,osw);
        }
    }

    public static String replaceUnderLineAndUpperCase(String str){
        StringBuffer sb = new StringBuffer();
        sb.append(str);
        int count = sb.indexOf("_");
        while(count!=0){
            int num = sb.indexOf("_",count);
            count = num + 1;
            if(num != -1){
                char ss = sb.charAt(count);
                char ia = (char) (ss - 32);
                sb.replace(count , count + 1,ia + "");
            }
        }
        String result = sb.toString().replaceAll("_","");
        return StringUtils.capitalize(result);
    }



    public static CodeGenerator me(){
        return SpringContextHolder.getBean(CodeGenerator.class);
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
}
