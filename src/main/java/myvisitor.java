import domain.result;

import java.util.HashSet;
import java.util.Set;

public class myvisitor extends HplsqlBaseVisitor {
    result re = new result();
    Set<String> set = new HashSet<>();




    /*
    * 获取结果表
     */
    @Override
    public Object visitInsert_stmt (HplsqlParser.Insert_stmtContext ctx){

        StringBuilder sb = new StringBuilder();
        String tableName = ctx.table_name().getText();
        sb.append(tableName);
        re.setToTable(sb.toString());
        return visitChildren(ctx);


    }


    /*
    *  获取来源表
     */
    @Override
    public Object visitFrom_table_name_clause(HplsqlParser.From_table_name_clauseContext ctx) {

        String table_name = ctx.table_name().getText();
        set.add(table_name);
        re.setFromTables(set);
        return visitChildren(ctx); }

//    @Override
//    public Object visitFrom_join_clause(HplsqlParser.From_join_clauseContext ctx) {
//
//        String table_name = ctx.from_table_clause().from_table_name_clause().table_name().getText();
//        set.add(table_name);
//        re.setFromTables(set);
//
//
//        return visitChildren(ctx); }


    public result getRe() {
        return re;
    }
}
