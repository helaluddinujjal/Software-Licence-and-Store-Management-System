package supermarket;

import java.util.regex.Pattern;
import javax.swing.JOptionPane;


public class Validation {

    public boolean isNullOrEmpty(String... strArr) {
        for (String st : strArr) {
            if (st == null || st.equals("")) {
                return true;
            }

        }
        return false;
    }

    public boolean isNumeric(String... strArr) {
        for (String st : strArr) {
            try {
                Long.parseLong(st);
            } catch (Exception e) {
                return false;
            }
        }
        return true;
    }

    String printWord(int len, String word) {
        
        int wordLen = word.length();
        if (wordLen < len) {
            for (int i = wordLen; i < len; i++) {
                word = word+" ";
            }
        }

        return word;
        
    }
    
      public boolean isValidEmail(String email)
    {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                            "[a-zA-Z0-9_+&*-]+)*@" +
                            "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                            "A-Z]{2,7}$";
                              
        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pat.matcher(email).matches();
    }
      
      public boolean existedDbURLTest(String url){
          String[] getUrl = url.split("//");
        if (getUrl.length == 2) {
            String[] ch_url = getUrl[1].split("/");
            System.out.println("getUrl:" + ch_url.length);
            if (ch_url.length < 2) {
                JOptionPane.showMessageDialog(null, "URL must be include Database name");
            } else {
                return true;
            }
        }else{
             JOptionPane.showMessageDialog(null, "Input Database URL correctly.Checked to see the rite format");
        }
        return false;
      }
}
