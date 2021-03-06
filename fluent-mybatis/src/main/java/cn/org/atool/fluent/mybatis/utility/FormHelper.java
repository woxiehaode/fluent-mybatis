package cn.org.atool.fluent.mybatis.utility;

import cn.org.atool.fluent.mybatis.base.IEntity;
import cn.org.atool.fluent.mybatis.base.IRefs;
import cn.org.atool.fluent.mybatis.base.crud.FormSetter;
import cn.org.atool.fluent.mybatis.base.crud.IQuery;
import cn.org.atool.fluent.mybatis.base.model.SqlOp;
import cn.org.atool.fluent.mybatis.functions.FormApply;
import cn.org.atool.fluent.mybatis.model.Form;
import cn.org.atool.fluent.mybatis.model.FormItem;
import cn.org.atool.fluent.mybatis.model.IFormApply;
import cn.org.atool.fluent.mybatis.segment.WhereBase;

import java.util.Map;
import java.util.function.Supplier;

import static cn.org.atool.fluent.mybatis.If.isBlank;
import static cn.org.atool.fluent.mybatis.base.model.SqlOpStr.*;
import static cn.org.atool.fluent.mybatis.base.model.SqlOpStr.OP_NOT_LIKE;
import static cn.org.atool.fluent.mybatis.utility.MybatisUtil.assertNotNull;

/**
 * Form操作辅助类
 *
 * @author wudarui
 */
public class FormHelper {
    /**
     * 将表单Form转换为entityClass对应的Query
     *
     * @param entityClass
     * @param form        表单
     * @param <E>
     * @return
     */
    public static <E extends IEntity> IQuery toQuery(Class<E> entityClass, Form form) {
        assertNotNull("entityClass", entityClass);
        if (form.getNextId() != null && form.getCurrPage() != null) {
            throw new RuntimeException("nextId and currPage can only have one value");
        }
        IQuery query = IRefs.instance().defaultQuery(entityClass);
        WhereBase where = query.where();
        for (FormItem item : form.getItems()) {
            String column = IRefs.instance().findColumnByField(entityClass, item.getKey());
            if (isBlank(column)) {
                throw new RuntimeException("the field[" + item.getKey() + "] of Entity[" + entityClass.getSimpleName() + "] not found.");
            }
            switch (item.getOp()) {
                case OP_LEFT_LIKE:
                    where.and.apply(column, SqlOp.LIKE, item.getValue()[0] + "%");
                    break;
                case OP_LIKE:
                    where.and.apply(column, SqlOp.LIKE, "%" + item.getValue()[0] + "%");
                    break;
                case OP_NOT_LIKE:
                    where.and.apply(column, SqlOp.NOT_LIKE, "%" + item.getValue()[0] + "%");
                    break;
                default:
                    where.and.apply(column, SqlOp.valueOf(item.getOp()), item.getValue());
            }
        }
        if (form.getCurrPage() != null) {
            int from = form.getPageSize() * (form.getCurrPage() - 1);
            query.limit(from, form.getPageSize());
        } else if (form.getNextId() != null) {
            String column = IRefs.instance().findPrimaryColumn(entityClass);
            where.and.apply(column, SqlOp.GE, form.getNextId());
            query.limit(form.getPageSize());
        }
        return query;
    }

    public static <E extends IEntity, S extends FormSetter>
    IFormApply<E, S> by(Object object, Form form, Supplier<S> setterSupplier) {
        assertNotNull("object", object);
        Map map = PoJoHelper.toMap(object);
        FormApply<E, S> apply = new FormApply<>(setterSupplier.get(), map, form);
        return apply;
    }
}