/* be aware it rewrites the whole xml file will be lost */

import groovy.xml.*;


public class AddUsersToRepositoriesXml{
    
        public String listRepoFromXML(xmlFileContent){
        
            def xmlParsed = new XmlSlurper(false, true).parseText( xmlFileContent )
            println "listRepoFromXML"
            xmlParsed.repositories.repository.each(){
                print "»"+it.name+"« , "
            }
            println ""
        }    
        


    public String writeXML(reposMap, xmlFileContent){
        
        def xmlParsed = new XmlSlurper(false, true).parseText( xmlFileContent )
        println "reposMap----»"+reposMap
        xmlParsed.repositories.repository.each(){
            String reponame=it.name
            //println "get repo from name("+it.name+"-»"+reposMap.find(reponame)
            println "get repo from name("+it.name+"--»"+reposMap[reponame]
        }
        reposMap.each { login, perm ->
            
            println login
            println perm
            perm.each() { k,v ->
                println "k=$k,v=$v"
                def fragmentToAdd =   { 
                    permission {
                        name(k)
                        groupPermission("false")
                        type(v)
                    }
                }
                xmlParsed.repositories.repository.permissions.appendNode( fragmentToAdd )
            }
        }    
        return XmlUtil.serialize(xmlParsed)
        
    }
    
}