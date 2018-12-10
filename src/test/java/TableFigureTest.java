import com.google.gson.Gson;
import domain.TableFigureResult;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class TableFigureTest {

    public static void main(String[] args) throws IOException {

        String inputFile="/Users/jianjie/Desktop/Demo_Insert/src/test/java/TableFigureTest.txt";
        if(args.length>0) inputFile = args[0];
        InputStream is = System.in;
        if(inputFile !=null)is= new FileInputStream(inputFile);


        ANTLRInputStream input = new ANTLRInputStream(is);
        HplsqlLexer lexer = new HplsqlLexer( input);
        CommonTokenStream tokenStream = new CommonTokenStream(lexer);
        HplsqlParser parser = new HplsqlParser(tokenStream);


        /*
            自定义visitor全部遍历
         */
        ParseTree tree = parser.program();
        TableFigureVisitor visitor = new TableFigureVisitor();
        visitor.visit(tree);
        TableFigureResult tfr = visitor.getTFR();

        /*
            Json输出
         */

//        Gson gson = new Gson();
//        System.out.println(gson.toJson(tfr).toString());

    }
}
