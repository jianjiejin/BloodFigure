
import domain.TableFigureResult;
import java.util.HashSet;
import java.util.Set;

public class TableFigureVisitor extends HplsqlBaseVisitor {

    private String tableName = null;    //结果表名
    private String procName = null;     //存储过程名
    private String mergeTableName=null;     //merge表名

    private TableFigureResult tablere = new TableFigureResult();
    private Set<String> set = new HashSet<String>();
    private Set<String> sourLineSet = new HashSet<>();

    StringBuilder source = new StringBuilder();
    StringBuilder sb = new StringBuilder();


    /*
        分析insert，获取目标表，循环调用visitFrom_table_name_clause，获取影响表，set用于影响表去重，sourLineSet用于画图去重
     */
    @Override
    public Object visitInsert_stmt(HplsqlParser.Insert_stmtContext ctx) {
        tableName = ctx.table_name().getText();
        set.clear();

        Object result = visitChildren(ctx);


        for (String str : set) {
            sb.append("\"" + str + "\"");
            if (!set.isEmpty()) {
                sb.append(" -> " + "\"" + "存储过程：" + procName + "\"" + "\n");
                if (!sourLineSet.contains(sb.toString())) {
                    source.append(sb.toString());
                    sourLineSet.add(sb.toString());
                }
            }
            sb.delete(0, sb.length());
        }

        sb.append("\"" + "存储过程：" + procName + "\"" + " -> " + "\"" + tableName + "\"" + "[color=red penwidth=2.0]" + "\n");
        if (!sourLineSet.contains(sb.toString())) {
            source.append(sb);
            sourLineSet.add(sb.toString());
        }
        sb.delete(0,sb.length());


        set.clear();
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

//    @Override
//
//    public Object visitMerge_stmt(HplsqlParser.Merge_stmtContext ctx) {
//
//        mergeTableName = ctx.merge_table(0).ident().getText();
//        System.out.println(mergeTableName);
//
//        Object result = visitChildren(ctx);
//
//
//        return result;
//
//    }





    public String getSour() {
        return source.toString();
    }


}
