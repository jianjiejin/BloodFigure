import java.util.HashSet;
import java.util.Set;

public class TableFigureVisitor extends HplsqlBaseVisitor {

    private String tableName = null;
    private String procName = null;

    private Set<String> set = new HashSet<String>();
    private Set<String> sourLineSet = new HashSet<>();


    StringBuilder source = new StringBuilder();
    StringBuilder sb = new StringBuilder();

    /*
        处理过程函数
     */

    private void main(){
        for (String str : set) {
            sb.append("\"" + str + "\"");
            if (!set.isEmpty()  && !str.toUpperCase().contains("SESSION.")) {
                sb.append(" -> " + "\"" + "存储过程：" + procName + "\"" + "\n");
                if (!sourLineSet.contains(sb.toString())) {
                    source.append(sb.toString());
                    sourLineSet.add(sb.toString());
                }
            }
            sb.delete(0, sb.length());
        }

        if(tableName!=null && !tableName.toUpperCase().contains("SESSION.") && !tableName.contains("ETL_ERRLOG_INFO")) {
            sb.append("\"" + "存储过程：" + procName + "\"" + " -> " + "\"" + tableName + "\"" + "[color=red penwidth=2.0]" + "\n");
            if (!sourLineSet.contains(sb.toString())) {
                source.append(sb);
                sourLineSet.add(sb.toString());
            }
            sb.delete(0, sb.length());
            set.clear();
        }

    }


    /*
        分析insert，获取目标表，循环调用visitFrom_table_name_clause，获取影响表，set用于影响表去重，sourLineSet用于画图去重
     */
    @Override
    public Object visitInsert_stmt(HplsqlParser.Insert_stmtContext ctx) {

        tableName = ctx.table_name().getText();
        set.clear();
        Object result = visitChildren(ctx);
        main();

        return result;
    }

    /*
        获取影响表
     */
    @Override
    public Object visitFrom_table_name_clause(HplsqlParser.From_table_name_clauseContext ctx) {

        String table_name = ctx.table_name().ident().getText();
        set.add(table_name);
        return visitChildren(ctx);
    }

    /*
        获取存储过程
     */

    @Override
    public Object visitCreate_procedure_stmt(HplsqlParser.Create_procedure_stmtContext ctx) {

        procName = ctx.ident().get(0).getText();
        return visitChildren(ctx);
    }

    /*
        获取 mergeTableName

     */

    @Override

    public Object visitMerge_stmt(HplsqlParser.Merge_stmtContext ctx) {

        tableName = ctx.merge_table(0).table_name().ident().getText();
        set.clear();
        Object result = visitChildren(ctx);
        main();
        return result;

    }





    public String getSour() {
        return source.toString();
    }

}
