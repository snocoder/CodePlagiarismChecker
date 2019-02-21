package fileplagiarismchecker;
import java.util.*;
import java.io.*;
import java.nio.charset.Charset;
//import java.nio.charset.StandardCharsets;
//import java.nio.charset;
/**
 *
 * @author ankit
 */

public class FilePlagiarismChecker {
    public static void main(String[] args){
        
        File folder = new File("C:\\Users\\User\\Documents\\NetBeansProjects\\FilePlagiarismChecker\\src\\fileplagiarismchecker");
        File[] files = folder.listFiles();
        
        HashMap<String, HashMap> hm = new HashMap<>();
        
        String[] ignore = {"for", "while", "break", "continue", "do", "if", "else", "public", "class",
        "static", "main", "import", "java", "util", "void", "def", "#include", "python", "java.io", "int", "Integer",
        "String", "str", "float", "boolean", "printf", "scanf", "print", "i", "j", "k"};
        
        for (File file : files)
        {
            String fileType = file.getName().substring(file.getName().indexOf('.'));
            if(fileType.equals(".txt")){
                ArrayList<String> a = new ArrayList<>();
                int wordCount = 0;

                try{
                    String line;
                    InputStream fis = new FileInputStream(file.getAbsolutePath());
                    InputStreamReader isr = new InputStreamReader(fis, Charset.forName("UTF-8"));
                    BufferedReader br = new BufferedReader(isr);
                    while ((line = (br.readLine())) != null) {
                        line=line.trim();
                        String[] words = line.split(" ");
                        for(String s: words){
                            boolean flag = true;
                            for(int i=0; i<ignore.length; i++){
                                if(ignore[i] == s)
                                    flag = false;
                            }
                            if(flag){
                                String ns = " ";
                                if(!s.equals(ns) && !s.equals("")){
                                    wordCount += 1;
                                    a.add(s);
                                }
                            }
                        }
                    }
                    
                    HashMap<String, Integer> vhm = new HashMap<>();
                    for(int i=0; i<a.size(); i++){
                        String vs = a.get(i);
                        if(!vhm.containsKey(vs))
                            vhm.put(vs, 1);
                        else
                            vhm.put(vs, vhm.get(vs)+1);
                    }
                    vhm.put("wordCount", wordCount);
                    
                    hm.put(file.getName(), vhm);
                    //System.out.println(file.getName());
                    
                    
                    
                }
                catch(IOException e){
                    System.out.println("Not Found");
                }
                
                
                
            }
            
        }
        
        
        
//        HashMap<String, Integer> sample = hm.get("test1.txt");
//        Set<String> kl = sample.keySet();
//        Iterator<String> itr = kl.iterator();
//        while(itr.hasNext()){
//            System.out.println(itr.next());
//        }
        
        for(int i=0; i<files.length-1; i++){
            String fileType1 = files[i].getName().substring(files[i].getName().indexOf('.'));
            
            if(fileType1.equals(".txt")){
                String filename1 = files[i].getName();
                
                HashMap<String, Integer> samplefile1 = hm.get(filename1);
                int totalWords = samplefile1.get("wordCount");
                ArrayList<String> l1 = new ArrayList<String>(samplefile1.keySet());
                int st = l1.size();
                
                for(int j=i+1; j<files.length; j++){
                    
                    String fileType2 = files[j].getName().substring(files[j].getName().indexOf('.'));
                    if(fileType2.equals(".txt")){
                        int copy = 0;
                        String filename2 = files[j].getName();
                        HashMap<String, Integer> samplefile2 = hm.get(filename2);
                        ArrayList<String> l2 = new ArrayList<String>(samplefile2.keySet());
                        int x = 0;
                        while(x<st-1){
//                            System.out.println(filename1 + filename2 + " ");
                        String cmp = l1.get(x);
                        x += 1;
                        if(samplefile2.containsKey(cmp)){
                            
                            copy += 1;
                        }
                        
                        }
                        float perMatch = (float)copy/totalWords;
                        int match = (int)(perMatch*100);
                        System.out.println(filename1 + " and " + filename2 + " has " + match + "% same words");
                            
                        }
                    }
                    
                    
            }
            
        }

    }
    
}
