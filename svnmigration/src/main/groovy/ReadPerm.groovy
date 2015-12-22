public class ReadPerm {
    
    String permFileContent;
    Map repoMap = [:]
    
    def permMap = [ 'R' : 'READ', 'RW' : 'WRITE' , 'ADM' : 'OWNER']
    
    public void read(String path){
        permFileContent = new File(path).text
    }
    
    public void parse(){
        
        permFileContent.each {
            repoMap + (composeMap(parseLine(it)))
        }
        
    }
    
    public parseLine(def line){
        def equalsSign=line.indexOf('=')
        def left=line.substring(0, equalsSign)
        def right=line.substring(equalsSign+1,line.length())
        
        /* parse left */
        def underscoreSign = left.indexOf('_')
        def repoName = left.substring(0, underscoreSign)
        def permString = left.substring( underscoreSign+1, left.length())
        
        /* parse right */
        
        def userList = right.tokenize(',')
        
        return [ repoName, permMap[permString], userList ]
        
    }
    
    public Map composeMap(ArrayList line){
        
        Map result = [:]
        
        def repoName = line[0]
        def permMap = [:]
        line[2].each { user ->
            permMap[user]=line[1]
        }
        result[repoName]=permMap
        
        return result
    }
    
}