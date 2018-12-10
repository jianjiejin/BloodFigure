import com.google.gson.Gson;
import domain.TableFigureResult;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class TableFigureVisitor extends HplsqlBaseVisitor{

    TableFigureResult tablere= new TableFigureResult();
    Set<String> set = new HashSet<String>();
    String fromTables =null;
    Gson gson = new Gson();
    String dotFormat=null;





    @Override
    public Object visitInsert_stmt (HplsqlParser.Insert_stmtContext ctx){

        StringBuilder sb = new StringBuilder();
        String tableName = ctx.table_name().getText();
        //sb.append("\"").append(tableName).append("\"").append(" -> ");
        tablere.setToTable(sb.toString());
       // String toTmpTable = tablere.getToTable();
       // System.out.println(toTmpTable);




        set.clear();
        Object result = visitChildren(ctx);
       // System.out.println(fromTables);

        Iterator<String> it = set.iterator();
        while (it.hasNext()){
            String str=it.next();
            sb.append("\"").append(str).append("\" ");
            if(!set.isEmpty())sb.append(" -> ").append("\"").append(tableName).append("\"");
            System.out.println(sb.toString());
            sb.delete(0,sb.length());
        }
        //if(!set.isEmpty())sb.append(" -> ").append("\"").append(tableName).append("\"");

       // System.out.println(sb.toString());

        //System.out.println(gson.toJson(tablere).toString());






        return  result;


    }

    @Override
    public Object visitFrom_table_name_clause(HplsqlParser.From_table_name_clauseContext ctx) {

        String table_name = ctx.table_name().getText();
        set.add(table_name);
        tablere.setFromTables(set);

        fromTables=tablere.getFromTables().toString();

        return visitChildren(ctx);

    }


    public TableFigureResult getTFR(){

        return tablere;

    }


}
