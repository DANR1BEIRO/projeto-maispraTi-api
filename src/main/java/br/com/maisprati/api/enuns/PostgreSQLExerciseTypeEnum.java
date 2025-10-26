package br.com.maisprati.api.enuns;

import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.usertype.UserType;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

public class PostgreSQLExerciseTypeEnum implements UserType<ExerciseTypeEnum> {

    @Override
    public int getSqlType() {
        return Types.OTHER;
    }

    @Override
    public Class<ExerciseTypeEnum> returnedClass() {
        return ExerciseTypeEnum.class;
    }

    @Override
    public boolean equals(ExerciseTypeEnum x, ExerciseTypeEnum y) {
        return x == y;
    }

    @Override
    public int hashCode(ExerciseTypeEnum x) {
        return x.hashCode();
    }

    @Override
    public ExerciseTypeEnum nullSafeGet(ResultSet rs, int position, SharedSessionContractImplementor session, Object owner) throws SQLException {
        String role = rs.getString(position);
        return role != null ? ExerciseTypeEnum.valueOf(role) : null;
    }

    @Override
    public void nullSafeSet(PreparedStatement st, ExerciseTypeEnum value, int index, SharedSessionContractImplementor session) throws SQLException {
        if (value == null) {
            st.setNull(index, Types.OTHER);
        } else {
            st.setObject(index, value.name(), Types.OTHER); // envia para PostgreSQL enum
        }
    }

    @Override
    public ExerciseTypeEnum deepCopy(ExerciseTypeEnum value) {
        return value;
    }

    @Override
    public boolean isMutable() {
        return false;
    }

    @Override
    public Serializable disassemble(ExerciseTypeEnum value) {
        return value.name();
    }

    @Override
    public ExerciseTypeEnum assemble(Serializable cached, Object owner) {
        return ExerciseTypeEnum.valueOf((String) cached);
    }

    @Override
    public ExerciseTypeEnum replace(ExerciseTypeEnum original, ExerciseTypeEnum target, Object owner) {
        return original;
    }
}
