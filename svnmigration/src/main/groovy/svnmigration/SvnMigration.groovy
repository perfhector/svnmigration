package svnmigration;

import groovy.util.CliBuilder

class SvnMigration {
    static void main(String[] args) {
        def cli = new CliBuilder(usage:'SvnMigration')
        cli.with {
            h longOpt: 'help', 'Show usage information'
            i longOpt: 'input', args: 1, argName:'inputPath', 'the path of perm file in input'
            o longOpt: 'output', args: 1, argName: 'outputPath', 'the path of xml file in output'
        }
        //cli.f(args:1, argName:'filepath', 'the path of perm file in input')
        //cli.x(args:1, argName:'xmlpath',  'the path of xml file in output')
        def options = cli.parse(args)
        String inputPath, outputPath
        
        if(!options ){
            cli.usage()
            return
        }
        
        if (options.h) {
            cli.usage()
            return
        }
        if (options.i) {  
            inputPath=new String(options.i)
            // teste existance fichier
            def inputFile=new File( inputPath )
            if(!inputFile.exists()){
                throw new FileNotFoundException("File : '$inputFile' doesn't exist")
            }
        }    
        if (options.o) {  
            outputPath=new String(options.o)
            // teste existance fichier
            def outputFile=new File( outputPath )
            if(!outputFile.exists()){
                throw new FileNotFoundException("File : '$outputFile' doesn't exist")
            }
        }
        
 /* RUN MAIN */
        
        println "Running... "
        ReadPerm readperm = new ReadPerm();
        readperm.setPath(inputPath);
        readperm.whichNumberLineToRead()
        readperm.read()
        readperm.parse()
        
        AddUsersToRepositoriesXml addUsersToRepositoriesXml = new AddUsersToRepositoriesXml()
        def outputFileContent = new File(outputPath).text
        
        addUsersToRepositoriesXml.listRepoFromXML(outputFileContent)
        addUsersToRepositoriesXml.backUp(outputPath)
        addUsersToRepositoriesXml.writeXML(readperm.getPermissionMap(),outputFileContent)
        println "file $outputPath successfully modified.\n Thanks for using the SvnMigration program."
    
    }
}