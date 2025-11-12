package br.com.maisprati.api.enuns;

import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.usertype.UserType;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

public class PostgreSQLProgressStatusEnumType implements UserType<ProgressStatusEnum> {

    @Override
    public int getSqlType() {
        return Types.OTHER;
    }

    @Override
    public Class<ProgressStatusEnum> returnedClass() {
        return ProgressStatusEnum.class;
    }

    @Override
    public boolean equals(ProgressStatusEnum x, ProgressStatusEnum y) {
        return x == y;
    }

    @Override
    public int hashCode(ProgressStatusEnum x) {
        return x.hashCode();
    }

    @Override
    public ProgressStatusEnum nullSafeGet(ResultSet rs, int position, SharedSessionContractImplementor session, Object owner) throws SQLException {
        String role = rs.getString(position);
        return role != null ? ProgressStatusEnum.valueOf(role) : null;
    }

    @Override
    public void nullSafeSet(PreparedStatement st, ProgressStatusEnum value, int index, SharedSessionContractImplementor session) throws SQLException {
        if (value == null) {
            st.setNull(index, Types.OTHER);
        } else {
            st.setObject(index, value.name(), Types.OTHER); // envia para PostgreSQL enum
        }
    }

    @Override
    public ProgressStatusEnum deepCopy(ProgressStatusEnum value) {
        return value;
    }

    @Override
    public boolean isMutable() {
        return false;
    }

    @Override
    public Serializable disassemble(ProgressStatusEnum value) {
        return value.name();
    }

    @Override
    public ProgressStatusEnum assemble(Serializable cached, Object owner) {
        return ProgressStatusEnum.valueOf((String) cached);
    }

    @Override
    public ProgressStatusEnum replace(ProgressStatusEnum original, ProgressStatusEnum target, Object owner) {
        return original;
    }
}

