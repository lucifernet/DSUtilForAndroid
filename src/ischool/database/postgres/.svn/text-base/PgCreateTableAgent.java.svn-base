package ischool.database.postgres;

import ischool.database.AbstractCreateTableAgent;
import ischool.database.ColumnCollection;
import ischool.database.ColumnInfo;
import ischool.database.ForeignKeyInfo;

import java.sql.Types;

public class PgCreateTableAgent extends AbstractCreateTableAgent {

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
					sb.append("bigserial ");
				else
					sb.append("serial ");
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
			sb.append("integer ");
			break;
		case Types.BIGINT:
			sb.append("bigint ");
			break;
		case Types.NUMERIC:
			sb.append("numeric ");
			break;
		case Types.TIMESTAMP:
			sb.append("timestamp without time zone ");
			break;
		case Types.TIME:
			sb.append("time without time zone ");
			break;
		case Types.DATE:
			sb.append("date ");
			break;
		case Types.BOOLEAN:
			sb.append("boolean ");
			break;
		case Types.BIT:
			sb.append("bit ");
			break;
		default:
			sb.append("character varying ");
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
		if (column.getLimitLength() == ColumnInfo.UNLIMITED_COLUMN_LENGTH)
			return;

		if (column.getType() == Types.VARCHAR) {
			sb.append("(").append(column.getLimitLength()).append(") ");
		}
	}

	// public static void main(String[] args) {
	// SqlServerCreateTableAgent agent = new SqlServerCreateTableAgent();
	// agent.setTableName("abc");
	// agent.addAutoIncrementColumn("id", false);
	// agent.setPrimaryKey("id", "name");
	//
	// ColumnCollection selfColumns = ColumnCollection.create("name");
	// ColumnCollection targetColumns = ColumnCollection.create("name");
	// agent.setForeignKey("server", ForeignKeyAction.SET_NULL,
	// ForeignKeyAction.CASCADE, selfColumns, targetColumns);
	//
	// ColumnCollection selfColumns2 = ColumnCollection.create("name", "id");
	// ColumnCollection targetColumns2 = ColumnCollection.create("name", "id");
	// agent.setForeignKey("application", ForeignKeyAction.SET_NULL,
	// ForeignKeyAction.CASCADE, selfColumns2, targetColumns2);
	//
	// agent.addColumn("name", Types.VARCHAR, 50, false,
	// ColumnInfo.NO_DEFAULT_VALUE);
	// agent.addColumn("birthdate", Types.DATE,
	// ColumnInfo.UNLIMITED_COLUMN_LENGTH, true,
	// ColumnInfo.NO_DEFAULT_VALUE);
	// agent.addColumn("gender", Types.BOOLEAN, 500, false, "1");
	// agent.addColumn("last_login", Types.TIMESTAMP,
	// ColumnInfo.UNLIMITED_COLUMN_LENGTH, false, "current_timestamp");
	//
	// agent.setUnique("name", "birthdate");
	//
	// System.out.println(agent.generateSQL());
	// }

}
