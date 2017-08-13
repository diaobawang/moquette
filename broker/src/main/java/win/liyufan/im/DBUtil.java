package win.liyufan.im;


import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.RowId;
import java.sql.SQLClientInfoException;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Savepoint;
import java.sql.Statement;
import java.sql.Struct;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executor;
import java.util.regex.MatchResult;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import java.sql.PreparedStatement;
import java.sql.Ref;
import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Array;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.Date;
import java.sql.NClob;
import java.sql.ParameterMetaData;

public class DBUtil {
    private static ComboPooledDataSource comboPooledDataSource = null;
        //静态代码块
        static{
            /*
             * 读取c3p0的xml配置文件创建数据源，c3p0的xml配置文件c3p0-config.xml必须放在src目录下
             * 使用c3p0的命名配置读取数据源
             */
            comboPooledDataSource = new ComboPooledDataSource("c3p0");
        }
        //从数据源中获取数据库的连接
        public static Connection getConnection() throws SQLException {
           return comboPooledDataSource.getConnection();
        }

        public static void initDB() {
			try {
				closeDB(getConnection(), null);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
        //释放资源，将数据库连接还给数据库连接池
        public static void closeDB(Connection conn,PreparedStatement ps,ResultSet rs) {
                try {
                    if (rs!=null) {
                        rs.close();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally{
                    try {
                        if (ps!=null) {
                            ps.close();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally{
                        try {
                            if (conn!=null) {
                                conn.close();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
        }
        //释放资源，将数据库连接还给数据库连接池
        public static void closeDB(Connection conn, PreparedStatement ps) {
             try {
                if (ps!=null) {
                    ps.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally{
                try {
                    if (conn!=null) {
                        conn.close();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
}