package ischool.database.sqlserver;

import java.sql.Types;

import ischool.database.AbstractCreateTableAgent;
import ischool.database.ColumnCollection;
import ischool.database.ColumnInfo;
import ischool.database.ForeignKeyInfo;

public class SqlServerCreateTableAgent extends AbstractCreateTableAgent {

	public String generateSQL() {

		if (_tableName == null)
			throw new RuntimeException("Missing table name.");

		StringBuilder sb = new StringBuilder("CREATE TABLE \"");

		sb.append(super._tableName.toLowerCase()).append("\" (");

		for (int i = 0; i < _columns.size(); i++) {
			ColumnInfo column = _columns.get(i);

			sb.append("\"").append(column.getColumnName().toLowerCase())
					.append("\" ");
			if (column.isAutoIncrement()) {
				if (column.getType() == Types.BIGINT)
					sb.append("bigint identity ");
				else
					sb.append("int identity ");
				sb.append("NOT NULL ");
			} else {
				sb.append(this.getColumnInfoString(column));
			}

			if (i < _columns.size() - 1)
				sb.append(",\n");
		}

		if (super._pk != null) {
			sb.append(",\n");
			sb.append("CONSTRAINT \"pk_").append(super._tableName)
					.append("\" PRIMARY KEY (");
			sb.append(super._pk.getCombineStringWithQuote().toLowerCase())
					.append(") ");
		}

		for (ForeignKeyInfo fk : _foreignKeys) {
			sb.append(",\nCONSTRAINT \"fk_")
					.append(super._tableName)
					.append('_')
					.append(fk.getTargetTableName().toLowerCase())
					.append('_')
					.append(fk.getSelfColumns().getCombineString('_')
							.toLowerCase())
					.append("\" FOREIGN KEY (")
					.append(fk.getSelfColumns().getCombineStringWithQuote()
							.toLowerCase())
					.append(") REFERENCES ")
					.append("\"")
					.append(fk.getTargetTableName().toLowerCase())
					.append("\" (")
					.append(fk.getTargetColumns().getCombineStringWithQuote()
							.toLowerCase()).append(") ").append("ON UPDATE ")
					.append(fk.getUpdateAction().toString())
					.append(" ON DELETE ")
					.append(fk.getDeleteAction().toString());
		}

		for (ColumnCollection uniq : super._uniques) {
			sb.append(",\nCONSTRAINT \"uniq_").append(super._tableName)
					.append('_')
					.append(uniq.getCombineString('_').toLowerCase())
					.append("\" UNIQUE (")
					.append(uniq.getCombineStringWithQuote()).append(")");
		}

		sb.append(");");

		return sb.toString();
	}

	private String getColumnInfoString(ColumnInfo column) {
		StringBuilder sb = new StringBuilder();
		switch (column.getType()) {
		case Types.INTEGER:
			sb.append("int ");
			break;
		case Types.BIGINT:
			sb.append("bigint ");
			break;
		case Types.NUMERIC:
			sb.append("numeric ");
			break;
		case Types.TIMESTAMP:
			sb.append("datetime ");
			break;
		case Types.TIME:
			sb.append("time ");
			break;
		case Types.DATE:
			sb.append("date ");
			break;
		case Types.BOOLEAN:
			sb.append("bit ");
			break;
		case Types.BIT:
			sb.append("bit ");
			break;
		default:
			sb.append("nvarchar ");
			break;
		}

		this.appendLimitLength(column, sb);
		this.appendDefaultValue(column, sb);
		this.appendAllowNull(column, sb);
		return sb.toString();
	}

	private void appendAllowNull(ColumnInfo column, StringBuilder sb) {
		if (column.isAllowNull())
			return;
		sb.append("NOT NULL ");
	}

	private void appendDefaultValue(ColumnInfo column, StringBuilder sb) {
		if (column.getDefaultValue() == ColumnInfo.NO_DEFAULT_VALUE)
			return;

		sb.append("DEFAULT ").append(column.getDefaultValue()).append(" ");
	}

	private void appendLimitLength(ColumnInfo column, StringBuilder sb) {
		String lengthString = "MAX";
		if (column.getLimitLength() != ColumnInfo.UNLIMITED_COLUMN_LENGTH)
			lengthString = String.valueOf(column.getLimitLength());

		if (column.getType() == Types.VARCHAR
				|| column.getType() == Types.NVARCHAR) {
			sb.append("(").append(lengthString).append(") ");
		}
	}

	@Override
	public void addBooleanColumn(String columnName, boolean allowNull,
			boolean defaultValue) {
		String dv = defaultValue ? "1" : "0";
		this.addColumn(columnName, Types.BIT,
				ColumnInfo.UNLIMITED_COLUMN_LENGTH, allowNull, dv);
	}
}
