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

    public String writeXML(def reposMap, def xmlFileContent){
        def xmlParsed = new XmlSlurper(false, true).parseText( xmlFileContent )
        println "reposMap----»"+reposMap
        xmlParsed.repositories.repository.each(){
            String reponame=it.name //converting from a node to a String
            reposMap[reponame].each { k,v ->
                    println "inserting xml for $reponame : $k $v"
                    def fragmentToAdd =   { 
                        permission {
                            name(k)
                            groupPermission("false")
                            type(v)
                        }
                    }
                    def nodeRepo = xmlParsed.repositories.repository.find{ it.name == reponame }
                    nodeRepo.permissions.appendNode( fragmentToAdd )
                }
            }  
            return XmlUtil.serialize(xmlParsed)        
    }    
    def backUp(String path){
        Path source = Paths.get(path);
        Path target = Paths.get(path+".bak");
        Files.copy(source, target)
        println "File $target written"
    }
    
}