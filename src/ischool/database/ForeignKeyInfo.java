package ischool.database;

public class ForeignKeyInfo {

	private String _targetTableName;
	private ForeignKeyAction _updateAction;
	private ForeignKeyAction _deleteAction;
	private ColumnCollection _selfColumns;
	private ColumnCollection _targetColumns;

	public ForeignKeyInfo(String targetTableName, ColumnCollection selfColumns,
			ColumnCollection targetColumns, ForeignKeyAction updateAction,
			ForeignKeyAction deleteAction) {
		_targetTableName = targetTableName;
		_updateAction = updateAction;
		_deleteAction = deleteAction;
		_selfColumns = selfColumns;
		_targetColumns = targetColumns;
	}

	public String getTargetTableName() {
		return _targetTableName;
	}

	public ForeignKeyAction getUpdateAction() {
		return _updateAction;
	}

	public ForeignKeyAction getDeleteAction() {
		return _deleteAction;
	}

	public ColumnCollection getSelfColumns() {
		return _selfColumns;
	}

	public ColumnCollection getTargetColumns() {
		return _targetColumns;
	}
}
