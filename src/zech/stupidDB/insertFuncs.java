package zech.stupidDB;


import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class insertFuncs {
	private static int writeUpdate(String sql) {
		ResultSet keyRes = null;
		int key = 0;
		try {
			Statement statement = myConnection.connection.createStatement();
			statement.executeUpdate(sql);
			keyRes = statement.executeQuery("select last_insert_rowid()");
			if(keyRes.next()) {
				key = keyRes.getInt(1);
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return key;
	}
	
	private static ResultSet query(String sql) {
		ResultSet resultSet = null;
		try {
			Statement statement = myConnection.connection.createStatement();
			resultSet = statement.executeQuery(sql);
		} catch (Exception e) {
			System.out.println(e.toString());
		} 
		return resultSet;
	}
	
	public static boolean checkExists(String tableName, String pkName, String pkValue) throws SQLException{
		String sql = "select * from " + tableName + " where " + pkName + "=" + pkValue;
		ResultSet res = query(sql);
		return res.next();
	}
	
	public static boolean checkTableExists(String tableName) throws SQLException{
		DatabaseMetaData metaData = myConnection.connection.getMetaData();
		ResultSet tables = metaData.getTables(null, null, tableName, null);
		if(tables.next()) {
			return true;
		}
		return false;
	}
	
	public static void createTable(String name, FieldDissect [] namesAndTypes) {
		boolean pkInserted = false;
		String sql = "create table " + name + "(";
		for(int i = 0; i < namesAndTypes.length; i++) {
			sql = sql + namesAndTypes[i].getName() + " ";
			switch (namesAndTypes[i].getType()) {
			case "String":
				sql = sql + "text";
				break;
				
			case "int":
				sql = sql + "integer";
				break;

			default:
				sql = sql + namesAndTypes[i].getType();
				break;
			}
			if(namesAndTypes[i].isPK()) {
				sql = sql + " primary key";
				pkInserted = true;
				if(namesAndTypes[i].isAutoincrement())
					sql = sql + " autoincrement";
			}
			if(i+1 < namesAndTypes.length)
				sql = sql + ", ";
		}
		if(!pkInserted)
			sql = sql + ", pk integer primary key autoincrement";
		sql = sql + ");";
		writeUpdate(sql);
	}
	
	
	public static void update(String tableName, FieldDissect [] values, String pkName, String pkValue) {
		String sql = "update " + tableName + " set ";
		for (FieldDissect fieldDissect : values) {
			if(fieldDissect.isPK()) {
				pkName = fieldDissect.getName();
				pkValue = fieldDissect.getValue();
				continue;
			}
			sql = sql + fieldDissect.getName() + "=" + fieldDissect.getValue() + ", ";
		}
		sql = sql.substring(0, sql.length() -2);
		sql = sql + " where " + pkName + "=" + pkValue;
		System.out.println(sql);
		writeUpdate(sql);
	}
	
	public static int insert(String tableName, FieldDissect [] values) throws SQLException{
		String sql = "insert into " + tableName + " (";
		for (FieldDissect entry : values) {
			if(entry.isPK() && entry.isAutoincrement()) {
				continue;
			}
			sql = sql + entry.getName() + ", ";
		}
		sql = sql.substring(0, sql.length()-2);
		sql = sql + ") values (";
		for (FieldDissect value : values) {
			if (value.isPK() && value.isAutoincrement()) {
				continue;
			}
			sql = sql + value.getValue() + ", ";
		}
		sql = sql.substring(0, sql.length()-2) + ")";
		return writeUpdate(sql);
	}
	
	public static void delete(String tablename, FieldDissect pk) {
		String sql = "delete from " + tablename + " where " + pk.getName() + "=";
		if(pk.getType().equals("String")) {
			sql = sql + "'" + pk.getValue() + "'";
		} else {
			sql = sql + pk.getValue();
		}
		System.out.println(sql);
		writeUpdate(sql);
	}
	
	public static ResultSet select(String tablename, String FieldName, String FieldValue) {
		String sql = "select * from " + tablename;
		if(FieldValue != null) {
			sql = sql + " where " + FieldName + "=" + FieldValue + ";";
		}
		System.out.println(sql);
		return query(sql);
	}
	
}
