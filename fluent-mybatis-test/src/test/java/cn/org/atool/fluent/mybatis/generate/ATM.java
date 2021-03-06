package cn.org.atool.fluent.mybatis.generate;

import cn.org.atool.fluent.mybatis.generate.dm.HomeAddressDataMap;
import cn.org.atool.fluent.mybatis.generate.dm.MemberDataMap;
import cn.org.atool.fluent.mybatis.generate.dm.MemberFavoriteDataMap;
import cn.org.atool.fluent.mybatis.generate.dm.MemberLoveDataMap;
import cn.org.atool.fluent.mybatis.generate.dm.NoAutoIdDataMap;
import cn.org.atool.fluent.mybatis.generate.dm.NoPrimaryDataMap;
import cn.org.atool.fluent.mybatis.generate.dm.StudentDataMap;
import cn.org.atool.fluent.mybatis.generate.dm.StudentScoreDataMap;
import cn.org.atool.fluent.mybatis.generate.mix.HomeAddressTableMix;
import cn.org.atool.fluent.mybatis.generate.mix.MemberFavoriteTableMix;
import cn.org.atool.fluent.mybatis.generate.mix.MemberLoveTableMix;
import cn.org.atool.fluent.mybatis.generate.mix.MemberTableMix;
import cn.org.atool.fluent.mybatis.generate.mix.NoAutoIdTableMix;
import cn.org.atool.fluent.mybatis.generate.mix.NoPrimaryTableMix;
import cn.org.atool.fluent.mybatis.generate.mix.StudentScoreTableMix;
import cn.org.atool.fluent.mybatis.generate.mix.StudentTableMix;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.util.List;
import org.test4j.module.database.IDataSourceScript;
import org.test4j.module.spec.internal.MixProxy;

/**
 * ATM: Application Table Manager
 *
 * @author Powered By Test4J
 */
public interface ATM {
  DataMap dataMap = new DataMap();

  Table table = new Table();

  Mixes mixes = new Mixes();

  /**
   * 应用表名
   */
  class Table {
    public final String noAutoId = "no_auto_id";

    public final String noPrimary = "no_primary";

    public final String studentScore = "student_score";

    public final String homeAddress = "home_address";

    public final String student = "student";

    public final String memberFavorite = "t_member_favorite";

    public final String memberLove = "t_member_love";

    public final String member = "t_member";
  }

  /**
   * table or entity data构造器
   */
  class DataMap {
    public final NoAutoIdDataMap.Factory noAutoId = new NoAutoIdDataMap.Factory();

    public final NoPrimaryDataMap.Factory noPrimary = new NoPrimaryDataMap.Factory();

    public final StudentScoreDataMap.Factory studentScore = new StudentScoreDataMap.Factory();

    public final HomeAddressDataMap.Factory homeAddress = new HomeAddressDataMap.Factory();

    public final StudentDataMap.Factory student = new StudentDataMap.Factory();

    public final MemberFavoriteDataMap.Factory memberFavorite = new MemberFavoriteDataMap.Factory();

    public final MemberLoveDataMap.Factory memberLove = new MemberLoveDataMap.Factory();

    public final MemberDataMap.Factory member = new MemberDataMap.Factory();
  }

  /**
   * 应用表数据操作
   */
  class Mixes {
    public final NoAutoIdTableMix noAutoIdTableMix = MixProxy.proxy(NoAutoIdTableMix.class);

    public final NoPrimaryTableMix noPrimaryTableMix = MixProxy.proxy(NoPrimaryTableMix.class);

    public final StudentScoreTableMix studentScoreTableMix = MixProxy.proxy(StudentScoreTableMix.class);

    public final HomeAddressTableMix homeAddressTableMix = MixProxy.proxy(HomeAddressTableMix.class);

    public final StudentTableMix studentTableMix = MixProxy.proxy(StudentTableMix.class);

    public final MemberFavoriteTableMix memberFavoriteTableMix = MixProxy.proxy(MemberFavoriteTableMix.class);

    public final MemberLoveTableMix memberLoveTableMix = MixProxy.proxy(MemberLoveTableMix.class);

    public final MemberTableMix memberTableMix = MixProxy.proxy(MemberTableMix.class);

    public void cleanAllTable() {
      this.noAutoIdTableMix.cleanNoAutoIdTable();
      this.noPrimaryTableMix.cleanNoPrimaryTable();
      this.studentScoreTableMix.cleanStudentScoreTable();
      this.homeAddressTableMix.cleanHomeAddressTable();
      this.studentTableMix.cleanStudentTable();
      this.memberFavoriteTableMix.cleanMemberFavoriteTable();
      this.memberLoveTableMix.cleanMemberLoveTable();
      this.memberTableMix.cleanMemberTable();
    }
  }

  /**
   * 应用数据库创建脚本构造
   */
  class Script implements IDataSourceScript {
    @Override
    public List<Class> getTableKlass() {
      return list(
      	NoAutoIdDataMap.class,
      	NoPrimaryDataMap.class,
      	StudentScoreDataMap.class,
      	HomeAddressDataMap.class,
      	StudentDataMap.class,
      	MemberFavoriteDataMap.class,
      	MemberLoveDataMap.class,
      	MemberDataMap.class
      );
    }

    @Override
    public IndexList getIndexList() {
      return new IndexList();
    }
  }
}
