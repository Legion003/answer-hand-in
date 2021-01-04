package util;

import com.sun.istack.internal.Nullable;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URL;
import java.sql.*;
import java.util.*;

/**
 * @Author Legion
 * @Date 2020/12/16 21:14
 * @Description 数据库连接工具类
 */
public class JdbcUtil {
    private static String url;
    private static String user;
    private static String password;
    private static String driver;

    /**
     * 读取数据库连接相关的配置文件
     */
    static {
        try {
            // 读取配置文件中的值
            Properties properties = new Properties();
            ClassLoader classLoader = JdbcUtil.class.getClassLoader();
            URL resource = classLoader.getResource("database-config.properties");
            String path = resource.getPath();
            properties.load(new FileReader(path));
            url = properties.getProperty("url");
            user = properties.getProperty("user");
            password = properties.getProperty("password");
            driver = properties.getProperty("driver");
            // 注册驱动
            Class.forName(driver);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 获取连接
     * @return 连接
     * @throws SQLException
     */
    public static Connection getConnection() throws SQLException{
        return DriverManager.getConnection(url, user, password);
    }

    /**
     * 释放资源
     * @param statement
     * @param connection
     */
    public static void close(Statement statement, Connection connection){
        if (statement != null){
            try {
                statement.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        if (connection != null){
            try {
                connection.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    /**
     * 释放资源
     * @param resultSet
     * @param statement
     * @param connection
     */
    public static void close(ResultSet resultSet, Statement statement, Connection connection){
        if (resultSet != null){
            try {
                resultSet.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        close(statement, connection);
    }

    /**
     * 一个检索用的轮子
     * @warning 暂时只支持 int String boolean 类型的检索
     * @warning 仅支持最简单的sql语句，distinct、count等都不支持
     * @param sql sql语句
     * @param wrapperClass 需要封装进入的类
     * @param isList 是否返回一个列表
     * @return 检索结果
     */
    public static Object search(String sql, @Nullable Class<?> wrapperClass, boolean isList){
        Object obj = null;

        // 通过sql判断一下是全列检索 or 多列检索 or 单列检索
        String tempSql = sql;

        int index1 = tempSql.indexOf("select") + 6;
        int index2 = tempSql.indexOf("from");
        tempSql = tempSql.substring(index1, index2).trim();
        String[] col = tempSql.split(",");
        if (col[0].trim().equals("*")) {
            obj = searchAll(sql, wrapperClass, isList);
        } else if (col.length == 1) {
            obj = searchOne(sql, wrapperClass, isList, col[0]);
        } else if (col.length > 1) {
            obj = searchMany(sql, isList, col);
        }

        return obj;

    }

    /**
     * 全列检索的情况
     * @param sql sql语句
     * @param wrapperClass 需要封装进入的类
     * @param isList 是否返回一个列表
     * @return 检索结果
     */
    private static Object searchAll(String sql, Class<?> wrapperClass, boolean isList){
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        // 当isList为true时，结果将装入这个list中
        List<Object> objectList = new ArrayList<>();

        // 通过class对象获取该类中的成员变量，获取成员变量的名字和类型
        Field[] fields = wrapperClass.getDeclaredFields();
        List<String> nameList = new ArrayList<>();
        List<String> typeList = new ArrayList<>();
        int length = 0;
        for (Field field : fields) {
            field.setAccessible(true);
            nameList.add(field.getName());
            Class<?> type = field.getType();
            typeList.add(type.getName());
            length++;
        }

        try {
            // 获取连接
            conn = JdbcUtil.getConnection();
            // 套入sql并获取结果，结果封装于ResultSet中
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            boolean hasResult = false;

            while (rs.next()){
                hasResult = true;
                Object obj = wrapperClass.getConstructor().newInstance();
                for (int i = 0; i<length; i++) {
                    String name = nameList.get(i);
                    // 根据类型装入
                    switch (typeList.get(i)) {
                        case "java.lang.String":
                            fields[i].set(obj, rs.getString(name));
                            break;
                        case "int":
                            fields[i].set(obj, rs.getInt(name));
                            break;
                        case "boolean":
                            fields[i].set(obj, rs.getBoolean(name));
                            break;
                        case "java.sql.Date":
                            fields[i].set(obj, rs.getDate(name));
                            break;
                        default:
                            throw new ClassNotFoundException();
                    }
                }
                // 如果不需要返回一个list，就直接返回该对象
                if (!isList) {
                    return obj;
                }
                objectList.add(obj);
            }
            if (hasResult) {
                return objectList;
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JdbcUtil.close(rs, stmt, conn);
        }
        return null;
    }

    /**
     * 单列检索的情况
     * @param sql sql语句
     * @param wrapperClass 需要封装进入的类
     * @param isList 是否返回一个列表
     * @param col 列名
     * @return 检索结果
     */
    private static Object searchOne(String sql, Class<?> wrapperClass, boolean isList, String col) {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        // 当isList为true时，结果将装入这个list中
        List<Object> objectList = new ArrayList<>();

        String typeName = wrapperClass.getName();
        try {
            // 获取连接
            conn = JdbcUtil.getConnection();
            // 套入sql并获取结果，结果封装于ResultSet中
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            boolean hasResult = false;

            while (rs.next()){
                hasResult = true;
                Object obj = null;
                // 根据类型装入
                switch (typeName) {
                    case "java.lang.String":
                        obj = rs.getString(col);
                        break;
                    case "int":
                        obj = rs.getInt(col);
                        break;
                    case "boolean":
                        obj = rs.getBoolean(col);
                        break;
                    case "java.sql.Date":
                        obj = rs.getDate(col);
                        break;
                    default:
                        throw new ClassNotFoundException();
                }
                // 如果不需要返回一个list，就直接返回该对象
                if (!isList) {
                    return obj;
                }
                objectList.add(obj);
            }
            if (hasResult) {
                return objectList;
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JdbcUtil.close(rs, stmt, conn);
        }
        return null;
    }

    /**
     * 多列检索的情况
     * @param sql sql语句
     * @param isList 是否返回一个列表
     * @param col 列名
     * @return 检索结果
     */
    private static Object searchMany(String sql, Boolean isList, String[] col) {
        Map<String, String> obj = null;
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        // 当isList为true时，结果将装入这个list中
        List<Map<String, String>> objectList = new ArrayList<>();

        try {
            // 获取连接
            conn = JdbcUtil.getConnection();
            // 套入sql并获取结果，结果封装于ResultSet中
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);

            while (rs.next()){
                obj = new HashMap<>();
                for (String name : col) {
                    name = name.trim();
                    obj.put(name, rs.getString(name));
                }
                // 如果不需要返回一个list，就直接返回该对象
                if (!isList) {
                    return obj;
                }
                objectList.add(obj);
            }
            return objectList;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JdbcUtil.close(rs, stmt, conn);
        }
        return obj;
    }



}
