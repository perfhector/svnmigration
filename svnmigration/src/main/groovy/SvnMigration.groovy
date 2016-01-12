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
        
        if(!options ){
            cli.usage()
            return
        }
        
        if (options.h) {
            cli.usage()
            return
        }
        if (options.i) {  
            def inputPath=options.i
            // teste existance fichier
            def inputFile=new File( inputPath )
            if(!inputFile.exists()){
                throw new IOException("File : '$inputFile' doesn't exist")
            }
        }    
        if (options.o) {  
            def outputPath=options.o
            // teste existance fichier
            def outputFile=new File( outputPath )
            if(!outputFile.exists()){
                throw new IOException("File : '$outputFile' doesn't exist")
            }
        }        
    }    
    def usage(){
        println "java SvnMigration -i /path/to/inputFile -o /path/to/outputfile"
    }
    
}