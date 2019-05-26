package userend;

import java.util.List;

import zech.stupidDB.DBManage;

public class Main {

	public static void main(String[] args) {
		//DBManage.migrate(TestModel.class);
		TestModel testModel = new TestModel();
		try {
			DBManage.init(settings.class);
			List<TestModel> fList = DBManage.select(TestModel.class, "id", "14");
			System.out.println(fList.size());
			System.out.println(fList.get(0).getFirstName());
			TestModel testModel2 = DBManage.selectId(TestModel.class, 11);
			testModel2.setFirstName("yair");
			testModel2.save();
			
		} catch (Exception e) {
			System.out.println(e);
		}
	}

}
