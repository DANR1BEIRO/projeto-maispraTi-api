package br.com.maisprati.api.enuns;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.usertype.UserType;

public class PostgreSQLEnumType implements UserType<RoleEnum> {

    @Override
    public int getSqlType() {
        return Types.OTHER;
    }

    @Override
    public Class<RoleEnum> returnedClass() {
        return RoleEnum.class;
    }

    @Override
    public boolean equals(RoleEnum x, RoleEnum y) {
        return x == y;
    }

    @Override
    public int hashCode(RoleEnum x) {
        return x.hashCode();
    }

    @Override
    public RoleEnum nullSafeGet(ResultSet rs, int position, SharedSessionContractImplementor session, Object owner) throws SQLException {
        String role = rs.getString(position);
        return role != null ? RoleEnum.valueOf(role) : null;
    }

    @Override
    public void nullSafeSet(PreparedStatement st, RoleEnum value, int index, SharedSessionContractImplementor session) throws SQLException {
        if (value == null) {
            st.setNull(index, Types.OTHER);
        } else {
            st.setObject(index, value.name(), Types.OTHER); // envia para PostgreSQL enum
        }
    }

    @Override
    public RoleEnum deepCopy(RoleEnum value) {
        return value;
    }

    @Override
    public boolean isMutable() {
        return false;
    }

    @Override
    public Serializable disassemble(RoleEnum value) {
        return value.name();
    }

    @Override
    public RoleEnum assemble(Serializable cached, Object owner) {
        return RoleEnum.valueOf((String) cached);
    }

    @Override
    public RoleEnum replace(RoleEnum original, RoleEnum target, Object owner) {
        return original;
    }
}
