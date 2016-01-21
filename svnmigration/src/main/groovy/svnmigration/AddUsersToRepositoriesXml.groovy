package svnmigration;

/* be aware it rewrites the whole xml file will be lost */
import groovy.xml.*
import java.nio.file.Path
import java.nio.file.Files
import java.nio.file.Paths

public class AddUsersToRepositoriesXml{
    
        public String listRepoFromXML(xmlFileContent){
        
            def xmlParsed = new XmlSlurper(false, true).parseText( xmlFileContent )
            println "listRepoFromXML"
            xmlParsed.repositories.repository.each(){
                print "»"+it.name+"« , "
            }
            println ""
        }    

    public String generateXML(def reposMap, def xmlFileContent){
        def xmlParsed = new XmlSlurper(false, true).parseText( xmlFileContent )
        println "reposMap----»"+reposMap
        xmlParsed.repositories.repository.each(){
            String reponame=it.name //converting from a node to a String
            reposMap[reponame].each { k,v ->
                    
                    def fragmentToAdd =   { 
                        permissions {
                            name(k)
                            groupPermission("false")
                            type(v)
                        }
                    }
                    def nodeRepo = xmlParsed.repositories.repository.find{ it.name == reponame }
                    if(nodeRepo!=null){
                        println "inserting xml for $reponame : $k $v"
                    }else{
                        println "fail to insert xml for $reponame : $k $v. xml node not found for $reponame."
                    }
                    nodeRepo.appendNode( fragmentToAdd )
                }
            }  
            return XmlUtil.serialize(xmlParsed)        
    }  
    
    def writeXML(String xml, String path){
        assert(path!=null)
        PrintWriter pw = new PrintWriter(path)
        pw.write(xml)
        pw.close()  
    }
    
    def backUp(String path){
        Path source = Paths.get(path);
        Path target = Paths.get(path+".bak");
        Files.copy(source, target)
        println "File $target written"
    }
    
}