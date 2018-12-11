import domain.GraphViz;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class TableFigureTest {

    public static void main(String[] args) throws IOException {
        String inputFile="src/test/java/TableFigureTest.txt";
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

        GraphViz gv = new GraphViz();
        gv.addln(gv.start_graph());
        gv.add(visitor.getSour());
        gv.addln(gv.end_graph());
        String type = "pdf";
        gv.decreaseDpi();
        gv.decreaseDpi();
        String fileName="result";
        File out = new File(fileName+"."+ type);
        gv.writeGraphToFile( gv.getGraph( gv.getDotSource(), type ), out );
        System.out.println(visitor.getSour());


    }
}
