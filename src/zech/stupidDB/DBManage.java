package zech.stupidDB;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;




/**
 * @author lawrencereisler
 *
 */
public class DBManage {
	
	
	/**
	 * initializes the basic globals and migrates the databse tables if they do not exist yet
	 * @param settings a Class object of the class in which the user globals are set. Should usually be a settings.java file created in the user package.
	 * @throws IllegalAccessException
	 * @throws NoSuchFieldException
	 * @throws InstantiationException
	 */
	public static void init(Class<?> settings) throws IllegalAccessException, NoSuchFieldException, InstantiationException{
		myConnection.settings = settings;
		Object instance = settings.newInstance();
		Field url = settings.getDeclaredField("DATABASE_URL");
		url.setAccessible(true);
		myConnection.dataBaseURL = (String) url.get(instance);
		Field tables = settings.getDeclaredField("DATA_MODELS");
		tables.setAccessible(true);
		try {
			myConnection.startConnection(Thread.currentThread().getStackTrace()[1]);
			Object classArray = tables.get(instance);
			Class<?> currentTable;
			for(int i = 0; i < Array.getLength(classArray); i++) {
				currentTable = (Class<?>) Array.get(classArray, i);
				if(!insertFuncs.checkTableExists(currentTable.getSimpleName())){
					insertFuncs.createTable(currentTable.getSimpleName(), ClassManipulation.nameAndTypeList(currentTable));
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			myConnection.closeConnection();
		}
		
	}
	
	/**
	 * @param clazz
	 */
	public static void migrate(Class<?> clazz) {
		try {
			myConnection.startConnection(Thread.currentThread().getStackTrace()[1]);
			insertFuncs.createTable(clazz.getSimpleName(), ClassManipulation.nameAndTypeList(clazz));
		} catch (Exception e) {
			System.out.println(e);
			System.out.println(e.getStackTrace());
		} finally {
			myConnection.closeConnection();
		}
	}
	
	/**
	 * @param model
	 * @param fieldName
	 * @param value
	 * @return
	 */
	public static <T> List<T> select(Class<T> model, String fieldName, String value) {
		List<T> results = new ArrayList<T>();
		try {
			myConnection.startConnection(Thread.currentThread().getStackTrace()[1]);
			ResultSet selectedTables = insertFuncs.select(model.getSimpleName(), fieldName, value);
			Object currentRow;
			ResultSetMetaData metaData = selectedTables.getMetaData();
			while(selectedTables.next()) {
				currentRow = model.newInstance();
				for(int i = 1; i <= metaData.getColumnCount(); i++) {
					ClassManipulation.setVariableValue(currentRow, metaData.getColumnName(i), selectedTables.getObject(i));
				}
				results.add((T) model.cast(currentRow));
			}
		} catch (Exception e) {
			System.out.println(e);
			System.out.println(e.getStackTrace());
		} finally {
			myConnection.closeConnection();
		}
		return results;
	}
	
	/**
	 * @param model
	 * @param id
	 * @return
	 */
	public static <T> T selectId(Class<T> model, int id) {
		String pkName = ClassManipulation.getPrimaryKey(model);
		List<T> results = select(model, pkName, String.valueOf(id));
		return results.get(0);
	}
}
