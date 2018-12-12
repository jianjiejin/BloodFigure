import domain.GraphViz;
import domain.getFiles;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class TableFigureTest {

    public static void main(String[] args) throws IOException {

        String fileDir =  "/Users/jianjie/Desktop/test/";
        List<String> fileList = getFiles.getFileList(fileDir);

        GraphViz gv = new GraphViz();
        gv.addln(gv.start_graph());


        for (int i = 0; i < fileList.size(); i++) {

            String inputFile=fileList.get(i);
            if(args.length>0) {
                inputFile = args[0];
            }

            InputStream is = System.in;
            if(inputFile !=null) {
                is= new FileInputStream(inputFile);
            }

            ANTLRInputStream input = new ANTLRInputStream(is);
            HplsqlLexer lexer = new HplsqlLexer( input);
            CommonTokenStream tokenStream = new CommonTokenStream(lexer);
            HplsqlParser parser = new HplsqlParser(tokenStream);

            ParseTree tree = parser.program();
            TableFigureVisitor visitor = new TableFigureVisitor();          //自定义visitor遍历
            visitor.visit(tree);
            gv.add(visitor.getSour());
            System.out.println(visitor.getSour());
        }

        gv.addln(gv.end_graph());
        String type = "pdf";
        gv.decreaseDpi();
        gv.decreaseDpi();
        String fileName="result";
        File out = new File(fileName+"."+ type);
        gv.writeGraphToFile( gv.getGraph( gv.getDotSource(), type ), out );



    }
}
