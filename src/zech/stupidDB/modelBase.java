package zech.stupidDB;

import java.lang.reflect.Field;


public class modelBase {
	private int pk;
	
	public modelBase() {

	}
	
	public void save() throws NoSuchFieldException, IllegalAccessException{
		try {
			myConnection.startConnection(Thread.currentThread().getStackTrace()[1]);
			FieldDissect [] values = ClassManipulation.dissectWithValues(this);
			FieldDissect pkField = null;
			for (FieldDissect fieldDissect : values) {
				if(fieldDissect.isPK())
					pkField = fieldDissect;
			}
			if(pkField == null) {
				pkField = new FieldDissect("pk", "int");
				pkField.setValue(String.valueOf(this.pk));
			}
			try {
				if(insertFuncs.checkExists(this.getClass().getSimpleName(), pkField.getName(), pkField.getValue())) {
					insertFuncs.update(this.getClass().getSimpleName(), values, "pk", String.valueOf(this.pk));
				} else {
					int generatedKey = insertFuncs.insert(this.getClass().getSimpleName(), values);
					if(pkField.isPK() && pkField.isAutoincrement()) {
						ClassManipulation.setVariableValue(this, pkField.getName(), generatedKey);
					} else {
						this.pk = generatedKey;
					}
				}
			} catch (Exception e) {
				System.out.println(e);
			}
		} catch (Exception e) {
			System.out.println(e);
			System.out.println(e.getStackTrace());
		} finally {
			myConnection.closeConnection();
		}
	}
	
	public void delete() throws IllegalAccessException{
		try {
			myConnection.startConnection(Thread.currentThread().getStackTrace()[1]);
			Class<?> clazz = this.getClass();
			Field PKfield;
			FieldDissect dissectPK;
			try {
				PKfield = clazz.getDeclaredField(ClassManipulation.getPrimaryKey(clazz));
				dissectPK = new FieldDissect(PKfield.getName(), String.valueOf(PKfield.getType()));
				dissectPK.setValue(String.valueOf(ClassManipulation.getVariableValue(this, dissectPK.getName())));
			} catch (NoSuchFieldException e) {
				dissectPK = new FieldDissect("pk", "pk");
				dissectPK.setValue(String.valueOf(pk));
			} 
			insertFuncs.delete(clazz.getSimpleName(), dissectPK);
		} catch (Exception e) {
			System.out.println(e);
			System.out.println(e.getStackTrace());
		} finally {
			myConnection.closeConnection();
		}
	}
	
}
