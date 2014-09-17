package ischool.dsa.task;

import java.util.ArrayList;

public class TaskCollection {

	private ArrayList<ITask> _list;

	public TaskCollection() {
		_list = new ArrayList<ITask>();
	}

	public void addTask(ITask task) {
		_list.add(task);
	};

	public void doTask() {
		for (ITask task : _list) {
			System.out.println("測試項目:" + task.getName());

			try {
				task.test();

				System.out.println("OK");
			} catch (Exception ex) {
				System.out.println("發生錯誤:" + ex.getMessage());
				ex.printStackTrace();
			}
		}
	}
}
