package cn.org.atool.fluent.mybatis.method;

import cn.org.atool.fluent.mybatis.method.model.MapperParam;
import cn.org.atool.fluent.mybatis.method.model.SqlBuilder;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import org.apache.ibatis.mapping.MappedStatement;

/**
 * DeleteByIds: 按id列表批量删除
 *
 * @author wudarui
 */
public class DeleteByIds extends BaseMethod {
    @Override
    public MappedStatement injectMappedStatement(Class<?> mapperClass, Class<?> modelClass, TableInfo table) {
        MapperParam mapper = MapperParam.insertMapperParam(mapperClass, "deleteByIds")
            .setParameterType(modelClass)
            .setResultType(Integer.class)
            .setSql(this.getMethodSql(table));
        return super.addMappedStatement(mapper);
    }

    @Override
    protected String getMethodSql(TableInfo table) {
        SqlBuilder builder = SqlBuilder.instance();
        return builder.beginScript()
            .delete(table.getTableName())
            .where(() -> this.whereIdIn(table, builder))
            .endScript();
    }

    private SqlBuilder whereIdIn(TableInfo table, SqlBuilder builder) {
        return builder
            .value("@property IN (", table.getKeyProperty(), null)
            .foreach("coll", "item", ",", () -> builder.append("#{item}"))
            .append(")");
    }
}